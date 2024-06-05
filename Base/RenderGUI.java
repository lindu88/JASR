package Base;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import Base.RenderComponents.Settings;
public class RenderGUI extends JFrame {
    Render render;
    Canvas renderCanvas;
    JButton renderButton;
    JSlider RXSlider;
    JSlider RYSlider;
    JSlider RZSlider;
    JSlider ReflectiveSlider;
    JTextArea camerOrginArea;
    public RenderGUI(){
        super("Options");
        setLayout(new GridLayout(6, 0));
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

        ReflectiveSlider = new JSlider(0, 6, 3);
        add(ReflectiveSlider);

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
            double xRot = RXSlider.getValue() / 100.0f;
            double yRot = RYSlider.getValue() / 100.0f;
            double zRot = RZSlider.getValue() / 100.0f;
            int reflRecursion = ReflectiveSlider.getValue();


            int threadCount =  Settings.thread_count;
            if (threadCount % 2 != 0) threadCount++;

            int xBlockSize = threadCount/2;
            int yBlockSize = 2;

            int xSegSize = Settings.cW/xBlockSize;
            int ySegSize = Settings.cH/yBlockSize;

            for (int i = 0; i < yBlockSize; i++){
                for (int j = 0; j < xBlockSize; j++){
                    int finalJ = j;
                    int finalI = i;

                    // min and max screen numbers
                    //xMin = -Settings.cW/2
                    //xMax = Settings.cW/2
                    //yMin = -Settings.cH/2 + 1
                    //yMax = Settings.cH/2;

                    int xMin = finalJ*xSegSize - Settings.cW/2;
                    int xMax = (finalJ+1)*xSegSize - Settings.cW/2;
                    int yMin = (finalI*ySegSize) - Settings.cH/2;
                    int yMax = (finalI+1)*ySegSize -  Settings.cH/2 + 1;

                    Runnable renderThread = () ->
                    {
                        render.render(renderCanvas, getCameraOrgin(), xRot, yRot, zRot, reflRecursion, xMin, xMax, yMin, yMax);
                    };
                    Thread run = new Thread(renderThread);
                    run.start();
                }
            }


        }
    }
}
