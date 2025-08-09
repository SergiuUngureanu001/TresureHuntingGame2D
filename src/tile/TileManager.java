package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[GamePanel.maxScreenCol][GamePanel.maxScreenRow];

        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(1);
        }
    }

    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/maps/map01.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < GamePanel.maxScreenCol && row < GamePanel.maxScreenRow) {
                String line = br.readLine();
                String numbers[] = line.split(" ");
                while(col < GamePanel.maxScreenCol) {


                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == GamePanel.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(1);
        }
    }

    public void draw(Graphics2D g2d) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < GamePanel.maxScreenCol && row < GamePanel.maxScreenRow) {

            int tileNum = mapTileNum[col][row];

            g2d.drawImage(tile[tileNum].image, x, y, GamePanel.tileSize,  GamePanel.tileSize, null);
            col++;
            x +=  GamePanel.tileSize;

            if(col == GamePanel.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y  +=  GamePanel.tileSize;
            }
        }
    }

}
