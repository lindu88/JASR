package Base.RenderComponents;
public class Color{
    private int R;
    private int G;
    private int B;

    public static Color RED(){
        return new Color(255,0,0);
    }
    public static Color BLUE(){
        return new Color(0,0,255);
    }
    public static Color BLACK(){
        return new Color(0,0,0);
    }
    public static Color WHITE(){
        return new Color(255,255,255);
    }

    public Color(int R, int G, int B){//uses int instead of byte for eaiser convention\
        this.R = cap(R);
        this.B = cap(B);
        this.G = cap(G);
    }
    public int getR(){
        return this.R;
    }
    public int getG(){
        return this.G;
    }
    public int getB(){
        return this.B;
    }
    public void multiply(double k){
        double R = this.R * k;
        double G = this.G * k;
        double B = this.B * k;
        this.R = cap((int) R);
        this.G = cap((int) G);
        this.B = cap((int) B);
    }
    public Color clone(){
        return new Color(this.R,this.G,this.B);
    }
    public void add(Color color){
        this.R = cap(color.getR() + this.R);
        this.G = cap(color.getG() + this.G);
        this.B = cap(color.getB() + this.B);
    }

    private int cap(int rgb){
        int value;
        if (rgb > 255){value = 255;}
        else if (rgb < 0){value = 0;}
        else {value = rgb;}
        return value;
    } 
}