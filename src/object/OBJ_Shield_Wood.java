package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {

    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        name = "Wood Shield";
        down1 = setup("/objects/shield_wood", GamePanel.tileSize, GamePanel.tileSize);
        defenseValue = 1;
    }
}
