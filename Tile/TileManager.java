package Tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics2D;
import Main.GamePanel;
import Main.UtilityTool;

public class TileManager
{
    GamePanel gp;
    public Tile[]  tile;
    public int mapTileNum[][][];
    boolean drawPath = true;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[50];

        mapTileNum = new int [gp.maxMap] [gp.maxWorldCol] [gp.maxWorldRow];

        getTileImage();
        loadMap("/res/maps/worldV3.txt",0);
        loadMap("/res/maps/interior01.txt", 1);
        loadMap("/res/maps/dungeon01.txt", 2);
        loadMap("/res/maps/dungeon02.txt", 3);
    }
    public void getTileImage(){

        //PlaceHolders
        setUp(0, "grass00", false);
        setUp(1, "grass00", false);
        setUp(2, "grass00", false);
        setUp(3, "grass00", false);
        setUp(4, "grass00", false);
        setUp(5, "grass00", false);
        setUp(6, "grass01", false);
        setUp(7, "grass00", false);
        setUp(8, "grass00", false);
        setUp(9, "grass00", false);
        setUp(10, "grass00", false);
        setUp(11, "grass01", false);
        setUp(12, "water00", true);
        setUp(13, "water01", true);
        setUp(14, "water02", true);
        setUp(15, "water03", true);
        setUp(16, "water04", true);
        setUp(17, "water05", true);
        setUp(18, "water06", true);
        setUp(19, "water07", true); 
        setUp(20, "water08", true);
        setUp(21, "water09", true);
        setUp(22, "water10", true); 
        setUp(23, "water11", true);
        setUp(24, "water12",true);
        setUp(25, "water13", true);
        setUp(26, "road00", false);
        setUp(27, "road01", false);
        setUp(28, "road02", false);
        setUp(29, "road03", false);
        setUp(30, "road04", false);
        setUp(31, "road05", false);
        setUp(32, "road06", false);
        setUp(33, "road07", false);
        setUp(34, "road08", false);
        setUp(35, "road09", false);
        setUp(36, "road10", false);
        setUp(37, "road11", false);
        setUp(38, "road12", false);
        setUp(39, "earth", false);
        setUp(40, "wall", true);
        setUp(41, "tree", true);
        setUp(42, "hut", false);
        setUp(43, "floor1", false);
        setUp(44, "table1", true);
        setUp(45, "036", false);
        setUp(46, "037", false);
    }
    public void setUp(int index, String imageName, boolean collision){
        UtilityTool uTool = new UtilityTool();
        try{
            tile[index] = new Tile();
            String path = "/res/tiles/" + imageName + ".png";
            InputStream is = getClass().getResourceAsStream(path);
            if (is == null) {
                return;
            }
            tile[index].image = ImageIO.read(is);
            tile[index].image = uTool.scaledImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
    
        }catch(IOException e)   
        {
            System.err.println("Error loading image: " + imageName);
            e.printStackTrace();
        }
    
    }

    public void  loadMap(String filePath, int map){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                String numbers [] = line.split(" ");

                int num = Integer.parseInt(numbers [col]);

                mapTileNum [map] [col] [row] = num;
                col++;
            }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
           br.close();

        }catch(Exception e){
           
        }
    }

    public void draw(Graphics2D g2){
      int worldCol = 0;
      int worldRow = 0;

      while (worldCol <  gp.maxWorldCol && worldRow < gp.maxWorldRow){
        
        int tileNum = mapTileNum[gp.currentMap][worldCol] [worldRow];

        int worldX = worldCol * gp.tileSize;
        int worldY = worldRow * gp.tileSize;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
           worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
           worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
           worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
            
            g2.drawImage(tile[tileNum].image, screenX, screenY,  null);
        }
        worldCol++;
        
        if(worldCol == gp.maxWorldCol){
            
            worldCol = 0;
            worldRow++;
        }
      }
      if(drawPath == true){
        g2.setColor(new Color(255, 0, 0, 70));

        for(int i = 0; i < gp.pFinder.pathList.size(); i++){

            int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
            int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }
      }
    }
}
