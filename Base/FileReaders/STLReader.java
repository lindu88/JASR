package Base.FileReaders;
import Base.Primitives.Surface;
import Base.Primitives.Triangle;
import Base.RenderComponents.Color;

import java.io.File;
import java.util.Scanner;
public class STLReader {
    //need to add support for pre calculated normals
    public static Surface getListASCII(String filePath, Color color, double specular, double reflective){
        Surface object = new Surface();
        File file = new File(filePath);
        try {
            Scanner s = new Scanner(file);
            while(s.hasNextLine()){
                String type = s.nextLine();
                System.out.println(type);
                if (type.trim().equals("outer loop")){
                    String vStringOne = s.nextLine().trim();
                    String vStringTwo = s.nextLine().trim();
                    String vStringThree = s.nextLine().trim();

                    String[] vOneArr = vStringOne.split("\\s+");
                    String[] vTwoArr = vStringTwo.split("\\s+");
                    String[] vThreeArr = vStringThree.split("\\s+");
                    
                    double v1a = Double.parseDouble(vOneArr[1]);
                    double v1b = Double.parseDouble(vOneArr[2]);
                    double v1c = Double.parseDouble(vOneArr[3]);
                    double[] v1 = new double[]{v1a,v1b,v1c};

                    double v2a = Double.parseDouble(vTwoArr[1]);
                    double v2b = Double.parseDouble(vTwoArr[2]);
                    double v2c = Double.parseDouble(vTwoArr[3]);
                    double[] v2 = new double[]{v2a,v2b,v2c};

                    double v3a = Double.parseDouble(vThreeArr[1]);
                    double v3b = Double.parseDouble(vThreeArr[2]);
                    double v3c = Double.parseDouble(vThreeArr[3]);
                    double[] v3 = new double[]{v3a,v3b,v3c};

                    object.addLast(new Triangle(v1,v2,v3,color,specular,reflective));
                }
            }
            s.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        return object;
    }
}
