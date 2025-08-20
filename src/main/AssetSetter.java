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

        int mapNum = 0;
        int i = 0;
        gp.obs[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obs[mapNum][i].worldX = GamePanel.tileSize * 25;
        gp.obs[mapNum][i].worldY = GamePanel.tileSize * 19;
        i++;
        gp.obs[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obs[mapNum][i].worldX = GamePanel.tileSize * 21;
        gp.obs[mapNum][i].worldY = GamePanel.tileSize * 19;
        i++;
        gp.obs[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obs[mapNum][i].worldX = GamePanel.tileSize * 25;
        gp.obs[mapNum][i].worldY = GamePanel.tileSize * 21;
        i++;
        gp.obs[mapNum][i] = new OBJ_Axe(gp);
        gp.obs[mapNum][i].worldX = GamePanel.tileSize * 33;
        gp.obs[mapNum][i].worldY = GamePanel.tileSize * 21;
        i++;
        gp.obs[mapNum][i] = new OBJ_Shiled_Blue(gp);
        gp.obs[mapNum][i].worldX = GamePanel.tileSize * 35;
        gp.obs[mapNum][i].worldY = GamePanel.tileSize * 21;
        i++;
        gp.obs[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obs[mapNum][i].worldX = GamePanel.tileSize * 22;
        gp.obs[mapNum][i].worldY = GamePanel.tileSize * 27;
        i++;
        gp.obs[mapNum][i] = new OBJ_Heart(gp);
        gp.obs[mapNum][i].worldX = GamePanel.tileSize * 22;
        gp.obs[mapNum][i].worldY = GamePanel.tileSize * 29;
        i++;
        gp.obs[mapNum][i] = new OBJ_ManaCrystal(gp);
        gp.obs[mapNum][i].worldX = GamePanel.tileSize * 22;
        gp.obs[mapNum][i].worldY = GamePanel.tileSize * 31;
        i++;

    }

    public void setNPC() {

        int mapNum = 0;
        int i = 0;
        // MAP 0
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = GamePanel.tileSize * 21;
        gp.npc[mapNum][i].worldY = GamePanel.tileSize * 21;
        i++;
        /*
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = GamePanel.tileSize * 9;
        gp.npc[0].worldY = GamePanel.tileSize * 11;
         */

        // MAP 1
        mapNum = 1;
        i = 0;
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = GamePanel.tileSize * 12;
        gp.npc[mapNum][i].worldY = GamePanel.tileSize * 7;
        i++;



    }

    public void setMonster() {

        int mapNum = 0;
        int i = 0;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = GamePanel.tileSize * 23;
        gp.monster[mapNum][i].worldY = GamePanel.tileSize * 36;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = GamePanel.tileSize * 23;
        gp.monster[mapNum][i].worldY = GamePanel.tileSize * 37;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = GamePanel.tileSize * 24;
        gp.monster[mapNum][i].worldY = GamePanel.tileSize * 37;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = GamePanel.tileSize * 34;
        gp.monster[mapNum][i].worldY = GamePanel.tileSize * 42;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = GamePanel.tileSize * 38;
        gp.monster[mapNum][i].worldY = GamePanel.tileSize * 42;


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
        int mapNum = 0;
        int i = 0;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 27, 12);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 28, 12);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 29, 12);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 30, 12);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 31, 12);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 32, 12);
        i++;

        gp.iTile[mapNum][i] = new IT_DryTree(gp, 17, 40);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 16, 40);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 15, 40);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 14, 40);
        i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 14, 39);
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
