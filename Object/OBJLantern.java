package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJLantern extends Entity {

    public static final String objName = "Lantern";
    
    public OBJLantern(GamePanel gp) {
        super(gp);

        type = type_light;
        name = objName;
        down1 = setUp("/res/object/lantern", gp.tileSize, gp.tileSize);
        description = "[Lantern]\nIlluminartes your \nsurroundings.";
        price = 200;
        lightRadius = 350;
    }
}
