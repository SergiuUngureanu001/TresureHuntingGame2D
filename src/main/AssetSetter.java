package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obs[0] = new OBJ_Key();
        gp.obs[0].worldX = 23 * GamePanel.tileSize;
        gp.obs[0].worldY = 7 * GamePanel.tileSize;

        gp.obs[1] = new OBJ_Key();
        gp.obs[1].worldX = 23 * GamePanel.tileSize;
        gp.obs[1].worldY = 40 * GamePanel.tileSize;

        gp.obs[2] = new OBJ_Key();
        gp.obs[2].worldX = 38 * GamePanel.tileSize;
        gp.obs[2].worldY = 8 * GamePanel.tileSize;

        gp.obs[3] = new OBJ_Door();
        gp.obs[3].worldX = 10 * GamePanel.tileSize;
        gp.obs[3].worldY = 11 * GamePanel.tileSize;

        gp.obs[4] = new OBJ_Door();
        gp.obs[4].worldX = 8 * GamePanel.tileSize;
        gp.obs[4].worldY = 28 * GamePanel.tileSize;

        gp.obs[5] = new OBJ_Door();
        gp.obs[5].worldX = 12 * GamePanel.tileSize;
        gp.obs[5].worldY = 22 * GamePanel.tileSize;

        gp.obs[6] = new OBJ_Chest();
        gp.obs[6].worldX = 10 * GamePanel.tileSize;
        gp.obs[6].worldY = 7 * GamePanel.tileSize;

        gp.obs[7] = new OBJ_Boots();
        gp.obs[7].worldX = 37 * GamePanel.tileSize;
        gp.obs[7].worldY = 42 * GamePanel.tileSize;

    }

}
