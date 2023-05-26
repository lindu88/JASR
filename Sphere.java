class Sphere{
    private double r;
    private double[] center;
    private Color color;
    private double specular;
    public Sphere(double r, double[] center, Color color, double specular){
        this.r = r;
        this.center = center;
        this.color = color;
        this.specular = specular;
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
    public double getSpecular(){
        return this.specular;
    }
}