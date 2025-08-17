package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shiled_Blue extends Entity {

    public OBJ_Shiled_Blue(GamePanel gp) {
        super(gp);

        type = type_shield;
        name = "Blue Shield";
        down1 = setup("/objects/shield_blue", GamePanel.tileSize,GamePanel.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nA shiny blue shield.";
    }
}
