package main;

import entity.Entity;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    EventRect eventRect[][][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][GamePanel.maxWorldCol][GamePanel.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col < GamePanel.maxWorldCol && row < GamePanel.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col == GamePanel.maxWorldCol) {
                col = 0;
                row++;
                if(row == GamePanel.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {

        // Check if the player charachter is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > GamePanel.tileSize) {
            canTouchEvent = true;
        }


        if(canTouchEvent) {
            if(hit(0, 27, 16, "right")) {
                // event happens
                damagePit(gp.dialogueState);
            }
            else if(hit(0,23, 19, "any")) {
                damagePit( gp.dialogueState);
            }
            /* if(hit(27, 16, "right")) {
                teleport(gp.dialogueState);
            }*/
            else if(hit(0,23, 12, "up")) {
                healingPool(gp.dialogueState);
            }
            else if(hit(0, 13, 39, "any")) {
                teleport(1, 12, 13);
            }
            else if(hit(1, 12, 13, "any")) {
                teleport(0, 13, 39);
            }
            else if(hit(1, 12, 9, "up")) {
                speak(gp.npc[1][0]);
            }
        }

    }

    public boolean hit(int map, int col, int row, String reqDirection) {

        boolean hit = false;

        if(map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * GamePanel.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * GamePanel.tileSize + eventRect[map][col][row].y;
            if(gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone){
                if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;

    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You fall into a pit!";
        gp.player.life -= 1;
        // eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int gameState) {
        if(gp.keyHandler.enterPressed) {
            gp.gameState = gameState;
            gp.player.attackCancel = true;
            gp.ui.currentDialogue = "You drink the water.\nYour life and mana have been recovered.";
            gp.player.mana = gp.player.maxMana;
            gp.player.life = gp.player.maxLife;
            gp.assetSetter.setMonster();
        }
    }

    public void teleport(int map, int col, int row) {

        gp.gameState = gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;


        canTouchEvent = false;
        gp.playSE(13);
    }

    public void speak(Entity entity) {

        if(gp.keyHandler.enterPressed) {
            gp.gameState = gp.dialogueState;
            gp.player.attackCancel = true;
            entity.speak();
        }
    }

}
