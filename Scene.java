class Scene{
    public static Sphere[] spheres = new Sphere[]{
        new Sphere(2,new double[]{2,0,4},Color.RED),
        new Sphere(1,new double[]{0,-1,6},Color.BLUE),
        new Sphere(30,new double[]{0,-31,20},new Color(50,50,50))
    };
    public static Light[] lights = new Light[]{
        new Light(0.2, Light.Type.DIRECTIONAL, new double[]{1,4,4}),
        new Light(0.2),
        new Light(0.6, Light.Type.POINT, new double[]{2,1,0})
    };
}