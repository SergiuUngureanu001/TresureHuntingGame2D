package main;

import entity.Entity;
import entity.NPC_OldMan;

public class CollisionChecker {

    private GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;

        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / GamePanel.tileSize;
        int entityRightCol = entityRightWorldX / GamePanel.tileSize;
        int entityTopRow = entityTopWorldY / GamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / GamePanel.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction) {
            case "up": {
                entityTopRow = (entityTopWorldY - entity.speed) /  GamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityRightCol][entityTopRow];
                if(gamePanel.tileManager.tile[tileNum1].collision ||  gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            }

            case "down": {
                entityBottomRow = (entityBottomWorldY + entity.speed) /  GamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityRightCol][entityBottomRow];
                if(gamePanel.tileManager.tile[tileNum1].collision ||  gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            }

            case "left": {
                entityLeftCol = (entityLeftWorldX - entity.speed) /  GamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityLeftCol][entityBottomRow];
                if(gamePanel.tileManager.tile[tileNum1].collision ||  gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            }

            case "right": {
                entityRightCol = (entityRightWorldX + entity.speed) /  GamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[gamePanel.currentMap][entityRightCol][entityBottomRow];
                if(gamePanel.tileManager.tile[tileNum1].collision ||  gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            }
        }

    }

    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for(int i = 0; i < gamePanel.obs[gamePanel.currentMap].length; i++) {
            if(gamePanel.obs[gamePanel.currentMap][i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                gamePanel.obs[gamePanel.currentMap][i].solidArea.x = gamePanel.obs[gamePanel.currentMap][i].worldX + gamePanel.obs[gamePanel.currentMap][i].solidArea.x;
                gamePanel.obs[gamePanel.currentMap][i].solidArea.y = gamePanel.obs[gamePanel.currentMap][i].worldY + gamePanel.obs[gamePanel.currentMap][i].solidArea.y;

                switch(entity.direction) {
                    case "up": {
                        entity.solidArea.y -= entity.speed;
                        break;
                    }

                    case "down": {
                        entity.solidArea.y += entity.speed;
                        break;
                    }

                    case "left": {
                        entity.solidArea.x -= entity.speed;
                        break;

                    }

                    case "right": {
                        entity.solidArea.x += entity.speed;
                        break;

                    }
                }
                if(entity.solidArea.intersects(gamePanel.obs[gamePanel.currentMap][i].solidArea)) {
                    if(gamePanel.obs[gamePanel.currentMap][i].collision == true) {
                        entity.collisionOn = true;
                    }
                    if(player) {
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.obs[gamePanel.currentMap][i].solidArea.x = gamePanel.obs[gamePanel.currentMap][i].solidAreaDefaultX;
                gamePanel.obs[gamePanel.currentMap][i].solidArea.y = gamePanel.obs[gamePanel.currentMap][i].solidAreaDefaultY;
            }
        }

        return index;

    }

    // NPC OR MONSTER
    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999;

        for(int i = 0; i < target[gamePanel.currentMap].length; i++) {
            if(target[gamePanel.currentMap][i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                target[gamePanel.currentMap][i].solidArea.x = target[gamePanel.currentMap][i].worldX + target[gamePanel.currentMap][i].solidArea.x;
                target[gamePanel.currentMap][i].solidArea.y = target[gamePanel.currentMap][i].worldY + target[gamePanel.currentMap][i].solidArea.y;

                switch(entity.direction) {
                    case "up": {
                        entity.solidArea.y -= entity.speed;
                        break;
                    }

                    case "down": {
                        entity.solidArea.y += entity.speed;
                        break;
                    }

                    case "left": {
                        entity.solidArea.x -= entity.speed;
                        break;
                    }

                    case "right": {
                        entity.solidArea.x += entity.speed;
                        break;

                    }
                }
                if(entity.solidArea.intersects(target[gamePanel.currentMap][i].solidArea) && target[gamePanel.currentMap][i] != entity) {
                    entity.collisionOn = true;
                    index = i;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gamePanel.currentMap][i].solidArea.x = target[gamePanel.currentMap][i].solidAreaDefaultX;
                target[gamePanel.currentMap][i].solidArea.y = target[gamePanel.currentMap][i].solidAreaDefaultY;
            }
        }

        return index;
    }

    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;

        // Get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // Get the object's solid area position
        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

        switch(entity.direction) {
            case "up": {
                entity.solidArea.y -= entity.speed;
                break;
            }

            case "down": {
                entity.solidArea.y += entity.speed;
                break;
            }

            case "left": {
                entity.solidArea.x -= entity.speed;
                break;

            }

            case "right": {
                entity.solidArea.x += entity.speed;
                break;

            }
        }
        if(entity.solidArea.intersects(gamePanel.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;

        return contactPlayer;
    }

}
