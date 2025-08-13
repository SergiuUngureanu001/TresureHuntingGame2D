package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends SuperObject {

    public OBJ_Boots() {
        name = "Boots";
        try {

            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
            image = uTool.scaleImage(image, GamePanel.tileSize, GamePanel.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(1);
        }
    }

}
