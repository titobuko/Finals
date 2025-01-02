package Object;


import Entity.Entity;
import Main.GamePanel;

public class OBJBoots extends Entity{
    
    public static final String objName = "Boots";

    public OBJBoots(GamePanel gp)
    {
        super(gp);
        name = objName;
        down1 = setUp("/res/object/boots", gp.tileSize, gp.tileSize);
    }
}
