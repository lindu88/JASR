package Base.Primitives;
import java.util.LinkedList;
/*
 * 
 * 
 * 
 * 
 * Unused class
 * 
 * 
 * 
 * 
 */





public class Surface{
    private LinkedList<Triangle> triangles;
    public Surface(LinkedList<Triangle> triangles){
        this.triangles = triangles;
    }
    public Surface(){
        this.triangles = new LinkedList<Triangle>();
    }
    public Triangle get(int index){
        if (triangles == null){
            return null;
        }
        else{
            return triangles.get(index);
        }
        
    }
    public void set(int index, Triangle triangle){
        if (triangles == null){
            System.out.println("Error: Null Surface");
        }
        else{
           this.triangles.set(index, triangle);
        }
        
    }
    public void addLast(Triangle triangle){
        this.triangles.addLast(triangle);
    }
    public LinkedList<Triangle> getList(){
        return this.triangles;
    }
}
