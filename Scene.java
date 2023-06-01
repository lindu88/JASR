class Scene{
    public static Sphere[] spheres = new Sphere[]{
        new Sphere(2,new double[]{2,-3,4},Color.RED, 1000),
        new Sphere(1,new double[]{0,-1,6},Color.BLUE, 500),
        new Sphere(30,new double[]{0,-31,20},new Color(50,50,50), -1)
    };
    public static Light[] lights = new Light[]{
        new Light(0.2, Light.Type.DIRECTIONAL, new double[]{1,4,4}),
        new Light(0.2),
        new Light(0.6, Light.Type.POINT, new double[]{15,15,15})
    };
}