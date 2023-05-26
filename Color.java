public class Color{
    private int R;
    private int G;
    private int B;

    final static Color RED = new Color(255,0,0);
    final static Color BLUE = new Color(0,0,255);
    final static Color BLACK = new Color(0,0,0);
    final static Color WHITE = new Color(255,255,255);

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

    private int cap(int rgb){
        int value;
        if (rgb > 255){value = 255;}
        else if (rgb < 0){value = 0;}
        else {value = rgb;}
        return value;
    } 
}