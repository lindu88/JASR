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
    JSlider yawSlider;
    JSlider pitchSlider;
    JSlider rollSlider;
    JTextArea camerOrginArea;
    RenderGUI(){
        super("Options");
        setLayout(new GridLayout(5, 0));
        setSize(250, 600);

        renderButton = new JButton("Render");
        add(renderButton);
        renderButton.addActionListener(new renderButtonLisitiner());

        yawSlider = new JSlider(-100, 100, 0);
        add(yawSlider);

        pitchSlider = new JSlider(-100, 100, 0);
        add(pitchSlider);

        rollSlider = new JSlider(-100, 100, 0);
        add(rollSlider);

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
            render.render(renderCanvas, getCameraOrgin(), yawSlider.getValue() / 100.0f, pitchSlider.getValue() / 100.0f, rollSlider.getValue() / 100.0f);
        }
    }
}
