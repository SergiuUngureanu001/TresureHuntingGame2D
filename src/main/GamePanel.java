package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    public static final int originalTileSize = 16; // 16x16 tile
    public static final int scale = 3;

    public static final int tileSize = originalTileSize * scale; // 48x48 tile
    public static final int maxScreenCol = 16;
    public static final int maxScreenRow = 12;
    public static final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public static final int screenHeight = tileSize * maxScreenRow;// 576 pixels

    // FPS
    int FPS = 60;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyHandler);

    // Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInteval = 1000000000 / FPS;
        long lastTime = System.nanoTime();

        double delta = 0;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInteval;
            timer += currentTime -  lastTime;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                timer = 0;
                drawCount = 0;
            }

        }
    }

    public void update() {
        player.update();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        player.draw(g2);

        g2.dispose();
    }
}
