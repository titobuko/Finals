package Main;

import Entity.Entity;
import Object.OBJAxe;
import Object.OBJBoots;
import Object.OBJChest;
import Object.OBJCoin_Bronze;
import Object.OBJDoor;
import Object.OBJDoor_Iron;
import Object.OBJFireball;
import Object.OBJHeart;
import Object.OBJKey;
import Object.OBJLantern;
import Object.OBJManaCrystal;
import Object.OBJPickaxe;
import Object.OBJPotion_Red;
import Object.OBJRock;
import Object.OBJShield_Blue;
import Object.OBJShield_Wood;
import Object.OBJSword_Normal;
import Object.OBJTent;

public class EntityGenerator {
    
    GamePanel gp;

    public EntityGenerator(GamePanel gp){
        this.gp = gp;
    }
    public Entity getObject(String itemName){
        
        Entity obj = null;

        switch(itemName){
            case OBJAxe.objName: obj = new OBJAxe(gp); break;
            case OBJBoots.objName: obj = new OBJBoots(gp); break;
            case OBJChest.objName: obj = new OBJChest(gp); break;
            case OBJCoin_Bronze.objName: obj = new OBJCoin_Bronze(gp);break;
            case OBJDoor_Iron.objName: obj = new OBJDoor_Iron(gp);break;
            case OBJDoor.objName: obj = new OBJDoor(gp); break;
            case OBJFireball.objName: obj = new OBJFireball(gp);break;
            case OBJHeart.objName: obj = new OBJHeart(gp);break;
            case OBJKey.objName: obj = new OBJKey(gp); break;
            case OBJLantern.objName: obj = new OBJLantern(gp); break;
            case OBJManaCrystal.objName: obj = new OBJManaCrystal(gp);break;
            case OBJPickaxe.objName: obj = new OBJPickaxe(gp);break;    
            case OBJPotion_Red.objName: obj = new OBJPotion_Red(gp); break;
            case OBJRock.objName: obj = new OBJRock(gp);break;
            case OBJShield_Blue.objName: obj = new OBJShield_Blue(gp); break;
            case OBJShield_Wood.objName: obj = new OBJShield_Wood(gp); break;
            case OBJSword_Normal.objName: obj = new OBJSword_Normal(gp); break;
            case OBJTent.objName: obj = new OBJTent(gp); break;  
        }
        return obj;
    }
}
