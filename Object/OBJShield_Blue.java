package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJShield_Blue extends Entity {
    
    public static final String objName = "Blue Shield";

    public OBJShield_Blue(GamePanel gp){
        super(gp);

        type = type_shield;
        name = objName;
        down1 = setUp("/res/object/shield_blue", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "[" + name  + "]\nshiny blue shield";
        price = 250;
    }
}
