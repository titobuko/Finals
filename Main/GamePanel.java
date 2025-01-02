package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;

import AI.PathFinder;
import Entity.Entity;
import Entity.Player;
import Environment.EnvironmentManager;
import Tile.Map;
import Tile.TileManager;
import TileInteractive.InteractiveTile;
import data.SaveLoad;

public class GamePanel extends JPanel implements Runnable
{
    //screen settings
    final int originalTileSize = 16;// tile size
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; //48 x 48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;// 960 pixel
    public final int screenHeight = tileSize * maxScreenRow;//576 pixel

    //world settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    public final int maxMap = 10;
    public int currentMap = 0;
    
    //Full screen
    int screenWidth2  = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    int FPS = 60;   

    //System
    public TileManager tileM = new TileManager(this);
    public keyHandler keyH = new keyHandler(this);
    Sound music =  new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    Map map = new Map(this);
    SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);
    public CutsceneManager csManager = new CutsceneManager(this);
    Thread gameThread;

    //Entity and object
    public Player player = new Player(this,keyH);
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity NPC[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new  InteractiveTile[maxMap][50];
    public Entity projectile[][] = new Entity[maxMap][20];
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    //Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;
    public final int cutsceneState = 11;

    //others
    public boolean bossBattleOn = false;


    //Area
    public int currentArea;
    public int nextArea;
    public final int outside = 50;
    public final int indoor = 51;
    public final int dungeon = 52;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);   
        this.addKeyListener(keyH);
        this.setFocusable(true);                                                  
    }
    public void setFullScreen(){
        //Get local screen device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //get full screen
        screenWidth2 =  Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }
     public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setUpGame(){
        currentMap = 0;
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        eManager.setUp();

        gameState = titleState;
        currentArea = outside;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        if(fullScreenOn == true){
            setFullScreen();
        }
        
    }
    public void resetGame(boolean restart){

        stopMusic();
        currentArea = outside;
        removeTempEntity();
        bossBattleOn = false;
        player.setDefaultPosition();
        player.restoreStatus();
        player.resetCounter();
        aSetter.setNPC();
        aSetter.setMonster();

        if(restart == true){
            player.setDefaultValues();
            aSetter.setObject();
            aSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }
    }
    public void run() 
    {
        double drawInterval = 1000000000/FPS;
        double delta  = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime -lastTime) / drawInterval;
            timer += (currentTime -lastTime) / drawInterval;
            lastTime = currentTime;
            
            if(delta >= 1){
                update();
                drawToTempScreen(); //draw everything to the buffered image
                drawToScreen();//draw the buffered image to the screen
                delta --;
                drawCount ++;
            }
            if (timer >= 1000000000){
                System.out.println("FPS" + drawCount);
                drawCount =0;
                timer = 0;
            }
        }
    }
    public void update()
    {
        if(gameState == playState){
            //player
            player.update();   
                    
            //npc
            for(int i = 0; i < NPC[currentMap].length; i++) {
                if(NPC[currentMap][i] != null) {
                    NPC[currentMap][i].update();
                }
            }
            //monster
            for(int i = 0; i < monster[currentMap].length; i++){
                if(monster[currentMap][i] != null){
                    if(monster[currentMap][i].alive == true && monster[currentMap][i].died == false){
                        monster[currentMap][i].update();
                    }
                    if(!monster[currentMap][i].alive){
                        monster[currentMap][i].checkDrop();
                        monster[i] = null;
                    }
                    if(monster[currentMap][i].shouldRemove) {
                        monster[currentMap][i] = null;  // Remove the monster
                    }
                }
            }
            for(int i = 0; i < projectile[1].length; i++) {
                if(projectile[currentMap][1] != null) {
                    if(projectile[currentMap][1].alive) {
                        projectile[currentMap][1].update();
                    }
                    if(projectile[currentMap][1].alive == false) {
                        projectile[currentMap][1] = null;
                    }
                }
            
            }
            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    if(particleList.get(i).alive) {
                        particleList.get(i).update();
                    }
                    if(!particleList.get(i).alive) {
                        particleList.remove(i);
                        i--;
                    }
                }
            
            }
            for(int i = 0; i < iTile[currentMap].length; i++){
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].update();
                }
            }
            eManager.update();
        
        }
        if(gameState == pauseState){
            //wala
        }
    }
    public void drawToTempScreen(){
    //debug
        long drawStart = 0;
            if(keyH.showDebugText == true){
                drawStart = System.nanoTime();
            }
            drawStart = System.nanoTime();
      
        //Title screen
            if(gameState == titleState)
            {
                ui.draw(g2);
            }
            else if(gameState == mapState){
                map.drawFullMapScreen(g2);
            }
            //lebg
            else
              {
                //Tile
                  tileM.draw(g2);
      
                //Interactive tile
                for(int i = 0; i < iTile[currentMap].length; i++){
                    if(iTile[currentMap][i] != null){
                        iTile[currentMap][i].draw(g2);
                    }
                }
      
                //Add entity to list
                entityList.add(player);
                for(int i = 0; i < NPC[currentMap].length; i++){
                    if(NPC[currentMap][i] != null){
                        entityList.add(NPC[currentMap][i]);
                    }
                }
                for(int i = 0; i < obj[currentMap].length; i++){
                    if(obj[currentMap][i] != null){
                        entityList.add(obj[currentMap][i]);
                    }
                }
                for(int i = 0; i < monster[currentMap].length; i++){
                    if(monster[currentMap][i] != null){
                        entityList.add(monster[currentMap][i]);
                    }
                }
                  for(int i = 0; i < projectile[1].length; i++){
                    if(projectile[currentMap][i] != null){
                        entityList.add(projectile[currentMap][i]);
                    }
                }
                for(int i = 0; i < particleList.size(); i++){
                    if(particleList.get(i) != null){
                        entityList.add(particleList.get(i));
                    }
                }
      
                //sort
                  Collections.sort(entityList, new Comparator<Entity>()
                {
      
                    @Override
                    public int compare(Entity e1, Entity e2) 
                    {
                        int result = Integer.compare(e1.worldY, e2.worldX);
                        return result;
                         
                    } 
                });
                //draw entity
                for(int i = 0; i < entityList.size(); i++)
                {
                    entityList.get(i).draw(g2);
                }
                //empty entity list 
                entityList.clear();

                //Environment
                eManager.draw(g2);

                //mini map
                map.drawMiniMap(g2);
                
                //cutscene
                csManager.draw(g2);
      
                //UI
                ui.draw(g2);
      
            }
      
            //debug
            if(keyH.showDebugText == true){
                long drawEnd  = System.nanoTime();
                long passed = drawEnd - drawStart;
      
                g2.setFont(new Font("Arial", Font.PLAIN, 20));
                g2.setColor(Color.WHITE);
                int x = 10;
                int y = 400;
                int lineHeight = 20;
      
                g2.drawString("WorldX" + player.worldX, x, y); y += lineHeight;
                g2.drawString("WorldY" + player.worldY, x, y); y += lineHeight;
                g2.drawString("Col" + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
                g2.drawString("Row" + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;
                g2.drawString("Draw Time: " + passed, x, y); y += lineHeight;
                g2.drawString("God Mode:" + keyH.godModeOn, x, y);
            }
    
    }
    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
    public void playMusic(int i)
    {
        music.setFile(i);
        //music.play();
        music.loop();
    }
    public void stopMusic()
    {
        music.stop();
    }
    public void playSE(int i)
    {
        se.setFile(i);
        se.play();
    }
    public void drawImage(BufferedImage image, int x, int y, Graphics2D g2) {
        if (g2 != null && image != null) {
            g2.drawImage(image, x, y, null);
        }
    }
    public void changeArea(){
        if(nextArea != currentArea){
            stopMusic();
            if(nextArea == outside){
                playMusic(0);
            }
            if(nextArea == indoor){
                playMusic(18);
            }
            if(nextArea == outside){
                playMusic(19);
            }
            aSetter.setNPC();
        }
        aSetter.setMonster();
        currentArea = nextArea;
    }
    public void removeTempEntity(){
        for(int mapNum = 0; mapNum < maxMap; mapNum++){
            for(int i = 0; i < obj[1].length; i++){
                if(obj[mapNum][i] != null && obj[mapNum][i].temp == true){
                    obj[mapNum][i] = null;
                }
            }
        }
    }
}
