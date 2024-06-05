package Base.RenderComponents;

//container to encapsulate the different data needed to render primitives
//will have to change when implementing textures
public class RenderContainer {
    private Color color;
    private double[] normal;
    private double time;
    private double reflective;
    private double specular;
    
    public RenderContainer(Color color, double[] normal, double time, double reflective, double specular){
        this.color = color;
        this.normal = normal;
        this.time = time;
        this.reflective = reflective;
        this.specular = specular;
    }
    public Color getColor(){
        return this.color;
    }
    public double[] getNormal(){
        return this.normal;
    }
    public double getTime(){
        return this.time;
    }
    public double getReflective(){
        return this.reflective;
    }
    public double getSpecular(){
        return this.specular;
    }
}
