package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCancel = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxIntventorySize = 20;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        screenX = GamePanel.screenWidth / 2 - (GamePanel.tileSize / 2);
        screenY = GamePanel.screenHeight / 2  - (GamePanel.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        // attackArea.width = 36;
        // attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    private void setDefaultValues() {
        worldX = GamePanel.tileSize * 23;
        worldY = GamePanel.tileSize * 21;
        // worldX = GamePanel.tileSize * 10;
        // worldY = GamePanel.tileSize * 13;
        speed = 4;
        direction = "down";

        // PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 1; // The more strength hr has, the more damage he gives
        dexterity = 1; // The more dexterity he has, the less damge he receives
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        // currentWeapon = new OBJ_Sword_Normal(gp);
        currentWeapon = new OBJ_Axe(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        // projectile = new OBJ_Rock(gp);
        attack = getAttack(); // The total attack value is decided by strebgth and weapon
        defense = getDefense(); // The total defense value is decided by dexterity and shield
    }

    public void setItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }

    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage() {

        up1 = setup("/player/boy_up_1", GamePanel.tileSize,  GamePanel.tileSize);
        up2 = setup("/player/boy_up_2", GamePanel.tileSize,  GamePanel.tileSize);
        down1 = setup("/player/boy_down_1", GamePanel.tileSize,  GamePanel.tileSize);
        down2 = setup("/player/boy_down_2", GamePanel.tileSize,  GamePanel.tileSize);
        left1 = setup("/player/boy_left_1", GamePanel.tileSize,  GamePanel.tileSize);
        left2 = setup("/player/boy_left_2", GamePanel.tileSize,  GamePanel.tileSize);
        right1 = setup("/player/boy_right_1", GamePanel.tileSize,  GamePanel.tileSize);
        right2 = setup("/player/boy_right_2", GamePanel.tileSize,  GamePanel.tileSize);

    }

    public void getPlayerAttackImage() {
        if(currentWeapon.type == type_sword) {
            attackUp1 = setup("/player/boy_attack_up_1", GamePanel.tileSize,  GamePanel.tileSize * 2);
            attackUp2 = setup("/player/boy_attack_up_2", GamePanel.tileSize,  GamePanel.tileSize * 2);
            attackDown1 = setup("/player/boy_attack_down_1", GamePanel.tileSize,  GamePanel.tileSize * 2);
            attackDown2 = setup("/player/boy_attack_down_2", GamePanel.tileSize,  GamePanel.tileSize * 2);
            attackLeft1 = setup("/player/boy_attack_left_1", GamePanel.tileSize * 2,  GamePanel.tileSize);
            attackLeft2 = setup("/player/boy_attack_left_2", GamePanel.tileSize * 2,  GamePanel.tileSize);
            attackRight1 = setup("/player/boy_attack_right_1", GamePanel.tileSize * 2,  GamePanel.tileSize);
            attackRight2 = setup("/player/boy_attack_right_2", GamePanel.tileSize * 2,  GamePanel.tileSize);
        } else if(currentWeapon.type == type_axe) {
            attackUp1 = setup("/player/boy_axe_up_1", GamePanel.tileSize,  GamePanel.tileSize * 2);
            attackUp2 = setup("/player/boy_axe_up_2", GamePanel.tileSize,  GamePanel.tileSize * 2);
            attackDown1 = setup("/player/boy_axe_down_1", GamePanel.tileSize,  GamePanel.tileSize * 2);
            attackDown2 = setup("/player/boy_axe_down_2", GamePanel.tileSize,  GamePanel.tileSize * 2);
            attackLeft1 = setup("/player/boy_axe_left_1", GamePanel.tileSize * 2,  GamePanel.tileSize);
            attackLeft2 = setup("/player/boy_axe_left_2", GamePanel.tileSize * 2,  GamePanel.tileSize);
            attackRight1 = setup("/player/boy_axe_right_1", GamePanel.tileSize * 2,  GamePanel.tileSize);
            attackRight2 = setup("/player/boy_axe_right_2", GamePanel.tileSize * 2,  GamePanel.tileSize);
        }

    }

    public void update() {

        if(attacking) {

            attacking();

        } else if(keyH.downPressed ||  keyH.upPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
            if(keyH.upPressed) {
                direction = "up";
            } else if(keyH.downPressed) {
                direction = "down";
            }
            if(keyH.leftPressed) {
                direction = "left";
            } else if(keyH.rightPressed) {
                direction = "right";
            }


            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objectIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // CHECK INTERACTIVE TILE COLLISION
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);


            // CHECK EVENT
            gp.eventHandler.checkEvent();



            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(!collisionOn && !keyH.enterPressed) {
                switch (direction) {
                    case "up": {
                        worldY -= speed;
                        break;
                    }

                    case "down": {
                        worldY += speed;
                        break;
                    }

                    case "left": {
                        worldX -= speed;
                        break;
                    }

                    case "right": {
                        worldX += speed;
                        break;
                    }
                }
            }

            if(keyH.enterPressed && !attackCancel) {
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCancel  = false;
            gp.keyHandler.enterPressed = false;

            spriteCounter++;
            if(spriteCounter > 10) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                } else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            standCounter++;
            if(standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }

        if(gp.keyHandler.shotKeyPressed && !projectile.alive && shotAvailableCounter == 30 && projectile.haveResource(this)) {

            // SET DEAFULT COORDINATES, DIRECTION AND USER
            projectile.set(worldX, worldY, direction, true, this);

            // SUBSTRACT THE COST (MANA, AMMO, ETC)
            projectile.substracrResource(this);

            // ADD IT TO THE LIST
            gp.projectileList.add(projectile);

            shotAvailableCounter = 0;

            gp.playSE(10);

        }

        // This needs to be outside of the key if statement!
        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        if(life > maxLife) {
            life = maxLife;
        }
        if(mana > maxMana) {
            mana = maxMana;
        }

    }

    public void attacking() {
        spriteCounter++;
        if(spriteCounter <= 5) {
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            // Save the current WorldX, WorldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's worldX/Y for the attackArea
            switch(direction) {
                case "up": {
                    worldY -= attackArea.height;
                    break;
                }

                case "down": {
                    worldY += attackArea.height;
                    break;
                }

                case "left": {
                    worldX -= attackArea.width;
                    break;
                }

                case "right": {
                    worldX += attackArea.width;
                    break;
                }
            }

            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            // Check monster collision with the updated worldX, worldY and solidArea
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if(spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void damageInteractiveTile(int i) {
        if(i != 999 && gp.iTile[i].destructible && gp.iTile[i].isCorrectItem(this) && !gp.iTile[i].invincible) {
            gp.iTile[i].playSE();
            gp.iTile[i].life--;
            gp.iTile[i].invincible = true;

            if(gp.iTile[i].life == 0) {
                gp.iTile[i] = gp.iTile[i].getDestroyedForm();
            }
        }
    }

    public void contactMonster(int i) {
        if(i != 999) {

            if(!invincible && !gp.monster[i].dying) {
                gp.playSE(6);

                int damage = gp.monster[i].attack - defense;
                if(damage < 0) {
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }

        }
    }

    public void damageMonster(int i, int attack) {
        if(i != 999) {
            if(!gp.monster[i].invincible) {
                gp.playSE(5);

                int damage = attack - gp.monster[i].defense;
                if(damage < 0) {
                    damage = 0;
                }

                gp.monster[i].life -= damage;
                gp.ui.addMessage(damage + " damage!");

                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if(gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("killed the " + gp.monster[i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[i].exp);
                    exp += gp.monster[i].exp;
                    checkLevelUp();
                }
            }
        }
    }

    public void checkLevelUp() {
        if(exp >= nextLevelExp) {
            level++;
            nextLevelExp *= 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level " + level + " now!\n" + "You feel stronger!";
        }
    }

    public void pickUpObject(int i) {
        if(i != 999) {


            // PICKUP ONLY ITEMS
            if(gp.obs[i].type == type_pickupOnly) {

                gp.obs[i].use(this);
                gp.obs[i] = null;
            }

            // INVENTORY ITEMS
            else {
                String text;

                if(inventory.size() != maxIntventorySize) {
                    inventory.add(gp.obs[i]);
                    gp.playSE(1);
                    text = "Got a " + gp.obs[i].name + "!";
                } else {
                    text = "You cannot carry any more!";
                }
                gp.ui.addMessage(text);
                gp.obs[i] = null;
            }


        }
    }

    public void interactNPC(int i) {

        if(gp.keyHandler.enterPressed) {
            if(i != 999) {
                attackCancel = true;
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();

            }
        }
    }

    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot();
        if(itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield) {
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable) {
                selectedItem.use(this);
                inventory.remove(itemIndex);

            }

        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up": {
                if(!attacking) {
                    if(spriteNum == 1) {
                        image = up1;
                    }
                    if(spriteNum == 2) {
                        image = up2;
                    }
                } else {
                    tempScreenY -= GamePanel.tileSize;
                    if(spriteNum == 1) {
                        image = attackUp1;
                    }
                    if(spriteNum == 2) {
                        image = attackUp2;
                    }
                }

                break;
            }

            case "down": {
                if(!attacking) {
                    if(spriteNum == 1) {
                        image = down1;
                    }
                    if(spriteNum == 2) {
                        image = down2;
                    }
                } else {
                    if(spriteNum == 1) {
                        image = attackDown1;
                    }
                    if(spriteNum == 2) {
                        image = attackDown2;
                    }
                }

                break;
            }

            case "left": {
                if(!attacking) {
                    if(spriteNum == 1) {
                        image = left1;
                    }
                    if(spriteNum == 2) {
                        image = left2;
                    }
                } else {
                    tempScreenX -= GamePanel.tileSize;
                    if(spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if(spriteNum == 2) {
                        image = attackLeft2;
                    }
                }

                break;
            }

            case "right": {
                if(!attacking) {
                    if(spriteNum == 1) {
                        image = right1;
                    }
                    if(spriteNum == 2) {
                        image = right2;
                    }
                } else {
                    if(spriteNum == 1) {
                        image = attackRight1;
                    }
                    if(spriteNum == 2) {
                        image = attackRight2;
                    }
                }

                break;
            }
        }

        if(invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY,null);

        // Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}
