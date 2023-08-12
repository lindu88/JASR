package Base.RenderComponents;
import java.lang.Math;
public class RenderMath{
    //returns only two real non equal roots
    //Result is sorted from smallest to greatest
    public static double[] quadraticFormula(double a, double b, double c){
        double determinant = b * b - 4 * a * c;
        double root1, root2;

        // two real and distinct roots
        if (determinant > 0) {
            root1 = (-b + Math.sqrt(determinant)) / (2 * a);
            root2 = (-b - Math.sqrt(determinant)) / (2 * a);

            if (root1 < root2){
                return new double[]{root1,root2};
            }
            else{return new double[]{root2, root1};}
        }
        else{return new double[]{0,0};}
    }
    public static double dot(double[] a, double[] b){
        /*Even though the vectors will be length 3, its nice to have a genric form for future extensions */
        if (a.length != b.length){
            System.out.println("Vecors not of equal size");
            return 0;
        }
        double scalarOut = 0;
        for (int i = 0;i < a.length; i++){
            scalarOut += a[i] * b[i];
        }
        return scalarOut;
    }
    //cross product
    public static double[] cross(double[] a, double[] b){
        return new double[]{a[1]*b[2]-a[2]*b[1],-1*(a[0]*b[2]-a[2]*b[0]),a[0]*b[1]-a[1]*b[0]};
    }

    //check if point is inside triangle with barycentric cords of point projected on the plane of the triangle abc
    
    public static boolean checkPoint(double[] a, double[] b, double[] c, double[] p){
        double[] u = vectorSubtract(b, a);
        double[] v = vectorSubtract(c, a);

        double[] w = vectorSubtract(p, a);

        double[] N = cross(u, v);
        
        double gamma = dot(cross(u, w),N) / dot(N, N);
        
        double beta = dot(cross(w, v),N) / dot(N, N);
        double alpha = 1f - gamma - beta;
        
        
        return (0 <= alpha && alpha <= 1) && (0 <= beta && beta <= 1) && (0 <= gamma && gamma <= 1);
      
    }

    public static double[] getPointFromRay(double[] O, double[] D, double t){
        return vectorAdd(O, scalarMultiply(D, t));
    }
    //to avoid floating point errors : common algorithm that rounds double to int places
    public static double round(double d, int places){
        double scale = Math.pow(10, places);
        return Math.round(d * scale) / scale;
    }
    //rotation along x axis (RX)
    public static double[][] getRX(double alpha){
        return new double[][]{
            {1,               0,                   0},
            {0, Math.cos(alpha),  -1*Math.sin(alpha)},
            {0, Math.sin(alpha),     Math.cos(alpha)}
        };
    }
    public static double[][] getRY(double beta){
        return new double[][]{
            {Math.cos(beta),       0,    Math.sin(beta)},
            {0,                    1,                 0},
            {-1f * Math.sin(beta), 0,    Math.cos(beta)}
        };
    }
    public static double[][] getRZ(double phi){
        return new double[][]{
            {Math.cos(phi),  -1*Math.sin(phi),  0},
            {Math.sin(phi),     Math.cos(phi),  0},
            {0,                             0,  1}
        };
    }
    public static double[] multiply_3D(double[] a, double[][] b){//3D vector matrix multiply
        double[] c = new double[3];
        for (int i = 0; i < b.length; i++){
            for (int j = 0; j < b[0].length; j++){
                c[i] += a[j] * b[i][j];
            }
        }
        return c;
    }
    public static double[] rotate(double[] a, double RX, double RY, double RZ){ //3d rotate
        a = RenderMath.multiply_3D(a, RenderMath.getRX(RX));
        a = RenderMath.multiply_3D(a, RenderMath.getRY(RY));
        a = RenderMath.multiply_3D(a, RenderMath.getRZ(RZ));
        return a;

    }
    public static double[] canvasToViewport(int x, int y){ //point on viewport that corresponds to canvas. Used to trace ray in needed direction 
        return new double[]{x * Settings.vpW/Settings.cW, y * Settings.vpH/Settings.cH, Settings.d};
    }
    /*various linear algebra functions */
    public static double[] scalarMultiply(double[] a, double k){
        return new double[]{a[0] * k, a[1] * k, a[2] * k};
    }
    public static double[] vectorAdd(double[] a, double[] b){
        return new double[]{a[0] + b[0], a[1] + b[1], a[2] + b[2]};
    }
    public static double[] vectorSubtract(double[] a, double[] b){
        return new double[]{a[0] - b[0], a[1] - b[1], a[2] - b[2]};
    }
    public static double magnitude(double[] a){
        return Math.sqrt(RenderMath.dot(a, a));
    }
}