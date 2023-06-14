import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JTextArea;

public class RenderGUI extends JFrame {
    Render render;
    Canvas renderCanvas;
    JButton renderButton;
    JSlider RXSlider;
    JSlider RYSlider;
    JSlider RZSlider;
    JTextArea camerOrginArea;
    RenderGUI(){
        super("Options");
        setLayout(new GridLayout(5, 0));
        setSize(250, 600);

        renderButton = new JButton("Render");
        add(renderButton);
        renderButton.addActionListener(new renderButtonLisitiner());

        RXSlider = new JSlider(-100, 100, 0);
        add(RXSlider);

        RYSlider = new JSlider(-100, 100, 0);
        add(RYSlider);

        RZSlider = new JSlider(-100, 100, 0);
        add(RZSlider);

        camerOrginArea = new JTextArea("0 0 0", 1, 0);
        add(camerOrginArea);
        camerOrginArea.setFont(new Font("Serif",Font.PLAIN,20));

        render = new Render();
        renderCanvas = new Canvas(Settings.cW, Settings.cH);
        renderCanvas.setVisible(true);
    }
    public double[] getCameraOrgin(){
        Scanner s = new Scanner(camerOrginArea.getText());
        int x = s.nextInt();
        int y = s.nextInt();
        int z = s.nextInt();
        s.close();
        return new double[]{x,y,z};
    }
    private class renderButtonLisitiner implements ActionListener{
        public void actionPerformed(ActionEvent e){
            render.render(renderCanvas, getCameraOrgin(), RXSlider.getValue() / 100.0f, RYSlider.getValue() / 100.0f, RZSlider.getValue() / 100.0f);
        }
    }
}
