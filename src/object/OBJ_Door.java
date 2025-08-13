package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends SuperObject{

    public OBJ_Door() {

        name = "Door";
        try {

            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
            image = uTool.scaleImage(image, GamePanel.tileSize, GamePanel.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(1);
        }
        collision = true;
    }

}
