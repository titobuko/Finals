package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJPickaxe extends Entity{

    public static final String objName = "Pickaxe";
    
    public OBJPickaxe(GamePanel gp){
        super(gp);

        type = type_axe;
        name = objName;
        down1 = setUp("/res/object/pickaxe", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[Pickaxe]\n You will dig it!";
        price = 75;
        knockBackPower = 20;
        motion1_duration = 10;
        motion2_duration = 30;
    }
}
