package main;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, purisaB;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank;
    public boolean messageOn = false;
    // public String message = "";
    // int messageCounter = 0;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: the first screen, 1: the second screen
    public int slotCol = 0;
    public int slotRow = 0;

    // double playTime;
    // DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;


        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (FontFormatException e) {
            e.printStackTrace();
            System.out.println(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(1);
        }

        // CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
    }

    public void addMessage(String text) {

        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.white);


        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // PLAY STATE
        if(gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }
        // PAUSE STATE
        if(gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }
        // CHARACTER STATE
        if(gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory();
        }
    }

    public void drawMessage() {

        int messageX = GamePanel.tileSize;
        int messageY = GamePanel.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32));

        for(int i = 0; i < message.size(); i++) {
            if(message.get(i) != null) {

                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; // messageCounter++;
                messageCounter.set(i, counter); // set the counter to the array
                messageY += 50;

                if(messageCounter.get(i) > 180) {
                    messageCounter.remove(i);
                    message.remove(i);
                }
            }
        }
    }

    public void drawPlayerLife() {

        int x = GamePanel.tileSize / 2;
        int y = GamePanel.tileSize / 2;
        int i = 0;

        // DRAW MAX LIFE
        while(i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += GamePanel.tileSize;
        }

        // RESET
        x = GamePanel.tileSize / 2;
        y = GamePanel.tileSize / 2;
        i = 0;

        // DRAW CURRENT LIFE
        while(i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += GamePanel.tileSize;
        }
        // DRAW MAX MANA
        x = GamePanel.tileSize / 2 - 5;
        y = (int)(GamePanel.tileSize * 1.5);
        i = 0;

        while(i < gp.player.maxMana) {
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }

        // DRAW MANA
        x = GamePanel.tileSize / 2 - 5;
        y = (int)(GamePanel.tileSize * 1.5);
        i = 0;
        while(i < gp.player.mana) {
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
        }
    }

    public void drawTitleScreen() {

        if(titleScreenState == 0) {
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, GamePanel.screenWidth, GamePanel.screenHeight);

            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
            String text = "Blue Boy Adventure";
            int x = getXforCenteredText(text);
            int y = GamePanel.tileSize * 3;

            // SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x + 5, y + 5);

            // MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // BLUE BOY IMAGE
            x = GamePanel.screenWidth / 2 -  GamePanel.tileSize;
            y = GamePanel.screenHeight / 2 - 2 * GamePanel.tileSize;
            g2.drawImage(gp.player.down1, x, y, GamePanel.tileSize * 2, GamePanel.tileSize * 2, null);

            // MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y +=  GamePanel.tileSize * 3.5;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawString(">", x - GamePanel.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y +=  GamePanel.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x - GamePanel.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y +=  GamePanel.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x - GamePanel.tileSize, y);
            }
        } else if(titleScreenState == 1) {
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42f));

            String text = "Select your class!";
            int x = getXforCenteredText(text);
            int y = GamePanel.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Fighter";
            x = getXforCenteredText(text);
            y += GamePanel.tileSize * 3;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawString(">", x - GamePanel.tileSize, y);
            }

            text = "Thief";
            x = getXforCenteredText(text);
            y += GamePanel.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x - GamePanel.tileSize, y);
            }

            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += GamePanel.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x - GamePanel.tileSize, y);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y += GamePanel.tileSize * 2;
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.drawString(">", x - GamePanel.tileSize, y);
            }
        }

    }

    public void drawDialogueScreen() {
        // WINDOW
        int x = GamePanel.tileSize * 2;
        int y = GamePanel.tileSize / 2;
        int width = GamePanel.screenWidth - (GamePanel.tileSize * 4);
        int height = GamePanel.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32f));
        x += GamePanel.tileSize;
        y += GamePanel.tileSize;

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }


    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);


    }

    public void drawCharacterScreen() {
        // CREATE A FRAME
        final int frameX = GamePanel.tileSize * 2;
        final int frameY =  GamePanel.tileSize;
        final int frameWidth = GamePanel.tileSize * 5;
        final int frameHeight =  GamePanel.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32f));

        int textX = frameX + 20;
        int textY = frameY + GamePanel.tileSize;
        final int lineHeight = 35;

        // NAMES
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 10;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);
        textY += lineHeight;

        // VALUES
        int tailX = (frameX + frameWidth) - 30;
        // Reset textY
        textY = frameY + GamePanel.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - GamePanel.tileSize, textY - 20, null);
        textY += lineHeight;
        g2.drawImage(gp.player.currentShield.down1, tailX - GamePanel.tileSize, textY - 15, null);

    }

    public void drawInventory() {

        // FRAME
        int frameX = GamePanel.tileSize * 12;
        int frameY = GamePanel.tileSize;
        int frameWidth = GamePanel.tileSize * 6 - 10;
        int frameHeight = GamePanel.tileSize * 5 - 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY =  slotYstart;
        int slotSize = GamePanel.tileSize;

        // DRAW PLAYER'S ITMES
        for(int i = 0; i < gp.player.inventory.size(); i++) {

            if(gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, GamePanel.tileSize, GamePanel.tileSize, 10, 10);
            }

            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // CURSOR
        int cursorX = slotXstart + (GamePanel.tileSize * slotCol);
        int cursorY = slotYstart + (GamePanel.tileSize * slotRow);
        int cursorWidth = GamePanel.tileSize;
        int cursorHeight = GamePanel.tileSize;

        // DRAW CURSOR
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // DESCRIPTION FRAME
        int dFrameX = frameX;
        int dFrameY =  frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = GamePanel.tileSize * 3;
        // DRAW DESCRIPTION TEXT
        int textX = dFrameX + 20;
        int textY = dFrameY + GamePanel.tileSize;
        g2.setFont(g2.getFont().deriveFont(28f));

        int itemIndex = getItemIndexOnSlot();

        if(itemIndex < gp.player.inventory.size()) {

            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

            for(String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }

    }

    public int getItemIndexOnSlot() {
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }

    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,  80f));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = GamePanel.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = GamePanel.screenWidth / 2 - length / 2;
        return x;
    }

    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }

}
