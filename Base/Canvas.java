package Base;
import javax.swing.*;

import Base.RenderComponents.Color;
import Base.RenderComponents.Settings;

import java.awt.image.BufferedImage;

public class Canvas extends JPanel {

    private BufferedImage displayImage;
    private JLabel label;
    
    Canvas(int cWidth,int cHeight){//display
        label = new JLabel();
        add(label);

        this.displayImage = new BufferedImage(cWidth, cHeight, BufferedImage.TYPE_INT_ARGB); 


        setSize(cWidth, cHeight);
    }
    public void putPixel(int x, int y, Color color){
        
        x = x + (Settings.cW / 2); //convert orgin and switch y axis
        y = -1*y + (Settings.cW / 2); //
        
        int a = 255;//alpha - implement later

        int col =  (a << 24) | (color.getR() << 16) | (color.getG() << 8) | color.getB(); //RGB to int

        //System.out.println(x + " " + y);
        displayImage.setRGB(x, y, col);
    }
    public void display(){ //updates display image
        this.label.setIcon(new ImageIcon(displayImage));
        repaint();
    }
}