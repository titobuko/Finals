package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJHeart extends Entity 
{
    GamePanel gp;
    public static final String objName = "Heart";
    public OBJHeart(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = objName;
        value = 2;
        down1 = setUp("/res/object/heart_full",  gp.tileSize, gp.tileSize);
        image = setUp("/res/object/heart_full",  gp.tileSize, gp.tileSize);
        image2 = setUp("/res/object/heart_half",  gp.tileSize, gp.tileSize);
        image3 = setUp("/res/object/heart_blank", gp.tileSize, gp.tileSize);

    }
    public boolean use(Entity entity){
        gp.playSE(2);
        gp.ui.addMessage("Life" + value);
        entity.life += value;
        return true;
    }
}
