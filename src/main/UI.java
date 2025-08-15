package main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, purisaB;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0: the first screen, 1: the second screen

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
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
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
        }
        if(gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        if(gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
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
            x += GamePanel.screenWidth / 2 - GamePanel.tileSize * 2;
            y += GamePanel.tileSize * 2;
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

}
