package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJSword_Normal extends Entity {

    public static final String objName = "Normal Sword";

    public OBJSword_Normal(GamePanel gp){
        super(gp);

        type = type_sword;
        name = objName;
        down1 = setUp("/res/object/sword_normal", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name  + "]\nAn old sword";
        price = 20;
        knockBackPower = 2;
        motion1_duration = 2;
        motion2_duration = 10;
    }
}
