import java.util.ArrayList;

class Scene{
    public static Sphere[] spheres = new Sphere[]{
        new Sphere(2,new double[]{2,-3,4},Color.RED(), 1000, 0.5),
        new Sphere(1,new double[]{0,-1,6},Color.BLUE(), 500, 0.2),
        new Sphere(2,new double[]{4,-3,4},Color.RED(), 1000, 0.5),
        new Sphere(1,new double[]{2,-1,1},Color.BLUE(), 500, 0.2),
        new Sphere(30,new double[]{0,-31,20},new Color(255,50,50), -1, 0)
    };
    public static Light[] lights = new Light[]{
        new Light(0.2, Light.Type.DIRECTIONAL, new double[]{1,4,4}),
        new Light(0.2),
        new Light(0.6, Light.Type.POINT, new double[]{15,15,15})
    };

    //hold triangle data in form double[vertex][index of vertex]
    public static ArrayList<double[][]> triangles = new ArrayList<double[][]>(); 
}