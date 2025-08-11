package main;

import entity.Player;
import object.SuperObject;
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

    // WORLD SETTINGS
    public static final int maxWorldCol = 50;
    public static final int maxWorldRow = 50;
    public static final int worldWidth = tileSize * maxWorldCol;
    public static final int worldHeight = tileSize * maxWorldRow;


    // FPS
    int FPS = 60;

    ///  SYSTEM
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler();

    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyHandler);
    public SuperObject obs[] = new SuperObject[10];

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

    public void setupGame() {
        assetSetter.setObject();

        playMusic(0);
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

        // TILE
        tileManager.draw(g2);

        // OBJECT
        for(SuperObject ob : obs) {
            if(ob != null) {
                ob.draw(g2, this);
            }
        }

        // PLAYER
        player.draw(g2);

        // UI
        ui.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
