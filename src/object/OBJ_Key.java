package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends SuperObject{

    public OBJ_Key(){
        name = "Key";
        try {

            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
            image = uTool.scaleImage(image, GamePanel.tileSize, GamePanel.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(1);
        }
    }
}
