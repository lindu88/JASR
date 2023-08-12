package Base.Primitives;

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
    private Triangle[] triangles;
    private double[] center;
    public Surface(Triangle[] triangles, double[] center){
        this.triangles = triangles;
        this.center = center;
    }
    public Triangle get(int index){
        if (triangles == null){
            return null;
        }
        else{
            return triangles[index];   
        }
        
    }
    public void set(int index, Triangle triangle){
        if (triangles == null){
            System.out.println("Error: Null Surface");
        }
        else{
           this.triangles[index] = triangle;   
        }
        
    }
    public Triangle[] getList(){
        return this.triangles;
    }
    public double[] getCenter(){
        return this.center;
    }
}
