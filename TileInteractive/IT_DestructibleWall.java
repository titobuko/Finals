package TileInteractive;

import Entity.Entity;
import Main.GamePanel;
import Object.OBJCoin_Bronze;
import Object.OBJHeart;
import Object.OBJManaCrystal;

import java.awt.Color;
import java.util.Random;

public class IT_DestructibleWall extends InteractiveTile {
        GamePanel gp;

    public IT_DestructibleWall(GamePanel gp, int col, int row){
        super(gp, col,row );
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setUp("/res/tiles_interactive/destructiblewall", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 3;
        life = maxLife;
    }
        public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem = false;

        if(entity.currentWeapon.type == type_pickaxe){
            isCorrectItem = true;
        }
        return isCorrectItem;
    }
    public void playSE(){
        gp.playSE(20);
    }
    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }
    public Color getParticleColor(){
        Color color = new Color(65, 65, 65);
        return color;
    }
    public int getParticleSize(){
        int size = 6; // 6 pixels
        return size;
    }
    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
    public void checkDrop(){

        int i = new Random().nextInt(100) + 1;
    
      // Create the dropped item based on probability
       if(i < 50) {
        // 50% chance to drop bronze coin
        dropItem(new OBJCoin_Bronze(gp));
       }
       else if(i >= 50 && i < 75) {
        // 25% chance to drop heart
        dropItem(new OBJHeart(gp));
       }
       else if(i >= 75 && i < 100) {
        // 25% chance to drop mana crystal
        dropItem(new OBJManaCrystal(gp));
       }
    }
}
