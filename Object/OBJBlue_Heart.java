package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJBlue_Heart extends Entity {
    
    GamePanel gp;
    public static final String objName = "Blue Heart";

    public OBJBlue_Heart(GamePanel gp){
        super(gp);

        this.gp = gp;
        type =  type_pickupOnly;
        name = objName;
        down1 = setUp("/res/object/blueheart", gp.tileSize, gp.tileSize);

        setDialogue();
    }
    public void setDialogue(){
        dialogues[0][0] = "You pick up a beaiutiful blue gem.";
        dialogues[0][1] = "You find the Blue Heart, the Legendary treasure!";
    }
    public boolean use(Entity entity){

        gp.gameState = gp.cutsceneState;
        gp.csManager.sceneNum = gp.csManager.ending;  
        return true;
    }
}
