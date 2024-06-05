package Base;
import Base.RenderComponents.Color;
import Base.RenderComponents.Light;
import Base.RenderComponents.RenderContainer;
import Base.RenderComponents.RenderMath;
import Base.RenderComponents.Settings;
import Base.Generics.*;
import Base.Primitives.*;
class Render{
    //
    public void render(Canvas screen, double[] O, double xRot, double yRot, double zRot, int recursion_depth, int xMin, int xMax, int yMin, int yMax){
        //xMin = -Settings.cW/2
        //xMax = Settings.cW/2
        //yMin = -Settings.cH/2 + 1
        //yMax = Settings.cH/2;
        for (int x = xMin; x < xMax; x++){
            for (int y = yMin + 1; y < yMax; y++){
                double[] D = RenderMath.rotate(RenderMath.canvasToViewport(x, y), xRot, yRot, zRot); //V - O
                Color color = traceRay(O, D, 0.001, Settings.render_distance, recursion_depth);
                screen.putPixel(x, y, color);
            }
        }
        screen.display();
    }
    
    private Color traceRay(double[] O, double[] D, double t_min, double t_max, int recursion_depth){
        RenderContainer primitive = getClosestPrimitiveColorAndT(O, D, 0.001, t_max);

        if (primitive == null){
            return Color.BLACK(); 
        }
        double[] P = RenderMath.getPointFromRay(O,D,primitive.getTime());

        double[] N = primitive.getNormal();
        //make unit normal
        N = RenderMath.scalarMultiply(N, 1.0f / RenderMath.magnitude(N));

        //compute lighting effect on color
        
        Color color = primitive.getColor().clone();
        
        color.multiply(computeLight(P, N, RenderMath.scalarMultiply(D, -1), primitive.getSpecular()));

        //recursion limit and reflective index
        double r = primitive.getReflective();
       
        if (recursion_depth <= 0 || r <= 0){
            return color;
        }

        //reelection
        double[] R = reflectRay(RenderMath.scalarMultiply(D, -1), N);
        Color reflectedColor = traceRay(P, R, t_min, t_max, recursion_depth - 1);
         
        

        //compute color
        color.multiply(1.0 - r);
        reflectedColor.multiply(r);
        color.add(reflectedColor);

        return color;
    }
    private double[] intersectRaySphere(double[] O, double[] D, Sphere sphere){
        //returns the scalar t for the interstion points on the sphere from the camera
        //Point in ray = Camera orgin + t * direction vector

        double r = sphere.getR();
        double[] CO = RenderMath.vectorSubtract(O, sphere.getCenter());
        double a = RenderMath.dot(D, D);
        
        double b = 2 * RenderMath.dot(CO, D);
        
        double c = RenderMath.dot(CO, CO) - r*r;

        return RenderMath.quadraticFormula(a, b, c);
    }
    private double computeLight(double[] P, double[] N, double[] V, double s){ //sets intenstiy of color per pixel acroding to light soruce 
        double i = 0.0;
        double[] L = null;
        double[] R = null;
        double t_max = Settings.render_distance;
        for (Light l: Scene.lights){
            if (l.getType() == Light.Type.AMBIENT){
                i += l.getIntesity();
            }
            else if (l.getType() == Light.Type.POINT){
                L = RenderMath.vectorSubtract(l.getDirection(), P);
                t_max = 1;
                
            }
            else if (l.getType() == Light.Type.DIRECTIONAL){
                L = RenderMath.vectorAdd(l.getDirection(), new double[]{0,0,0}); //clone "workaround" 
            }

            //shadows
            RenderContainer shadow = getClosestPrimitiveColorAndT(P, L, 0.001, t_max);
            if (shadow != null){
                continue;
            }

            //diffuse
            double nlDot = RenderMath.dot(N, L);
            if ( nlDot > 0){ //no negative values - no lighting behind surface
                i += l.getIntesity() * nlDot/(RenderMath.magnitude(N) * RenderMath.magnitude(L));
            }
            //specular
            if (s != -1){
                /*  R = 2 * N * dot(N,L) - L  */
                R = reflectRay(L, N);
                
                double rvdot = RenderMath.dot(R, V);
                if (rvdot > 0){
                    
                    i += l.getIntesity() * Math.pow(rvdot / (RenderMath.magnitude(R) * RenderMath.magnitude(V)), s);
                }
            }
        }
        return i;
    }

    /* used pair to avoid repeat calculations*/
    private Pair<Sphere, Double> closestSphereAndT(double[] O, double[] D, double t_min, double t_max){ //finds closest sphere in ray
        Sphere closest_sphere = null;
        double closest_t = 10000; //just a big number -- should be inf -- also sets a max render distance
        for (Sphere s: Scene.spheres){
           double t = intersectRaySphere(O, D, s)[0];
           if (t < closest_t && t > t_min && t < t_max){
            closest_sphere = s;
            closest_t = t;
           }
        }
        
        return new Pair<Sphere,Double>(closest_sphere, closest_t);
    }
    /*
     * Will change to render surfaces 
     */
    private Pair<Triangle, Double> closestTriangleAndT(double[] O, double[] D, double t_min, double t_max){ //finds closest sphere in ray
        Triangle closest_triangle = null;
        double closest_t = 10000; //just a big number -- should be inf -- also sets a max render distance
        for (Triangle p: Scene.triangles){
            double[] N = RenderMath.cross(RenderMath.vectorSubtract(p.getB(), p.getA()), RenderMath.vectorSubtract(p.getC(), p.getA()));
            N = RenderMath.scalarMultiply(N, 1f / RenderMath.magnitude(N));
            double t = intersectRayPlane(O, D, N, p.getA());
            double[] point = RenderMath.getPointFromRay(O, D, t);
            //check if in triangle
            boolean inTri = false;
            if (t>0){
                inTri = RenderMath.checkPoint(p.getA(), p.getB(), p.getC(), point);
            }

            if ((t < closest_t & t > t_min & t < t_max) & inTri){
                closest_triangle = p;
                closest_t = t;
            }

        }
        
        return new Pair<Triangle,Double>(closest_triangle, closest_t);
    }
    public RenderContainer getClosestPrimitiveColorAndT(double[] O, double[] D, double t_min, double t_max){
        Pair<Sphere, Double> tSphere = closestSphereAndT(O, D, t_min, t_max);
        Pair<Triangle, Double> tTriangle = closestTriangleAndT(O, D, t_min, t_max);
        double[] NSphere = null;
        double[] NTriangle = null;

        double timeForSphere = tSphere.getSecond();
        double timeForTriangle = tTriangle.getSecond();

        //Could filter out null another way but this works. If null assume > max distance
        if (tSphere.getFirst() == null){
            timeForSphere = t_max + 1f;
        }
        if (tTriangle.getFirst() == null){
            timeForTriangle = t_max + 1f;
        }

        if (timeForSphere < timeForTriangle){
            NSphere = RenderMath.vectorSubtract(RenderMath.getPointFromRay(O, D, tSphere.getSecond()), tSphere.getFirst().getCenter());
            return new RenderContainer(tSphere.getFirst().getColor(), NSphere, timeForSphere,tSphere.getFirst().getReflective(), tSphere.getFirst().getSpecular() );
        }
        if(timeForSphere > timeForTriangle){
            //System.out.println(timeForTriangle);
            NTriangle = RenderMath.cross(RenderMath.vectorSubtract(tTriangle.getFirst().getB(), tTriangle.getFirst().getA()), RenderMath.vectorSubtract(tTriangle.getFirst().getC(), tTriangle.getFirst().getA()));
            //NTriangle = RenderMath.scalarMultiply(NTriangle, 1f / RenderMath.magnitude(NTriangle));
            return new RenderContainer(tTriangle.getFirst().getColor(), NTriangle, timeForTriangle, tTriangle.getFirst().getReflective(), tTriangle.getFirst().getSpecular());
        }
        else{
            return null;
        }
    }
    
    private double intersectRayPlane(double[] O, double[] D, double[] N, double[] vertex){
        double denominator = RenderMath.dot(N, D);
       
        if (denominator > 0.001f){
            double numerator = (RenderMath.dot(RenderMath.vectorSubtract(vertex, O), N));
            //System.out.println(t);
            return numerator / denominator;
        }
        else{
            return 0;
        }
    }
    // returns /*  R = 2 * N * dot(N,L) - L  */
    // returns vector for reflected ray
    private double[] reflectRay(double[] R, double[] N){
        double[] ray;
        ray = RenderMath.scalarMultiply(N, 2);
        ray = RenderMath.scalarMultiply(ray, RenderMath.dot(N, R));
        ray = RenderMath.vectorSubtract(ray, R);
        return ray;
    }
}
