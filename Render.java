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
        Sphere closest_sphere = null;
        double closest_t = 1000; //just a big number -- should be inf
        for (Sphere s: Scene.spheres){
           double t = intersectRaySphere(O, D, s)[0];
           if (t > 0){
            //System.out.println(t);
           }
           if (t < closest_t && t != 0 && t < t_max){
            
            closest_sphere = s;
            closest_t = t;
           }
        }
        if (closest_sphere == null){
            return Color.WHITE;
        }
        double[] P = RenderMath.vectorAdd(O, RenderMath.scalarMultiply(D, closest_t));
        double[] N = RenderMath.vectorSubtract(P, closest_sphere.getCenter());
        N = RenderMath.scalarMultiply(N, 1.0f / RenderMath.magnitude(N));
        Color color = closest_sphere.getColor().clone();
        color.multiply(computeLight(P, N));
        return color;
    }
    private double[] intersectRaySphere(double[] O, double[] D, Sphere sphere){
        double r = sphere.getR();
        double[] CO = RenderMath.vectorSubtract(O, sphere.getCenter());
        double a = RenderMath.dot(D, D);
        //System.out.println(a);
        double b = 2 * RenderMath.dot(CO, D);
        //System.out.println(b);
        double c = RenderMath.dot(CO, CO) - r*r;
       // System.out.println(c);
        return RenderMath.quadraticFormula(a, b, c);
    }
    private double computeLight(double[] P, double[] N){ //sets intenstiy of color per pixel acroding to light soruce -- diffuse reflection
        double i = 0.0;
        double[] L = null;
        for (Light l: Scene.lights){
            if (l.getType() == Light.Type.AMBIENT){
                i += l.getIntesity();
            }
            else if (l.getType() == Light.Type.POINT){
                L = RenderMath.vectorSubtract(l.getDirection(), P);
                
            }
            else if (l.getType() == Light.Type.DIRECTIONAL){
                L = RenderMath.vectorAdd(l.getDirection(), new double[]{0,0,0}); //clone "workaround" 
            }
            double nlDot = RenderMath.dot(N, L);
            if ( nlDot > 0){
                i += l.getIntesity() * nlDot/(RenderMath.magnitude(N) * RenderMath.magnitude(L));
            }
        }
        return i;
    }
}
