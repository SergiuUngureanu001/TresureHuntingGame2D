package tile.tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InteractiveTile extends Entity {

    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;



        return isCorrectItem;
    }

    public void playSE() {}

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        return tile;
    }

    public void update() {

        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + GamePanel.tileSize > gp.player.worldX - gp.player.screenX && worldX - GamePanel.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + GamePanel.tileSize > gp.player.worldY - gp.player.screenY && worldY - GamePanel.tileSize < gp.player.worldY + gp.player.screenY) {

            g2.drawImage(down1, screenX, screenY,null);

        }
    }
}
