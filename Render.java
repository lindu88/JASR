class Render{
    //
    public void render(Canvas screen, double[] O, double yaw, double pitch, double roll){
        for (int x = -Settings.cW/2; x < Settings.cW/2; x++){
            for (int y = -Settings.cH/2 + 1; y < Settings.cH/2; y++){
                double[] D = RenderMath.rotate(RenderMath.canvasToViewport(x, y), yaw, pitch, roll); //V - O
                Color color = traceRay(O, D, 100);
                screen.putPixel(x, y, color);
            }
        }
        screen.display();
    }
    
    private Color traceRay(double[] O, double[] D, int t_max){
        Sphere closest_sphere = closestSphere(O, D, 0, t_max);
        double closest_t = closestT(O, D, 0, t_max);
        if (closest_sphere == null){
            return Color.WHITE;
        }
        double[] P = RenderMath.vectorAdd(O, RenderMath.scalarMultiply(D, closest_t));
        double[] N = RenderMath.vectorSubtract(P, closest_sphere.getCenter());
        N = RenderMath.scalarMultiply(N, 1.0f / RenderMath.magnitude(N));
        Color color = closest_sphere.getColor().clone();
        color.multiply(computeLight(P, N, RenderMath.scalarMultiply(D, -1), closest_sphere.getSpecular()));
        if (color.getR() > 255){System.out.println(">255" + color.getR());}
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
            Sphere shadowSphere = closestSphere(P, L, 0.001, t_max);
            if (shadowSphere != null){
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
                R = RenderMath.scalarMultiply(N, 2);
                R = RenderMath.scalarMultiply(R, RenderMath.dot(N, L));
                R = RenderMath.vectorSubtract(R, L);
                
                double rvdot = RenderMath.dot(R, V);
                if (rvdot > 0){
                    
                    i += l.getIntesity() * Math.pow(rvdot / (RenderMath.magnitude(R) * RenderMath.magnitude(V)), s);
                }
            }
        }
        return i;
    }

    /* Java needs pairs and tupals.ect this isnt very efficient*/
    private Sphere closestSphere(double[] O, double[] D, double t_min, double t_max){ //finds closest sphere in ray
        Sphere closest_sphere = null;
        double closest_t = Settings.render_distance; //just a big number -- should be inf -- also sets a max render distance
        for (Sphere s: Scene.spheres){
           double t = intersectRaySphere(O, D, s)[0];
           if (t < closest_t && t > t_min && t < t_max){
            closest_sphere = s;
            closest_t = t;
           }
        }
        return closest_sphere;
    }
    private double closestT(double[] O, double[] D, double t_min, double t_max){ //finds closest interseciton t in ray
        double closest_t = Settings.render_distance; //just a big number -- should be inf -- also sets a max render distance
        for (Sphere s: Scene.spheres){
           double t = intersectRaySphere(O, D, s)[0];
           if (t < closest_t && t > t_min && t < t_max){
            closest_t = t;
           }
        }
        return closest_t;
    }
}
