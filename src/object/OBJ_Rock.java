package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile {

    public OBJ_Rock(GamePanel gp) {
        super(gp);

        name = "Rock";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();

    }

    public void getImage() {
        up1 = setup("/projectile/rock_down_1", GamePanel.tileSize, GamePanel.tileSize);
        up2 = setup("/projectile/rock_down_1", GamePanel.tileSize, GamePanel.tileSize);
        down1 = setup("/projectile/rock_down_1",  GamePanel.tileSize, GamePanel.tileSize);
        down2 = setup("/projectile/rock_down_1", GamePanel.tileSize, GamePanel.tileSize);
        left1 = setup("/projectile/rock_down_1", GamePanel.tileSize, GamePanel.tileSize);
        left2 = setup("/projectile/rock_down_1",  GamePanel.tileSize, GamePanel.tileSize);
        right1 = setup("/projectile/rock_down_1",  GamePanel.tileSize, GamePanel.tileSize);
        right2 = setup("/projectile/rock_down_1", GamePanel.tileSize, GamePanel.tileSize);
    }

    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if(user.ammo >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }

    public void substracrResource(Entity user) {
        user.ammo -= useCost;
    }

}
