package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {


    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);

        type = type_pickupOnly;
        name = "Mana Crystal";
        value = 1;
        down1 = setup("/objects/manacrystal_full", GamePanel.tileSize, GamePanel.tileSize);
        image = setup("/objects/manacrystal_full", GamePanel.tileSize, GamePanel.tileSize);
        image2 = setup("/objects/manacrystal_blank", GamePanel.tileSize, GamePanel.tileSize);
    }

    public void use(Entity entity) {
        gp.playSE(2);
        gp.ui.addMessage("Mana + " + value);
        entity.mana += value;
    }
}
