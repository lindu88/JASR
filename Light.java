class Light{
    public enum Type {AMBIENT, POINT, DIRECTIONAL}
    private double intesity;
    private Type name;
    private double[] direction;//postion or direction
    public Light(double intesity){
        this.intesity = intesity;
        this.name = Type.AMBIENT;
    }
    public Light(double intesity, Type name, double[] direction){
        this.intesity = intesity;
        this.name = name;
        this.direction = direction;
    }
    public double getIntesity(){
        return this.intesity;
    }
    public double[] getDirection(){
        return this.direction;
    }
    public Light.Type getType(){
        return this.name;
    }
}