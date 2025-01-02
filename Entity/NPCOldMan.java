package Entity;

import java.awt.Rectangle;
import java.util.Random;
import Main.GamePanel;  

public class NPCOldMan extends Entity{

    public NPCOldMan(GamePanel gp){

        super(gp);
        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        dialogueSet = -1;
        getNPCImage();
        setDialogue();
    }
     public void getNPCImage(){

        up1 = setUp("/res/NPC/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/res/NPC/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setUp("/res/NPC/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("/res/NPC/oldman_down_2",  gp.tileSize, gp.tileSize);
        left1 = setUp("/res/NPC/oldman_left_1",  gp.tileSize, gp.tileSize);
        left2 = setUp("/res/NPC/oldman_left_2",  gp.tileSize, gp.tileSize);
        right1 = setUp("/res/NPC/oldman_right_1",  gp.tileSize, gp.tileSize);
        right2 = setUp("/res/NPC/oldman_right_2", gp.tileSize, gp.tileSize);
    }
    public void setDialogue(){

        dialogues[0][0] = "Hello";
        dialogues[0][1] = "So you came here to find the treasure?";
        dialogues[0][2] = "I used to be a great wizzard but now ... \n I'm a bit too old for taking an adventure. ";
        dialogues[0][3] = "Well, goodluck on your adventure.";

        dialogues[1][0] = "If you became tired, rest at the water.";
        dialogues[1][1] = "However, the monster reappeard if youn rest. \nI dont know why but thats how it works.";
        dialogues[1][2] = "Well goood luck on you.";

        dialogues[2][0] = "I wonder how to open the door...";

    }
    public void setAction(){

        if(onPath == true){

            int goalCol = 12;
            int goalRow = 9;
            // int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            // int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

            searchPath(goalCol, goalRow);
        }
        else{
            actionLockCounter++;
            if(actionLockCounter == 120){

            Random random = new Random();
            int i = random.nextInt(100) + 1; //pick number 1 to 100
            
            if( i <= 25){
                direction = "up";
            }
            if(i > 25 && i <= 50){
                direction = "down";
            }
            if(i > 50 && i <= 75){
                direction = "left";
            }
            if(i > 75 && i <= 100){
                direction = "right";
            }
            actionLockCounter = 0;
           }
        }
    }
    public void speak(){

        // do this character specific stuff
        facePlayer();
        startDialogue(this, dialogueSet);

        dialogueSet++;
        if(dialogues[dialogueSet][0] == null){
            dialogueSet--;
        }
    
       //onPath = true;
    }
    public void onCollision(){
        
        changeDirection();
    }
}
