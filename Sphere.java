class Sphere{
    private double r;
    private double[] center;
    private Color color;
    public Sphere(double r, double[] center, Color color){
        this.r = r;
        this.center = center;
        this.color = color;
    }
    public double getR(){
        return this.r;
    }
    public double[] getCenter(){
        return this.center;
    }
    public Color getColor(){
        return this.color;
    }
}