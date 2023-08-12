package Base.Primitives;

import Base.RenderComponents.Color;

public class Triangle extends SurfaceTraits{
    private double[] a;
    private double[] b;
    private double[] c;

    public Triangle(double[] a, double[] b, double[] c, Color color, double specular, double reflective){
        super(color,specular, reflective);
        this.a = a;
        this.b = b;
        this.c = c;
    }
    public double[] getA(){
        return this.a;
    }
    public double[] getB(){
        return this.b;
    }
    public double[] getC(){
        return this.c;
    }

}
