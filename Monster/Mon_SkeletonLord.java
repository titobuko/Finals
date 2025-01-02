package Monster;

import java.util.Random;

import Entity.Entity;
import Main.GamePanel;
import Object.OBJCoin_Bronze;
import Object.OBJDoor_Iron;
import Object.OBJHeart;
import Object.OBJManaCrystal;
import data.Progress;

public class Mon_SkeletonLord extends Entity{
    GamePanel gp;
    public static final String monName = "Skeleton Lord"; 
    public Mon_SkeletonLord(GamePanel gp)
    {
        super(gp);
        this.gp = gp;

        type = type_monster;
        boss = true;
        name = monName;
        defaultSpeed = 0;
        speed = defaultSpeed; 
        maxLife = 50;
        life = maxLife;
        attack = 10;
        defense = 2;
        exp = 50;
        knockBackPower = 5;
        sleep = true;

        int size = gp.tileSize*5;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48*2;
        solidArea.height = size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_duration = 25;
        motion2_duration = 50;

        getImage();
        getAttackImage();
        setDialogue();
    }
    public void getImage(){

        int i = 5;

        if(inRage == false){
            up1 = setUp("/res/monster/skeletonlord_up_1",  gp.tileSize*i, gp.tileSize*i);
            up2 = setUp("/res/monster/skeletonlord_up_2", gp.tileSize*i,gp.tileSize*i);
            down1 = setUp("/res/monster/skeletonlord_down_1",  gp.tileSize*i, gp.tileSize*i);
            down2 = setUp("/res/monster/skeletonlord_down_2",  gp.tileSize*i, gp.tileSize*i);
            left1 = setUp("/res/monster/skeletonlord_left_1",  gp.tileSize*i, gp.tileSize*i);
            left2 = setUp("/res/monster/skeletonlord_left_2",  gp.tileSize*i, gp.tileSize*i);
            right1 = setUp("/res/monster/skeletonlord_right_1",  gp.tileSize*i, gp.tileSize*i);
            right2 = setUp("/res/monster/skeletonlord_right_2",  gp.tileSize*i, gp.tileSize*i);
        }
        if(inRage == true){
            up1 = setUp("/res/monster/skeletonlord_phase2_up_1",  gp.tileSize*i, gp.tileSize*i);
            up2 = setUp("/res/monster/skeletonlord_pahase2_up_2", gp.tileSize*i,gp.tileSize*i);
            down1 = setUp("/res/monster/skeletonlord_phase2_down_1",  gp.tileSize*i, gp.tileSize*i);
            down2 = setUp("/res/monster/skeletonlord_phase2_down_2",  gp.tileSize*i, gp.tileSize*i);
            left1 = setUp("/res/monster/skeletonlord_phase2_left_1",  gp.tileSize*i, gp.tileSize*i);
            left2 = setUp("/res/monster/skeletonlord_phase2_left_2",  gp.tileSize*i, gp.tileSize*i);
            right1 = setUp("/res/monster/skeletonlord_phase2_right_1",  gp.tileSize*i, gp.tileSize*i);
            right2 = setUp("/res/monster/skeletonlord_phase2_right_2",  gp.tileSize*i, gp.tileSize*i);
        }
    }
    public void getAttackImage(){

        int i = 5;

        if(inRage == false){
            attackUp1 = setUp("/res/monster/skeletonlord_attack_up_1", gp.tileSize*i, gp.tileSize*i*2);
            attackUp2 = setUp("/res/monster/skeletonlord_attack_up_2", gp.tileSize*i, gp.tileSize*i*2);
            attackDown1 = setUp("/res/monster/skeletonlord_attack_down_1", gp.tileSize*i, gp.tileSize*i*2);
            attackDown2 = setUp("/res/monster/skeletonlord_attack_down_2",gp.tileSize*i, gp.tileSize*i*2);
            attackRight1 = setUp("/res/monster/skeletonlord_attack_right_1", gp.tileSize*i*2, gp.tileSize*i);
            attackRight2 = setUp("/res/monster/skeletonlord_attack_right_2", gp.tileSize*i*2, gp.tileSize*i);
            attackLeft1 = setUp("/res/monster/skeletonlord_attack_left_1", gp.tileSize*i*2, gp.tileSize*i);
            attackLeft2 = setUp("/res/monster/skeletonlord_attack_left_2", gp.tileSize*i*2, gp.tileSize*i);
        }
        if(inRage == true){
            attackUp1 = setUp("/res/monster/skeletonlord_phase2_attack_up_1", gp.tileSize*i, gp.tileSize*i*2);
            attackUp2 = setUp("/res/monster/skeletonlord_phase2_attack_up_2", gp.tileSize*i, gp.tileSize*i*2);
            attackDown1 = setUp("/res/monster/skeletonlord_phase2_attack_down_1", gp.tileSize*i, gp.tileSize*i*2);
            attackDown2 = setUp("/res/monster/skeletonlord_phase2_attack_down_2",gp.tileSize*i, gp.tileSize*i*2);
            attackRight1 = setUp("/res/monster/skeletonlord_phase2_attack_right_1", gp.tileSize*i*2, gp.tileSize*i);
            attackRight2 = setUp("/res/monster/skeletonlord_phase2_attack_right_2", gp.tileSize*i*2, gp.tileSize*i);
            attackLeft1 = setUp("/res/monster/skeletonlord_phase2_attack_left_1", gp.tileSize*i*2, gp.tileSize*i);
            attackLeft2 = setUp("/res/monster/skeletonlord_phase2_attack_left_2", gp.tileSize*i*2, gp.tileSize*i);
        }
    }
    public void setDialogue (){
        dialogues[0][0] = "No one can steal my treasure!";
        dialogues[0][1] = "You wil die here!";
        dialogues[0][2] = "WELCOME TO YOUR DOOM!";
    }
    public void setAction(){

        if(inRage == false && life < maxLife/2){
            inRage = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack *= 2;
        }
        
        if(getTileDistance(gp.player) < 10){
            moveTowardPlayer(60);
        }else{
          //get a random direction
          getRandomDirection(120);
        }
        //check if it attacks
        if(attacking == false){
            checkAttackOrNot(60, gp.tileSize*7, gp.tileSize*5);
        }
    }
    public void onCollision(){
        // Change direction
        changeDirection();
    }
    public void damageReaction(){
        actionLockCounter = 0;
    }
    public void checkDrop(){

        gp.bossBattleOn = false;
        Progress.skeletonLordDefeated = true;

        //restore the previous music
        gp.stopMusic();
        gp.playMusic(19);

        //remove the iron doors
        for(int i = 0; i < gp.obj[1].length; i++){
            if(gp.obj[gp.currentMap][i] != null && gp.obj[gp.currentMap][i].name.equals(OBJDoor_Iron.objName)){
                gp.playSE(21);
                gp.obj[gp.currentMap][i] = null;
            }
        }

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
