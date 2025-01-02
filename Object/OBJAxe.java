package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJAxe extends Entity {

    public static final String objName = "Fighter's Axe";

    public OBJAxe(GamePanel gp){
        super(gp);

        type = type_axe;
        name = objName;
        down1 = setUp("/res/object/axe", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[Wood Cutter Axe]\n Bit rusty but still sharp";
        price = 75;
        knockBackPower = 20;
        motion1_duration = 20;
        motion2_duration = 40;
    }
}
