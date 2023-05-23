import java.util.Scanner;

class Start{
    public static void main(String args[]){
        Scanner stdin = new Scanner(System.in);
        Canvas canvas =  new Canvas(Settings.cW, Settings.cH);
        Render test = new Render();

        System.out.println("Enter string in format x y z yaw pitch roll: ");
        double x = stdin.nextDouble();
        double y = stdin.nextDouble();
        double z = stdin.nextDouble();
        double yaw = stdin.nextDouble();
        double pitch = stdin.nextDouble();
        double roll = stdin.nextDouble();
        
        canvas.setVisible(true);
        test.render(canvas, new double[]{x,y,z}, yaw, pitch, roll);
        stdin.close();
        
    }
}