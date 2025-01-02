package Monster;

import java.util.Random;

import Entity.Entity;
import Main.GamePanel;
import Object.OBJCoin_Bronze;
import Object.OBJHeart;
import Object.OBJManaCrystal;

public class Mon_Orc extends Entity{
    GamePanel gp;
    public Mon_Orc(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = "Orc";
        defaultSpeed = 1;
        speed = defaultSpeed; 
        maxLife = 7;
        life = maxLife;
        attack = 8;
        defense = 2;
        exp = 10;
        knockBackPower = 5;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 48;
        attackArea.height = 48;
        motion1_duration = 40;
        motion2_duration = 85;

        getImage();
        getAttackImage();
    }
    public void getImage(){

        up1 = setUp("/res/monster/orc_up_1",  gp.tileSize, gp.tileSize);
        up2 = setUp("/res/monster/orc_up_2", gp.tileSize, gp.tileSize);
        down1 = setUp("/res/monster/orc_down_1",  gp.tileSize, gp.tileSize);
        down2 = setUp("/res/monster/orc_down_2",  gp.tileSize, gp.tileSize);
        left1 = setUp("/res/monster/orc_left_1",  gp.tileSize, gp.tileSize);
        left2 = setUp("/res/monster/orc_left_2",  gp.tileSize, gp.tileSize);
        right1 = setUp("/res/monster/orc_right_1",  gp.tileSize, gp.tileSize);
        right2 = setUp("/res/monster/orc_right_2",  gp.tileSize, gp.tileSize);

    }
    public void getAttackImage(){
        attackUp1 = setUp("/res/monster/orc_attack_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2 = setUp("/res/monster/orc_attack_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1 = setUp("/res/monster/orc_attack_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2 = setUp("/res/monster/orc_attack_down_2",gp.tileSize, gp.tileSize*2);
        attackRight1 = setUp("/res/monster/orc_attack_right_1", gp.tileSize*2, gp.tileSize);
        attackRight2 = setUp("/res/monster/orc_attack_right_2", gp.tileSize*2, gp.tileSize);
        attackLeft1 = setUp("/res/monster/orc_attack_left_1", gp.tileSize*2, gp.tileSize);
        attackLeft2 = setUp("/res/monster/orc_attack_left_2", gp.tileSize*2, gp.tileSize);

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
        //check if it attacks
        if(attacking == false){
            checkAttackOrNot(30, gp.tileSize*4, gp.tileSize);
        }
    }
    public void onCollision(){
        // Change direction
        changeDirection();
    }
    public void damageReaction(){
        actionLockCounter = 0;
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
