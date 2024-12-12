package Base;

import Base.FileReaders.STLReader;
import Base.Primitives.*;
import Base.RenderComponents.Color;
import Base.RenderComponents.Light;

import java.util.LinkedList;

class Scene{
    
    public static Sphere[] spheres = new Sphere[]{
        
        //new Sphere(0.4,new double[]{0.57,3.57,-0.02},Color.BLUE(), 1000, 0.5),
        new Sphere(1,new double[]{0,5,0},Color.RED(), 500, 0.2),
        new Sphere(1,new double[]{5,0,0},Color.RED(), 1000, 0.5),
        new Sphere(1,new double[]{0,0,0},Color.BLUE(), 500, 0.2),
        new Sphere(3000,new double[]{0,-3001,20},new Color(255,50,50), -1, 0)
    };
    public static Light[] lights = new Light[]{
        new Light(0.2, Light.Type.DIRECTIONAL, new double[]{1,4,4}),
        new Light(0.2),
        new Light(0.6, Light.Type.POINT, new double[]{15,15,15})
    };

    
    public static Surface object = STLReader.getListASCII("C:/Users/weston12232/Downloads/cube.stl", Color.RED(), 0.5, 0.3);

    //STLReader.getListASCII("C:/Users/weston12232/Downloads/block100.stl", Color.BLUE(), 0.5, 0.3);
}