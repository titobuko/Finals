package Object;

import Entity.Entity;
import Main.GamePanel;

public class OBJPotion_Red extends Entity{

    GamePanel gp;
    public static final String objName = "Red Potion";

    public OBJPotion_Red(GamePanel gp){
        super(gp);

        this.gp = gp;

        type = type_consumable;
        name = objName;
        down1 = setUp("/res/object/potion_red", gp.tileSize, gp.tileSize);
        description = "[Red Potion]\nHeals your Life" + value + ".";
        price = 25;
        stackable = true;

        setDialogue();
    }
    public void setDialogue(){
        dialogues[0][0] = "You drink the " + name + "!\n"
          + "your life has been restored by " + value + ".";

    }
    public boolean use(Entity entity){
        startDialogue(this, 0);
        entity.life += value;
        gp.playSE(2);
        return true;
    }
}
