package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJKey extends Entity{

    GamePanel  gp;
    public static final String objName = "Key";
    public OBJKey(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = objName;
        down1 = setUp("/res/object/key", gp.tileSize, gp.tileSize);
        description = "[" + name  + "]\nWill open the door";
        price = 100;
        stackable = true;
    }
    public void setDialogue(){
        dialogues[0][0] = "You use the " + name + " and open the door";

        dialogues[1][0] = "What are you diong?";
    }
    public boolean use(Entity entity){

        int objIndex = getDetected(entity, gp.obj, "Door");

        if(objIndex != 999){
            startDialogue(this, 0);
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else{
            startDialogue(this, 1);
            return false;
        }
    }
}
