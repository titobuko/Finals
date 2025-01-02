package Monster;

import java.util.Random;

import Entity.Entity;
import Main.GamePanel;
import Object.OBJCoin_Bronze;
import Object.OBJHeart;
import Object.OBJManaCrystal;
import Object.OBJRock;

public class Mon_RedSlime extends Entity{
    GamePanel gp;
    public Mon_RedSlime(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = "Red Slime";
        defaultSpeed = 1;
        speed = defaultSpeed; 
        maxLife = 4;
        life = maxLife;
        attack = 1;
        defense = 0;
        exp = 2;
        projectile = new OBJRock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    public void getImage()
    {
        up1 = setUp("/res/monster/redslime_down_1",  gp.tileSize, gp.tileSize);
        up2 = setUp("/res/monster/redslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setUp("/res/monster/redslime_down_1",  gp.tileSize, gp.tileSize);
        down2 = setUp("/res/monster/redslime_down_2",  gp.tileSize, gp.tileSize);
        left1 = setUp("/res/monster/redslime_down_1",  gp.tileSize, gp.tileSize);
        left2 = setUp("/res/monster/redslime_down_2",  gp.tileSize, gp.tileSize);
        right1 = setUp("/res/monster/redslime_down_1",  gp.tileSize, gp.tileSize);
        right2 = setUp("/res/monster/redslime_down_2",  gp.tileSize, gp.tileSize);
    }
    public void setAction(){
        
        if(onPath == true){
           //check if it stops chasing
           checkStopChasingOrNot(gp.player, 15, 100); 
            
            //search distance to  go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
            

        }else{
            //check if it start chasing 
            checkStartChasingOrNot(gp.player, 5, 100);

          //get a random direction
          getRandomDirection(120);
        }
    }
    public void onCollision(){
        changeDirection();
    }
    public void damageReaction(){
        actionLockCounter = 0;
        //direction = gp.player.direction;
        onPath = true;
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
