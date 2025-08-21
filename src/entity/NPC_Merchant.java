package entity;

import main.GamePanel;
import object.*;

public class NPC_Merchant extends Entity {

    public NPC_Merchant(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {
        up1 = setup("/npc/merchant_down_1", GamePanel.tileSize,  GamePanel.tileSize);
        up2 = setup("/npc/merchant_down_2", GamePanel.tileSize,  GamePanel.tileSize);
        down1 = setup("/npc/merchant_down_1", GamePanel.tileSize,  GamePanel.tileSize);
        down2 = setup("/npc/merchant_down_2", GamePanel.tileSize,  GamePanel.tileSize);
        left1 = setup("/npc/merchant_down_1", GamePanel.tileSize,  GamePanel.tileSize);
        left2 = setup("/npc/merchant_down_2", GamePanel.tileSize,  GamePanel.tileSize);
        right1 = setup("/npc/merchant_down_1", GamePanel.tileSize,  GamePanel.tileSize);
        right2 = setup("/npc/merchant_down_2", GamePanel.tileSize,  GamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "He he, so you found me.\nI have some good stuff.\n Do thou want to trade?";
    }

    public void setItems() {
        inventory.clear();
        inventory.add(new OBJ_Potion_Red(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Sword_Normal(gp));
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Shield_Wood(gp));
        inventory.add(new OBJ_Shiled_Blue(gp));
    }

    public void speak() {
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
