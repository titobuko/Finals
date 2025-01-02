package Main;

import Entity.NPCOldMan;
import Entity.NPC_BigRock;
import Entity.NPC_Merchant;
import Monster.Mon_Bat;
import Monster.Mon_GreenSlime;
import Monster.Mon_Orc;
import Monster.Mon_RedSlime;
import Monster.Mon_SkeletonLord;
import Object.OBJAxe;
import Object.OBJBlue_Heart;
import Object.OBJChest;
import Object.OBJDoor;
import Object.OBJDoor_Iron;
import Object.OBJKey;
import Object.OBJLantern;
import Object.OBJPickaxe;
import Object.OBJPotion_Red;
import Object.OBJTent;
import TileInteractive.IT_DestructibleWall;
import TileInteractive.IT_DryTree;
import TileInteractive.IT_MetalPlate;
import data.Progress;

public class AssetSetter 
{
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject() {

        int mapNum = 0;
        int i = 0;

        gp.obj[mapNum][i] = new OBJAxe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*33;
        gp.obj[mapNum][i].worldY = gp.tileSize*7;
        i++;
        gp.obj[mapNum][i] = new OBJLantern(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*18;
        gp.obj[mapNum][i].worldY = gp.tileSize*20;
        i++;
        gp.obj[mapNum][i] = new OBJTent(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*19;
        gp.obj[mapNum][i].worldY = gp.tileSize*20;
        i++;
        gp.obj[mapNum][i] = new OBJDoor(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*14;
        gp.obj[mapNum][i].worldY = gp.tileSize*28;
        i++;
        gp.obj[mapNum][i] = new OBJDoor(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*12;
        gp.obj[mapNum][i].worldY = gp.tileSize*12;
        i++;
        gp.obj[mapNum][i] = new OBJChest(gp);
        gp.obj[mapNum][i].setLoot(new OBJKey(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*30;
        gp.obj[mapNum][i].worldY = gp.tileSize*29;
        i++;
        gp.obj[mapNum][i] = new OBJChest(gp);
        gp.obj[mapNum][i].setLoot(new OBJKey(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*17;
        gp.obj[mapNum][i].worldY = gp.tileSize*20;
        i++;
        gp.obj[mapNum][i] = new OBJChest(gp);
        gp.obj[mapNum][i].setLoot(new OBJPotion_Red(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*16;
        gp.obj[mapNum][i].worldY = gp.tileSize*20;
        i++;

        mapNum = 2;
        i = 0;
        gp.obj[mapNum][i] = new OBJChest(gp);
        gp.obj[mapNum][i].setLoot(new OBJPickaxe(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*40;
        gp.obj[mapNum][i].worldY = gp.tileSize*41;
        i++;
        gp.obj[mapNum][i] = new OBJChest(gp);
        gp.obj[mapNum][i].setLoot(new OBJPotion_Red(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*13;
        gp.obj[mapNum][i].worldY = gp.tileSize*16;
        i++;
        gp.obj[mapNum][i] = new OBJChest(gp);
        gp.obj[mapNum][i].setLoot(new OBJPotion_Red(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*26;
        gp.obj[mapNum][i].worldY = gp.tileSize*34;
        i++;
        gp.obj[mapNum][i] = new OBJChest(gp);
        gp.obj[mapNum][i].setLoot(new OBJPotion_Red(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*27;
        gp.obj[mapNum][i].worldY = gp.tileSize*15 ;
        i++;
        gp.obj[mapNum][i] = new OBJDoor_Iron(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*18;
        gp.obj[mapNum][i].worldY = gp.tileSize*23 ;
        i++;

        mapNum = 3;
        i++;
        gp.obj[mapNum][i] = new OBJDoor_Iron(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*25;
        gp.obj[mapNum][i].worldY = gp.tileSize*15 ;
        i++;
        gp.obj[mapNum][i] = new OBJBlue_Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*25;
        gp.obj[mapNum][i].worldY = gp.tileSize*8 ;
        i++;
    }
    public void setNPC()
    {
        int mapNum= 0;
        int i = 0;
        
        //map 0
        gp.NPC[mapNum][i] = new NPCOldMan(gp);
        gp.NPC[mapNum][i].worldX = gp.tileSize*21;
        gp.NPC[mapNum][i].worldY = gp.tileSize*21;
        i++;

        //map 1
        mapNum = 1;
        i = 0;
        gp.NPC[mapNum][i] = new NPC_Merchant(gp);
        gp.NPC[mapNum][i].worldX = gp.tileSize*12;
        gp.NPC[mapNum][i].worldY = gp.tileSize*7;
        i++;

        //map 2
        mapNum = 2;
        i = 0;
        gp.NPC[mapNum][i] = new NPC_BigRock(gp);
        gp.NPC[mapNum][i].worldX = gp.tileSize*20;
        gp.NPC[mapNum][i].worldY = gp.tileSize*25;
        i++;
        gp.NPC[mapNum][i] = new NPC_BigRock(gp);
        gp.NPC[mapNum][i].worldX = gp.tileSize*11;
        gp.NPC[mapNum][i].worldY = gp.tileSize*18;
        i++;
        gp.NPC[mapNum][i] = new NPC_BigRock(gp);
        gp.NPC[mapNum][i].worldX = gp.tileSize*23;
        gp.NPC[mapNum][i].worldY = gp.tileSize*14;
        i++;
    }
    public void setMonster()
    {
        int mapNum = 0;
        int i = 0;
        gp.monster[mapNum][i] = new Mon_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*21;
        gp.monster[mapNum][i].worldY = gp.tileSize*38;
        i++;
        gp.monster[mapNum][i] = new Mon_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*23;
        gp.monster[mapNum][i].worldY = gp.tileSize*42;
        i++;
        gp.monster[mapNum][i] = new Mon_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*24;
        gp.monster[mapNum][i].worldY = gp.tileSize*27;
        i++;
        gp.monster[mapNum][i] = new Mon_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*34;
        gp.monster[mapNum][i].worldY = gp.tileSize*42;
        i++;
        gp.monster[mapNum][i] = new Mon_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*38;
        gp.monster[mapNum][i].worldY = gp.tileSize*42;
        i++;
        gp.monster[mapNum][i] = new Mon_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*38;
        gp.monster[mapNum][i].worldY = gp.tileSize*42;
        i++;
        gp.monster[mapNum][i] = new Mon_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*35;
        gp.monster[mapNum][i].worldY = gp.tileSize*8;
        i++;
        gp.monster[mapNum][i] = new Mon_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*35;
        gp.monster[mapNum][i].worldY = gp.tileSize*10;
        i++;
        gp.monster[mapNum][i] = new Mon_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*40;
        gp.monster[mapNum][i].worldY = gp.tileSize*9;
        i++;
        gp.monster[mapNum][i] = new Mon_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*12;
        gp.monster[mapNum][i].worldY = gp.tileSize*33;
        i++;

        mapNum = 2;
        i++;
        gp.monster[mapNum][i] = new Mon_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*34;
        gp.monster[mapNum][i].worldY = gp.tileSize*39;
        i++;
        gp.monster[mapNum][i] = new Mon_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*36;
        gp.monster[mapNum][i].worldY = gp.tileSize*25;
        i++;
        gp.monster[mapNum][i] = new Mon_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*29;
        gp.monster[mapNum][i].worldY = gp.tileSize*26;
        i++;
        gp.monster[mapNum][i] = new Mon_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*28;
        gp.monster[mapNum][i].worldY = gp.tileSize*11;
        i++;
        gp.monster[mapNum][i] = new Mon_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*10;
        gp.monster[mapNum][i].worldY = gp.tileSize*19;
        i++;

        mapNum = 3;
        i++;

        if(Progress.skeletonLordDefeated == false){
            gp.monster[mapNum][i] = new Mon_SkeletonLord(gp);
            gp.monster[mapNum][i].worldX = gp.tileSize*23;
            gp.monster[mapNum][i].worldY = gp.tileSize*16;
            i++;
        }
    }
    public void setInteractiveTile(){

        int mapNum = 0;
        int i = 0;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 27, 12);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 28, 12);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 29, 12);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 30, 12);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 31, 12);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 32, 12);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 33, 12);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 31, 21);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 18, 40);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 17, 40);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 16, 40);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 15, 40);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 14, 40);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 13, 40);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 13, 41);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 12, 41);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 11, 41);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 10, 41);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 10, 40);i++;

        gp.iTile[mapNum][i] = new IT_DryTree(gp, 25, 27);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 26, 27);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 27, 28);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 27, 29);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 27, 30);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 27, 31);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 28, 31);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 29, 31);i++;
        
        mapNum = 2;
        i = 0;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 30);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 17, 31);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 17, 32);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 17, 34);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 34);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18,33);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 10, 22);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 10, 24);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 18);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 19);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 20);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 38, 21);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 13);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 18, 14);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 22, 28);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 30, 28);i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp, 32, 28);i++;

        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 20, 22);i++;
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 8, 17);i++;
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 39, 31);i++;
    }
}
