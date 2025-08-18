package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.*;
import tile.tile_interactive.IT_DryTree;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        int i = 0;
        gp.obs[i] = new OBJ_Coin_Bronze(gp);
        gp.obs[i].worldX = GamePanel.tileSize * 25;
        gp.obs[i].worldY = GamePanel.tileSize * 19;
        i++;
        gp.obs[i] = new OBJ_Coin_Bronze(gp);
        gp.obs[i].worldX = GamePanel.tileSize * 21;
        gp.obs[i].worldY = GamePanel.tileSize * 19;
        i++;
        gp.obs[i] = new OBJ_Coin_Bronze(gp);
        gp.obs[i].worldX = GamePanel.tileSize * 25;
        gp.obs[i].worldY = GamePanel.tileSize * 21;
        i++;
        gp.obs[i] = new OBJ_Axe(gp);
        gp.obs[i].worldX = GamePanel.tileSize * 33;
        gp.obs[i].worldY = GamePanel.tileSize * 21;
        i++;
        gp.obs[i] = new OBJ_Shiled_Blue(gp);
        gp.obs[i].worldX = GamePanel.tileSize * 35;
        gp.obs[i].worldY = GamePanel.tileSize * 21;
        i++;
        gp.obs[i] = new OBJ_Potion_Red(gp);
        gp.obs[i].worldX = GamePanel.tileSize * 22;
        gp.obs[i].worldY = GamePanel.tileSize * 27;
        i++;
        gp.obs[i] = new OBJ_Heart(gp);
        gp.obs[i].worldX = GamePanel.tileSize * 22;
        gp.obs[i].worldY = GamePanel.tileSize * 29;
        i++;
        gp.obs[i] = new OBJ_ManaCrystal(gp);
        gp.obs[i].worldX = GamePanel.tileSize * 22;
        gp.obs[i].worldY = GamePanel.tileSize * 31;
        i++;

    }

    public void setNPC() {

        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = GamePanel.tileSize * 21;
        gp.npc[0].worldY = GamePanel.tileSize * 21;
        /*
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = GamePanel.tileSize * 9;
        gp.npc[0].worldY = GamePanel.tileSize * 11;
         */

    }

    public void setMonster() {

        int i = 0;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = GamePanel.tileSize * 23;
        gp.monster[i].worldY = GamePanel.tileSize * 36;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = GamePanel.tileSize * 23;
        gp.monster[i].worldY = GamePanel.tileSize * 37;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = GamePanel.tileSize * 24;
        gp.monster[i].worldY = GamePanel.tileSize * 37;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = GamePanel.tileSize * 34;
        gp.monster[i].worldY = GamePanel.tileSize * 42;
        i++;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = GamePanel.tileSize * 38;
        gp.monster[i].worldY = GamePanel.tileSize * 42;


        /*
        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = GamePanel.tileSize * 11;
        gp.monster[0].worldY = GamePanel.tileSize * 10;

        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldX = GamePanel.tileSize * 11;
        gp.monster[1].worldY = GamePanel.tileSize * 11;
         */
    }

    public void setInteractiveTile() {
        int i = 0;
        gp.iTile[i] = new IT_DryTree(gp, 27, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 28, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 29, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 30, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 31, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 32, 12);
        i++;

        /*
        gp.iTile[i] = new IT_DryTree(gp, 30, 20);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 30, 21);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 30, 22);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 30, 23);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 30, 24);
        i++;
         */
    }

}
