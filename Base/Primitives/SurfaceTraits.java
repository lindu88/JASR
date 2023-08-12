package Base.Primitives;

import Base.RenderComponents.Color;

public class SurfaceTraits{
    private Color color;
    private double specular;
    private double reflective;
    public SurfaceTraits(Color color, double specular, double reflective){
        this.color = color;
        this.specular = specular;
        this.reflective = reflective;
    }
    public Color getColor(){
        return this.color;
    }
    public double getSpecular(){
        return this.specular;
    }
    public double getReflective(){
        return this.reflective;
    }
}