package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends SuperObject {

    public OBJ_Chest() {

        name = "Chest";
        try {

            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
            image = uTool.scaleImage(image, GamePanel.tileSize, GamePanel.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(1);
        }
    }
}
