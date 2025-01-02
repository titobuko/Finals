package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJDoor extends Entity{
    GamePanel gp;
    public static final String objName = "Door";

      public OBJDoor(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = objName;
        down1 = setUp("/res/object/door", gp.tileSize, gp.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void setDialogue(){
        dialogues[0][0] = "You need a key to open this.";
    }
    public void interact(){
        startDialogue(this, 0);
    }
}
