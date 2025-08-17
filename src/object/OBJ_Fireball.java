package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile {



    public OBJ_Fireball(GamePanel gp) {
        super(gp);

        name = "Fireball";
        speed = 10;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();

    }

    public void getImage() {
        up1 = setup("/projectile/fireball_up_1", GamePanel.tileSize, GamePanel.tileSize);
        up2 = setup("/projectile/fireball_up_2", GamePanel.tileSize, GamePanel.tileSize);
        down1 = setup("/projectile/fireball_down_1",  GamePanel.tileSize, GamePanel.tileSize);
        down2 = setup("/projectile/fireball_down_2", GamePanel.tileSize, GamePanel.tileSize);
        left1 = setup("/projectile/fireball_left_1", GamePanel.tileSize, GamePanel.tileSize);
        left2 = setup("/projectile/fireball_left_2",  GamePanel.tileSize, GamePanel.tileSize);
        right1 = setup("/projectile/fireball_right_1",  GamePanel.tileSize, GamePanel.tileSize);
        right2 = setup("/projectile/fireball_right_2", GamePanel.tileSize, GamePanel.tileSize);
    }

}
