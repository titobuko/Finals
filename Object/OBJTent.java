package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJTent extends Entity {
    
    GamePanel gp;
    public static final String objName = "Tent";

    public OBJTent(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = objName;
        down1 = setUp("/res/object/tent", gp.tileSize, gp.tileSize);
        description = "[Tent]\nYou can sleep until\nnext morning.";
        price = 300;
        stackable = true;
    }
    public boolean use(Entity entity){
        gp.gameState = gp.sleepState;
        gp.playSE(14);
        gp.player.life = gp.player.maxLife; 
        gp.player.mana = gp.player.maxMana; 
        gp.player.getPlayerSleepingImage(down1);
        return true;
    }
}
