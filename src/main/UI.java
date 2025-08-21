package main;

import entity.Entity;
import object.OBJ_Coin_Bronze;
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
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
    public boolean messageOn = false;
    // public String message = "";
    // int messageCounter = 0;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: the first screen, 1: the second screen
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotRow = 0;
    public int npcSlotCol = 0;
    int subState = 0;
    int counter = 0;
    public Entity npc;

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
        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin = bronzeCoin.down1;
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
            gp.stopMusic();
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
            drawInventory(gp.player, true);
        }
        // OPTION STATE
        if(gp.gameState == gp.optionState) {
            drawOptionsState();
        }
        // GAME OVER STATE
        if(gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
        // TRANSITION STATE
        if(gp.gameState == gp.transitionState) {
            drawTransition();
        }
        // TRADE STATE
        if(gp.gameState == gp.tradeState) {
            drawTradeScreen();
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
        int x = GamePanel.tileSize * 3;
        int y = GamePanel.tileSize / 2;
        int width = GamePanel.screenWidth - (GamePanel.tileSize * 6);
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

    public void drawInventory(Entity entity, boolean cursor) {


        int frameX;
        int frameY;
        int frameWidth;
        int frameHeight;
        int slotCol;
        int slotRow;

        if(entity == gp.player) {
            frameX = GamePanel.tileSize * 12;
            frameY = GamePanel.tileSize;
            frameWidth = GamePanel.tileSize * 6 - 10;
            frameHeight = GamePanel.tileSize * 5 - 10;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            frameX = GamePanel.tileSize * 2;
            frameY = GamePanel.tileSize;
            frameWidth = GamePanel.tileSize * 6;
            frameHeight = GamePanel.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        // FRAME


        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY =  slotYstart;
        int slotSize = GamePanel.tileSize;

        // DRAW PLAYER'S ITMES
        for(int i = 0; i < entity.inventory.size(); i++) {

            if(entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, GamePanel.tileSize, GamePanel.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // CURSOR
        if(cursor) {
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

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if(itemIndex < entity.inventory.size()) {

                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

                for(String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }


    }

    public void drawOptionsState() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32f));

        // SUB WINDOW
        int frameX =  GamePanel.tileSize * 6;
        int frameY = GamePanel.tileSize;
        int frameWidth = GamePanel.tileSize * 8;
        int frameHeight = GamePanel.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0: {
                option_top(frameX, frameY);
                break;
            }

            case 1: {
                options_fullScreenNotification(frameX, frameY);
                break;
            }

            case 2: {
                options_control(frameX, frameY);
                break;
            }

            case 3: {
                options_endGameConfirmation(frameX, frameY);
                break;
            }
        }
        gp.keyHandler.enterPressed = false;
    }

    public void option_top(int frameX, int frameY) {
        int textX;
        int textY;

        // TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + GamePanel.tileSize;
        g2.drawString(text, textX, textY);

        // FULL SCREEN ON/OFF
        text = "Full Screen";
        textX = frameX + GamePanel.tileSize;
        textY += frameY + GamePanel.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25,  textY);
            if(gp.keyHandler.enterPressed) {
                if(!gp.fullScreenOn) {
                    gp.fullScreenOn = true;
                } else if(gp.fullScreenOn) {
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }

        // MUSIC
        text = "Music";
        textY += GamePanel.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX - 25,  textY);
        }

        // SE
        text = "SE";
        textY += GamePanel.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 2) {
            g2.drawString(">", textX - 25,  textY);
        }

        // CONTROL
        text = "Control";
        textY += GamePanel.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 3) {
            g2.drawString(">", textX - 25,  textY);
            if(gp.keyHandler.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }

        // END GAME
        text = "End Game";
        textY += GamePanel.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 4) {
            g2.drawString(">", textX - 25,  textY);
            if(gp.keyHandler.enterPressed) {
                subState = 3;
                commandNum = 0;
            }
        }

        // BACK
        text = "Back";
        textY += GamePanel.tileSize * 2;
        g2.drawString(text, textX, textY);
        if(commandNum == 5) {
            g2.drawString(">", textX - 25,  textY);
            if(gp.keyHandler.enterPressed) {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }

        // FULL SCREEN CHECK BOX
        textX = frameX + (int)(GamePanel.tileSize * 4.5);
        textY = frameY + GamePanel.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if(gp.fullScreenOn) {
            g2.fillRect(textX, textY, 24, 24);
        }

        // MUSIC VOLUME
        textY += GamePanel.tileSize;
        g2.drawRect(textX, textY, 120, 24); // 120 / 5 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // SE VOLUME
        textY += GamePanel.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }

    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + GamePanel.tileSize;
        int textY = frameY + GamePanel.tileSize * 3;

        currentDialogue = "The change will take \neffect after restarting \nthe game.";
        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // BACK
        textY = frameY + GamePanel.tileSize * 9;
        g2.drawString("Back", textX,  textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25,  textY);
            if(gp.keyHandler.enterPressed) {
                subState = 0;
            }
        }
    }

    public void options_control(int frameX, int frameY) {

        int textX;
        int textY;

        // TITLE
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + GamePanel.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + GamePanel.tileSize;
        textY += GamePanel.tileSize;
        g2.drawString("Move", textX, textY);
        textY += GamePanel.tileSize;
        g2.drawString("Confirm/Attack", textX, textY);
        textY += GamePanel.tileSize;
        g2.drawString("Shoot/Cast", textX, textY);
        textY += GamePanel.tileSize;
        g2.drawString("Character Screen", textX, textY);
        textY += GamePanel.tileSize;
        g2.drawString("Pause", textX, textY);
        textY += GamePanel.tileSize;
        g2.drawString("Options", textX, textY);
        textY += GamePanel.tileSize;

        textX = frameX + GamePanel.tileSize * 6;
        textY = frameY + GamePanel.tileSize * 2;
        g2.drawString("WASD", textX, textY);
        textY += GamePanel.tileSize;
        g2.drawString("ENTER", textX, textY);
        textY += GamePanel.tileSize;
        g2.drawString("F", textX, textY);
        textY += GamePanel.tileSize;
        g2.drawString("C", textX, textY);
        textY += GamePanel.tileSize;
        g2.drawString("P", textX, textY);
        textY += GamePanel.tileSize;
        g2.drawString("ESC", textX, textY);
        textY += GamePanel.tileSize;

        // BACK
        textX = frameX + GamePanel.tileSize;
        textY = frameY + GamePanel.tileSize * 9;
        g2.drawString("Back", textX,  textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25,  textY);
            if(gp.keyHandler.enterPressed) {
                subState = 0;
                commandNum = 3;
            }
        }

    }

    public void options_endGameConfirmation(int  frameX, int frameY) {

        int textX =  frameX + GamePanel.tileSize;
        int textY = frameY + GamePanel.tileSize * 3;

        currentDialogue = "Quit the game and \nreturn to the title screen?";
        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // YES
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += GamePanel.tileSize * 3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25,  textY);
            if(gp.keyHandler.enterPressed) {
                subState = 0;
                titleScreenState = 0;
                gp.gameState = gp.titleState;
            }
        }

        // NO
        text = "No";
        textX = getXforCenteredText(text);
        textY += GamePanel.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX - 25,  textY);
            if(gp.keyHandler.enterPressed) {
                subState = 0;
                commandNum  = 4;
            }
        }

    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, GamePanel.screenWidth, GamePanel.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "Game Over";
        // Shadow
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = GamePanel.tileSize * 4;
        g2.drawString(text, x, y);
        // Main
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        // Retry

        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += GamePanel.tileSize * 4;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x - 40,  y);
        }

        // Back to the title screen
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x - 40,  y);
        }
    }

    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, GamePanel.screenWidth, GamePanel.screenHeight);

        if(counter == 50) {
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eventHandler.tempMap;
            gp.player.worldX = GamePanel.tileSize * gp.eventHandler.tempCol;
            gp.player.worldY = GamePanel.tileSize * gp.eventHandler.tempRow;
            gp.eventHandler.previousEventX = gp.player.worldX;
            gp.eventHandler.previousEventY = gp.player.worldY;

        }
    }

    public void drawTradeScreen() {
        switch (subState) {
            case 0: {
                trade_select();
                break;
            }

            case 1: {
                trade_buy();
                break;
            }

            case 2: {
                trade_sell();
                break;
            }
        }
    }

    public void trade_select() {
        drawDialogueScreen();

        // DRAW WINDOW
        int x = GamePanel.tileSize * 15;
        int y = GamePanel.tileSize * 4;
        int width = GamePanel.tileSize * 3;
        int height = (int)(GamePanel.tileSize * 3.5);
        drawSubWindow(x,y,width,height);

        // DRAW TEXTS
        x += GamePanel.tileSize;
        y += GamePanel.tileSize;
        g2.drawString("Buy", x, y);
        if(commandNum == 0) {
            g2.drawString(">", x - 24, y);
            if(gp.keyHandler.enterPressed) {
                subState = 1;
                gp.keyHandler.enterPressed = false;
            }
        }
        y += GamePanel.tileSize;
        g2.drawString("Sell", x, y);
        if(commandNum == 1) {
            g2.drawString(">", x - 24, y);
            if(gp.keyHandler.enterPressed) {
                subState = 2;
                gp.keyHandler.enterPressed = false;
            }
        }
        y +=  GamePanel.tileSize;
        g2.drawString("Leave", x, y);
        if(commandNum == 2) {
            g2.drawString(">", x - 24, y);
            if(gp.keyHandler.enterPressed) {
                commandNum = 0;
                gp.gameState = gp.dialogueState;
                currentDialogue = "Come again, hehe!";
            }
        }
    }

    public void trade_buy() {
        // DRAW PLAYER INVENTORY
        drawInventory(gp.player, false);
        // DRAW NPC INVENTORY
        drawInventory(npc, true);

        // DRAW HINT WINDOW
        int x = GamePanel.tileSize * 2;
        int y = GamePanel.tileSize * 9;
        int width = GamePanel.tileSize * 6;
        int height = GamePanel.tileSize * 2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Back", x + 24, y + 60);

        // DRAW PLAYER COINT WINDOW
        x = GamePanel.tileSize * 12;
        y = GamePanel.tileSize * 9;
        width = GamePanel.tileSize * 6;
        height = GamePanel.tileSize * 2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60);

        // DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()) {
            x = (int)(GamePanel.tileSize * 5.5);
            y = (int)(GamePanel.tileSize * 5.5);
            width = (int)(GamePanel.tileSize * 2.5);
            height = GamePanel.tileSize;
            drawSubWindow(x,y,width,height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlignToRightText(text, GamePanel.tileSize * 8 - 20);
            g2.drawString(text, x, y + 34);

            // BUY AN ITEM
            if(gp.keyHandler.enterPressed) {
                if(npc.inventory.get(itemIndex).price > gp.player.coin) {
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You need more coin to buy that!";
                    drawDialogueScreen();
                } else if(gp.player.inventory.size() == gp.player.maxIntventorySize) {
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You cannot carry any more!";
                } else {
                    gp.player.coin -= npc.inventory.get(itemIndex).price;
                    gp.player.inventory.add(npc.inventory.get(itemIndex));
                    gp.keyHandler.enterPressed = false;
                }
            }
        }
    }

    public void trade_sell() {

        // DRAW PLAYER INVENTORY
        drawInventory(gp.player, true);

        int x;
        int y;
        int width;
        int height;

        // DRAW HINT WINDOW
        x = GamePanel.tileSize * 2;
        y = GamePanel.tileSize * 9;
        width = GamePanel.tileSize * 6;
        height = GamePanel.tileSize * 2;
        drawSubWindow(x,y,width,height);
        g2.drawString("[ESC] Back", x + 24, y + 60);

        // DRAW PLAYER COINT WINDOW
        x = GamePanel.tileSize * 12;
        y = GamePanel.tileSize * 9;
        width = GamePanel.tileSize * 6;
        height = GamePanel.tileSize * 2;
        drawSubWindow(x,y,width,height);
        g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60);

        // DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if(itemIndex < gp.player.inventory.size()) {
            x = (int)(GamePanel.tileSize * 15.5);
            y = (int)(GamePanel.tileSize * 5.5);
            width = (int)(GamePanel.tileSize * 2.5);
            height = GamePanel.tileSize;
            drawSubWindow(x,y,width,height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = gp.player.inventory.get(itemIndex).price / 2;
            String text = "" + price;
            x = getXforAlignToRightText(text, GamePanel.tileSize * 18 - 20);
            g2.drawString(text, x, y + 34);

            // SELL AN ITEM
            if(gp.keyHandler.enterPressed) {
                if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon || gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
                    commandNum = 0;
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You cannot sell an equipped item!";
                } else {
                    gp.player.inventory.remove(itemIndex);
                    gp.player.coin += price;
                    gp.keyHandler.enterPressed = false;
                }
            }
        }
    }

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
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
