package Entity;

import java.awt.Rectangle;

import Main.GamePanel;
import Object.OBJAxe;
import Object.OBJKey;
import Object.OBJPotion_Red;
import Object.OBJShield_Blue;
import Object.OBJShield_Wood;
import Object.OBJSword_Normal;

public class NPC_Merchant extends Entity {
    
     public NPC_Merchant(GamePanel gp)
    {
        super(gp);
        direction = "down";
        speed = 0;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        getNPCImage();
        setDialogue();
        setItems();
    }
     public void getNPCImage() 
    {
        up1 = setUp("/res/NPC/merchant_down_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/res/NPC/merchant_down_2", gp.tileSize, gp.tileSize);
        down2 = setUp("/res/NPC/merchant_down_2", gp.tileSize, gp.tileSize);
        down1 = setUp("/res/NPC/merchant_down_2",  gp.tileSize, gp.tileSize);
        left1 = setUp("/res/NPC/merchant_down_1",  gp.tileSize, gp.tileSize);
        left2 = setUp("/res/NPC/merchant_down_2",  gp.tileSize, gp.tileSize);
        right1 = setUp("/res/NPC/merchant_down_1",  gp.tileSize, gp.tileSize);
        right2 = setUp("/res/NPC/merchant_down_2", gp.tileSize, gp.tileSize);
    }
    public void setDialogue()
    {
        dialogues[0][0] = "He he, so you found me. \nI have some good stuff. \nDo you want to trade?"; 
        dialogues[1][0] = "Come again, hehe";
        dialogues[2][0] = "You need more coin to buy that!";
        dialogues[3][0] = "You cannot carry anymore!";
        dialogues[4][0] = "You cannot sell an equipped item!";
    }
    public void setItems(){

        inventory.add(new OBJPotion_Red(gp));
        inventory.add(new OBJKey(gp));
        inventory.add(new OBJSword_Normal(gp));
        inventory.add(new OBJAxe(gp));
        inventory.add(new OBJShield_Wood(gp));
        inventory.add(new OBJShield_Blue(gp));
    }
    public void speak(){
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.NPC =this;
    }
}
