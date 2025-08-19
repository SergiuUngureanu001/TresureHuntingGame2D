package tile.tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_DryTree extends InteractiveTile {

    public IT_DryTree(GamePanel gp, int  col, int row) {
        super(gp, col, row);

        this.worldX = GamePanel.tileSize * col;
        this.worldY = GamePanel.tileSize * row;

        down1 = setup("/tiles_interactive/drytree", GamePanel.tileSize, GamePanel.tileSize);
        destructible = true;
        life = 3;
    }
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;

        if(entity.currentWeapon.type == type_axe) {
            isCorrectItem = true;
        }

        return isCorrectItem;
    }

    public void playSE() {
        gp.playSE(11);
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new IT_Trunk(gp, worldX / GamePanel.tileSize, worldY / GamePanel.tileSize);
        return tile;
    }

    public Color getParticleColor() {
        Color color = new Color(65,50,30);
        return color;
    }

    public int getParticleSize() {
        int size = 6; // 6 pixels
        return size;
    }

    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife =  20;
        return maxLife;
    }

}
