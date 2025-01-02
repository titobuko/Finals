package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJManaCrystal extends Entity {
    GamePanel gp;
    public static final String objName = "Mana Crystal";

    public OBJManaCrystal(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = objName;
        down1 = setUp("/res/object/manacrystal_full", gp.tileSize, gp.tileSize);
        image = setUp("/res/object/manacrystal_full", gp.tileSize, gp.tileSize);
        image2 = setUp("/res/object/manacrystal_blank", gp.tileSize, gp.tileSize);
    }
    public boolean use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("Mana" + value);
        entity.mana += value;
        return true;
    }
}
