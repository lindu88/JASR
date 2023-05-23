public class Color{
    private int R;
    private int G;
    private int B;

    final static Color RED = new Color(255,0,0);
    final static Color BLUE = new Color(0,0,255);
    final static Color BLACK = new Color(0,0,0);
    final static Color WHITE = new Color(255,255,255);

    public Color(int R, int G, int B){//uses int instead of byte for eaiser convention
        if (R > 256){R = 256;}
        if (G > 256){G = 256;}//have to cap
        if (B > 256){B = 256;}
        if (R < 0){R = 0;}
        if (G < 0){G = 0;}
        if (B < 0){B = 0;}
        this.R = R;
        this.B = B;
        this.G = G;
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
        this.R = (int) R;
        this.G = (int) G;
        this.B = (int) B;
    }
    public Color clone(){
        return new Color(this.R,this.G,this.B);
    }
}