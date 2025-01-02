package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJShield_Wood extends Entity {

    public static final String objName = "Wood Shield";

    public OBJShield_Wood(GamePanel gp){
        super(gp);

        type = type_shield;
        name = objName;
        down1 = setUp("/res/object/shield_wood", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "[" + name  + "]\nMade by wood";
        price = 100;
    }
    
}
