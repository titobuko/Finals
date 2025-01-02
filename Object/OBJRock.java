package Object;

import java.awt.Color;

import Entity.Entity;
import Entity.Projectile;
import Main.GamePanel;

public class OBJRock extends Projectile {

    GamePanel gp;
    public static final String objName = "Rock";

    public OBJRock(GamePanel gp){
        super(gp);

        this.gp = gp;
        
        name = objName;
        speed = 8;
        maxLife = 0;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }
    public void getImage(){
        up1 = setUp("/res/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/res/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        down1 = setUp("/res/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("/res/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        left1 = setUp("/res/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        left2 = setUp("/res/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        right1 = setUp("/res/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        right2= setUp("/res/projectile/rock_down_1", gp.tileSize, gp.tileSize);
    }
    public boolean haveResource(Entity user){
        boolean haveResource = false;
        if(user.ammo >= useCost){
            haveResource = true;
        }
        return haveResource;
    }
    public void substractResource(Entity user){
        
    }
    public Color getParticleColor(){
        Color color = new Color(20, 50, 0);
        return color;
    }
    public int getParticleSize(){
        int size = 10; // 6 pixels
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
    public void update() {
        // Move projectile
        switch(direction) {
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }
        
        life--; // Decrease projectile lifetime
        if(life <= 0) {
            alive = false;
        }
        
        // Animation
        spriteCounter++;
        if(spriteCounter > 12) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        
        // Check collision with player
        if(user != gp.player) {
            boolean hit = false;
            
            // Get collision areas
            int projectileLeftX = worldX + solidArea.x;
            int projectileRightX = worldX + solidArea.x + solidArea.width;
            int projectileTopY = worldY + solidArea.y;
            int projectileBottomY = worldY + solidArea.y + solidArea.height;
            
            int playerLeftX = gp.player.worldX + gp.player.solidArea.x;
            int playerRightX = gp.player.worldX + gp.player.solidArea.x + gp.player.solidArea.width;
            int playerTopY = gp.player.worldY + gp.player.solidArea.y;
            int playerBottomY = gp.player.worldY + gp.player.solidArea.y + gp.player.solidArea.height;
            
            if(!gp.player.invisible && 
               projectileRightX > playerLeftX &&
               projectileLeftX < playerRightX &&
               projectileBottomY > playerTopY &&
               projectileTopY < playerBottomY) {
                
                hit = true;
            }
            
            if(hit) {
                int damage = attack;
                if(user != null) {
                    damage += user.attack;
                }
                damage -= gp.player.defense;
                if(damage < 0) damage = 0;
                
                gp.player.life -= damage;
                gp.player.invisible = true;
                gp.ui.addMessage(damage + " damage from " + name + "!");
                gp.playSE(6);
                alive = false;
            }
        }
    }
}
