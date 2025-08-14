package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends SuperObject {

    public OBJ_Heart() {
        name = "Heart";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/heart_full.png"));
            image = uTool.scaleImage(image, GamePanel.tileSize, GamePanel.tileSize);

            image2 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_half.png"));
            image2 = uTool.scaleImage(image2, GamePanel.tileSize, GamePanel.tileSize);

            image3 = ImageIO.read(getClass().getResourceAsStream("/objects/heart_blank.png"));
            image3 = uTool.scaleImage(image3, GamePanel.tileSize, GamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(1);
        }
    }

}
