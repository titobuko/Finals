package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJCoin_Bronze extends Entity {

    GamePanel gp;
    public static final String objName = "Bronze Coin";

    public OBJCoin_Bronze(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = objName;
        value = 5;
        down1 = setUp("/res/object/coin_bronze", gp.tileSize, gp.tileSize);
    }
    public boolean use(Entity entity){
      gp.playSE(1);
      gp.ui.addMessage("Coin " + value);
      gp.player.coin += value;
      return true;
    }
    
}
