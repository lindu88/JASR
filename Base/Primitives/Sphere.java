package Base.Primitives;

import Base.RenderComponents.Color;;
public class Sphere extends SurfaceTraits{
    private double r;
    private double[] center;
    public Sphere(double r, double[] center, Color color, double specular, double reflective){
        super(color,specular,reflective);
        this.r = r;
        this.center = center;
    }
    public double getR(){
        return this.r;
    }
    public double[] getCenter(){
        return this.center;
    }
}