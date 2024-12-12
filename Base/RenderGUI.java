package Base;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import javax.swing.*;

import Base.RenderComponents.Settings;
public class RenderGUI extends JFrame implements KeyListener {
    Render render;
    Canvas renderCanvas;
    JButton renderButton;
    JSlider ReflectiveSlider;
    JSlider jumpSlider;
    double xRot;
    double yRot;
    double zRot;
    double[] cameraOrgin;
    public RenderGUI(){
        super("RenderScreen");

        //set default
        xRot = 0;
        yRot = 0;
        zRot = 0;
        cameraOrgin = new double[]{0,0,-25};


        setLayout(new FlowLayout(FlowLayout.LEADING));
        JPanel options = new JPanel(new GridLayout(0,3));

        renderButton = new JButton("Render");
        options.add(renderButton);
        renderButton.addActionListener(new renderButtonLisitiner());
        renderButton.setFocusable(false);

        ReflectiveSlider = new JSlider(0, 6, 3);
        ReflectiveSlider.setFocusable(false);
        options.add(ReflectiveSlider);

        jumpSlider = new JSlider(1, 25, 10);
        jumpSlider.setFocusable(false);
        options.add(jumpSlider);

        render = new Render();
        renderCanvas = new Canvas(Settings.cW, Settings.cH);

        options.setSize(250,1000);
        setSize(1050,1100);

        renderCanvas.setVisible(true);
        options.setVisible(true);
        add(options);
        addKeyListener(this);
        setFocusable(true);
        add(renderCanvas);
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            yRot+= (double) jumpSlider.getValue()/25;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            yRot-=(double) jumpSlider.getValue()/25;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            xRot-=(double) jumpSlider.getValue()/25;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            xRot+=(double) jumpSlider.getValue()/25;
        }
        if (e.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET) {
            zRot+=(double) jumpSlider.getValue()/25;
        }
        if (e.getKeyCode() == KeyEvent.VK_OPEN_BRACKET) {
            zRot-=(double) jumpSlider.getValue()/25;
        }
        if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
            cameraOrgin[2]+=1;
        }
        if (e.getKeyCode() == KeyEvent.VK_MINUS) {
            cameraOrgin[2]-=1;
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
            cameraOrgin[1]+=1;
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
            cameraOrgin[1]-=1;
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
            cameraOrgin[0]+=1;
        }
        if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
            cameraOrgin[0]-=1;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int reflRecursion = ReflectiveSlider.getValue();

        int threadCount =  Settings.thread_count;
        if (threadCount % 2 != 0) threadCount++;

        int xBlockSize = threadCount/2;
        int yBlockSize = 2;

        int xSegSize = Settings.cW/xBlockSize;
        int ySegSize = Settings.cH/yBlockSize;

        for (int i = 0; i < yBlockSize; i++){
            for (int j = 0; j < xBlockSize; j++){

                // min and max screen numbers
                //xMin = -Settings.cW/2
                //xMax = Settings.cW/2
                //yMin = -Settings.cH/2 + 1
                //yMax = Settings.cH/2;

                int xMin = j *xSegSize - Settings.cW/2;
                int xMax = (j +1)*xSegSize - Settings.cW/2;
                int yMin = (i *ySegSize) - Settings.cH/2;
                int yMax = (i +1)*ySegSize -  Settings.cH/2 + 1;

                Runnable renderThread = () ->
                {
                    render.render(renderCanvas, cameraOrgin, xRot, yRot, zRot, reflRecursion, xMin, xMax, yMin, yMax);
                };
                Thread run = new Thread(renderThread);
                run.start();
            }
        }

    }

    private class renderButtonLisitiner implements ActionListener{
        public void actionPerformed(ActionEvent e){
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
                        render.render(renderCanvas, cameraOrgin, xRot, yRot, zRot, reflRecursion, xMin, xMax, yMin, yMax);
                    };
                    Thread run = new Thread(renderThread);
                    run.start();
                }
            }


        }
    }
}
