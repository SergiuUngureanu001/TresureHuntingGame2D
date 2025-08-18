package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {

    public OBJ_Heart(GamePanel gp) {

        super(gp);

        type = type_pickupOnly;
        name = "Heart";
        value = 2;
        down1 = setup("/objects/heart_full", GamePanel.tileSize,  GamePanel.tileSize);
        image = setup("/objects/heart_full", GamePanel.tileSize,  GamePanel.tileSize);
        image2 = setup("/objects/heart_half", GamePanel.tileSize,  GamePanel.tileSize);
        image3 = setup("/objects/heart_blank", GamePanel.tileSize,  GamePanel.tileSize);

    }

    public void use(Entity entity) {
        gp.playSE(2);
        gp.ui.addMessage("Life + " + value);
        entity.life += value;
    }

}
