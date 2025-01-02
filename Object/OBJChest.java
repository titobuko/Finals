package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJChest extends Entity
{
    GamePanel gp;
    public static final String objName = "Chest";

    public OBJChest(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = objName;
        image = setUp("/res/object/chest", gp.tileSize, gp.tileSize);
        image2 = setUp("/res/object/chest_opened", gp.tileSize, gp.tileSize);
        down1 = image;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void setLoot(Entity loot){
        this.loot = loot;

        setDialogue();
    }
    public void setDialogue(){
        dialogues[0][0] = "You can open and find a " + loot.name + "!\n...But you cannot carry anymore!";
        dialogues[1][0] = "You can open and find a " + loot.name + "!\nYou can obtain the " + loot.name;
        dialogues[2][0] = "It's empty.";
    }
    public void interact(){
        if(opened == false){
            gp.playSE(3);

            if(gp.player.canObtainItem(loot) == false){
                startDialogue(this, 0);
            } 
            else {
               startDialogue(this, 1);
                down1 = image2;
                opened = true;
            }
        }
        else{
            startDialogue(this, 2);
        }
    }
}
