package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile.tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    public static final int originalTileSize = 16; // 16x16 tile
    public static final int scale = 3;

    public static final int tileSize = originalTileSize * scale; // 48x48 tile
    public static final int maxScreenCol = 20;
    public static final int maxScreenRow = 12;
    public static final int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public static final int screenHeight = tileSize * maxScreenRow;// 576 pixels

    // WORLD SETTINGS
    public static final int maxWorldCol = 50;
    public static final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;
    // FOR FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;


    // FPS
    int FPS = 60;

    ///  SYSTEM
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);

    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    Config config = new Config(this);
    public Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyHandler);
    public Entity obs[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    Comparator<Entity> sortedEntity = new Comparator<Entity>() {
        @Override
        public int compare(Entity a, Entity b) {
            int result = Integer.compare(a.worldY, b.worldY);
            return result;
        }
    };

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;

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
        assetSetter.setNPC();
        assetSetter.setMonster();
        assetSetter.setInteractiveTile();
        playMusic(0);
        stopMusic();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if(fullScreenOn) {
            setFullScreen();
        }
    }

    public void retry() {
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        assetSetter.setNPC();
        assetSetter.setMonster();
    }

    public void restart() {
        player.setDefaultValues();
        player.setDefaultPositions();
        player.setItems();
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        assetSetter.setInteractiveTile();
    }

    public void setFullScreen() {
        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
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
                drawToTempScreen(); // draw everything to the buffered image
                drawToScreen(); // draw the buffered image to the screen
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
        if(gameState == playState) {
            // PLAYER
            player.update();

            // NPC
            for(Entity e : npc[currentMap]) {
                if(e != null) {
                    e.update();
                }
            }

            // MONSTER
            for(int i = 0; i < monster[1].length; i++) {
                if(monster[currentMap][i] != null) {
                    if(monster[currentMap][i].alive && !monster[currentMap][i].dying)  {
                        monster[currentMap][i].update();
                    } else if(!monster[currentMap][i].alive) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }

            for(int i = 0; i < projectileList.size(); i++) {
                if(projectileList.get(i) != null) {
                    if(projectileList.get(i).alive) {
                        projectileList.get(i).update();
                    } else if(!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    }
                }
            }

            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    if(particleList.get(i).alive) {
                        particleList.get(i).update();
                    } else if(!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }

            for(int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }

        } else if(gameState == pauseState) {
            // nothing
        }

    }

    public void drawToTempScreen() {
        g2.setColor(Color.black);
        g2.fillRect(0, 0, screenWidth, screenHeight);


        // DEBUG
        long drawStart = 0;
        if(keyHandler.showDebugText) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if(gameState == titleState) {

            ui.draw(g2);

        } else {

            // TILE
            tileManager.draw(g2);

            // INTERACTIVE TILE
            for(int i = 0; i < iTile[0].length; i++) {
                if(iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }

            // ADD ENTITIES TO THE LIST
            entityList.add(player);

            for(Entity e : npc[currentMap]) {
                if(e != null) {
                    entityList.add(e);
                }
            }

            for(Entity e : obs[currentMap]) {
                if(e != null) {
                    entityList.add(e);
                }
            }

            for(Entity e : monster[currentMap]) {
                if(e != null) {
                    entityList.add(e);
                }
            }

            for(Entity e : projectileList) {
                if(e != null) {
                    entityList.add(e);
                }
            }

            for(Entity e : particleList) {
                if(e != null) {
                    entityList.add(e);
                }
            }

            entityList.sort(sortedEntity);

            for(Entity e : entityList) {
                if(e != null) {
                    e.draw(g2);
                }
            }

            entityList.clear();

            // UI
            ui.draw(g2);

            // DEBUG
            if(keyHandler.showDebugText) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;

                g2.setFont(new Font("Arial", Font.PLAIN, 20));
                int x = 10;
                int y = 400;
                int lineHeight = 20;

                g2.drawString("WorldX" + player.worldX, x, y);
                y += lineHeight;
                g2.drawString("WorldY" + player.worldY, x, y);
                y += lineHeight;
                g2.drawString("Col" + (player.worldX + player.solidArea.x) / GamePanel.tileSize, x, y);
                y += lineHeight;
                g2.drawString("Row" + (player.worldY + player.solidArea.y) / GamePanel.tileSize, x, y);
                y += lineHeight;

                g2.setColor(Color.white);
                g2.drawString("Draw Time: " + passed, x, y);
            }

        }
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
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
