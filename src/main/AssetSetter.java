package main;

import entity.NPC_OldMan;
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
        gp.obs[0] = new OBJ_Door(gp);
        gp.obs[0].worldX = GamePanel.tileSize * 21;
        gp.obs[0].worldY = GamePanel.tileSize * 22;

        gp.obs[1] = new OBJ_Door(gp);
        gp.obs[1].worldX = GamePanel.tileSize * 23;
        gp.obs[1].worldY = GamePanel.tileSize * 25;

    }

    public void setNPC() {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = GamePanel.tileSize * 21;
        gp.npc[0].worldY = GamePanel.tileSize * 21;

        gp.npc[1] = new NPC_OldMan(gp);
        gp.npc[1].worldX = GamePanel.tileSize * 11;
        gp.npc[1].worldY = GamePanel.tileSize * 21;

        gp.npc[2] = new NPC_OldMan(gp);
        gp.npc[2].worldX = GamePanel.tileSize * 31;
        gp.npc[2].worldY = GamePanel.tileSize * 21;

        gp.npc[3] = new NPC_OldMan(gp);
        gp.npc[3].worldX = GamePanel.tileSize * 21;
        gp.npc[3].worldY = GamePanel.tileSize * 11;

        gp.npc[4] = new NPC_OldMan(gp);
        gp.npc[4].worldX = GamePanel.tileSize * 21;
        gp.npc[4].worldY = GamePanel.tileSize * 31;
    }
}
