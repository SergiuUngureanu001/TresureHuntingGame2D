package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {
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
    }

    public void getImage() {
        up1 = setup("/npc/oldman_up_1", GamePanel.tileSize,  GamePanel.tileSize);
        up2 = setup("/npc/oldman_up_2", GamePanel.tileSize,  GamePanel.tileSize);
        down1 = setup("/npc/oldman_down_1", GamePanel.tileSize,  GamePanel.tileSize);
        down2 = setup("/npc/oldman_down_2", GamePanel.tileSize,  GamePanel.tileSize);
        left1 = setup("/npc/oldman_left_1", GamePanel.tileSize,  GamePanel.tileSize);
        left2 = setup("/npc/oldman_left_2", GamePanel.tileSize,  GamePanel.tileSize);
        right1 = setup("/npc/oldman_right_1", GamePanel.tileSize,  GamePanel.tileSize);
        right2 = setup("/npc/oldman_right_2", GamePanel.tileSize,  GamePanel.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "Hello, lad";
        dialogues[1] = "So you've come to \nthis island to find some treasure";
        dialogues[2] = "I used to be a great wizard but now... \nI'm a bit too old for taking an adventure";
        dialogues[3] = "Well, good luck on you.";
    }

    public void setAction() {

        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

            if(i <= 25) {
                direction = "up";
            }
            if(i > 25 && i <= 50) {
                direction = "down";
            }
            if(i > 50 && i <= 75) {
                direction = "left";
            }
            if(i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    public void speak() {

        // Do this character specific stuff

        super.speak();
    }

}
