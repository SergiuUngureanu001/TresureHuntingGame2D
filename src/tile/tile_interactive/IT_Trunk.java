package tile.tile_interactive;

import main.GamePanel;

public class IT_Trunk extends InteractiveTile {

    public IT_Trunk(GamePanel gp, int col, int row) {
        super(gp, col, row);

        this.worldX = GamePanel.tileSize * col;
        this.worldY = GamePanel.tileSize * row;

        down1 = setup("/tiles_interactive/trunk", GamePanel.tileSize,  GamePanel.tileSize);

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
