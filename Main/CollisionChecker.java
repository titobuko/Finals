package Main;

import Entity.Entity;
import Entity.NPCOldMan;
import Monster.Mon_Bat;
import Monster.Mon_GreenSlime;
import Monster.Mon_Orc;
import Monster.Mon_RedSlime;
import Monster.Mon_SkeletonLord;

public class CollisionChecker {

    GamePanel gp;
    public CollisionChecker (GamePanel gp)
    {
        this.gp = gp;
    }
    public void checkTile(Entity entity){

      // Calculate collision box edges
      int entityLeftWorldX = entity.worldX + entity.solidArea.x;
      int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
      int entityTopWorldY = entity.worldY + entity.solidArea.y;
      int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
      
      // Calculate row and col positions
      int entityLeftCol = entityLeftWorldX/gp.tileSize;
      int entityRightCol = entityRightWorldX/gp.tileSize;
      int entityTopRow = entityTopWorldY/gp.tileSize;
      int entityBottomRow = entityBottomWorldY/gp.tileSize;
      
      int tileNum1, tileNum2;

      //use a temporal direction when its being knockbacked
      String direction = entity.direction;
      if(entity.knockBack == true){
        direction = entity.knockBackDirection;
      }

      switch(entity.direction)
      {
      case "up":
      entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
        if (isWithinMapBounds(entityLeftCol, entityRightCol, entityTopRow)) {
            tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
            tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
            if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
              entity.collisionOn = true;
              if(entity instanceof NPCOldMan || entity instanceof Mon_GreenSlime || entity instanceof Mon_RedSlime 
              || entity instanceof Mon_Orc || entity instanceof Mon_Bat || entity instanceof Mon_SkeletonLord) {
                  entity.onCollision();
                 entity.onCollision(); // Optional: Handle specific NPC/Monster collision behavior
                        }
                    }
                } else {
                  entity.collisionOn = true;
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                if (isWithinMapBounds(entityLeftCol, entityRightCol, entityBottomRow)) {
                    tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                    tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                    if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                        if(entity instanceof NPCOldMan || entity instanceof Mon_GreenSlime || entity instanceof Mon_RedSlime 
                            || entity instanceof Mon_Orc || entity instanceof Mon_Bat || entity instanceof Mon_SkeletonLord) {
                            entity.onCollision();
                        }
                    }
                } else {
                    entity.collisionOn = true;
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                if (isWithinMapBounds(entityLeftCol, entityTopRow, entityBottomRow)) {
                    tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                  if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                      entity.collisionOn = true;
                      if(entity instanceof NPCOldMan || entity instanceof Mon_GreenSlime || entity instanceof Mon_RedSlime 
                      || entity instanceof Mon_Orc || entity instanceof Mon_Bat || entity instanceof Mon_SkeletonLord) {
                          entity.onCollision();
                        }
                    }
                } else {
                    entity.collisionOn = true;
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                if (isWithinMapBounds(entityRightCol, entityTopRow, entityBottomRow)) {
                    tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                    if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                        if(entity instanceof NPCOldMan || entity instanceof Mon_GreenSlime || entity instanceof Mon_RedSlime 
                        || entity instanceof Mon_Orc || entity instanceof Mon_Bat || entity instanceof Mon_SkeletonLord) {
                          entity.onCollision();
                        }
                    }
                } else {
                    entity.collisionOn = true;
                }
                break;
              }
        
    }

    // Helper method to check if positions are within map bounds
    private boolean isWithinMapBounds(int col1, int col2, int row) {
      return col1 >= 0 && col1 < gp.maxWorldCol &&
        col2 >= 0 && col2 < gp.maxWorldCol &&
        row >= 0 && row < gp.maxWorldRow;
      }
    
   
    public int checkObject(Entity entity, boolean player){
      int index = 999;
      for(int i = 0; i < gp.obj[1].length; i++){ //fixed
        if(gp.obj[gp.currentMap][i] != null){  //fixed

          // Get entity's solid area position
          entity.solidArea.x = entity.worldX + entity.solidArea.x;
          entity.solidArea.y = entity.worldY + entity.solidArea.y;

          //object solid area position
          gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x; //fixed
          gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y; //fixed

          switch(entity.direction){
            case "up": entity.solidArea.y -= entity.speed; break;
            case "down": entity.solidArea.y += entity.speed; break;
            case "left": entity.solidArea.x -= entity.speed; break;
            case "right": entity.solidArea.x += entity.speed; break;
          }
          if(entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)){//fixed
            if(gp.obj[gp.currentMap][i].collision == true){ //fixed
              entity.collisionOn = true;
            }
            if(player == true){
              index = i;
            }
          }
          entity.solidArea.x = entity.solidAreaDefaultX;
          entity.solidArea.y = entity.solidAreaDefaultY;
          gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX; //fixed
          gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY; //fixed
        }
      }
      return index;
    }
    // NPC or monster
    public int checkEntity(Entity entity, Entity[][] target)
    {
      int index = 999;

      //use a temporal direction when its being knockbacked
      String direction = entity.direction;
      if(entity.knockBack == true){
        direction = entity.knockBackDirection;
      }

      for(int i = 0; i < target[1].length; i++){ //fixed
        if(target[gp.currentMap][i] != null){ //fixed

          //solid area position
          entity.solidArea.x = entity.worldX + entity.solidArea.x;
          entity.solidArea.y = entity.worldY + entity.solidArea.y;

          //object solid area position
          target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
          target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

          switch(direction){
            case "up": entity.solidArea.y -= entity.speed; break;
            case "down": entity.solidArea.y += entity.speed; break;
            case "left": entity.solidArea.x -= entity.speed; break;
            case "right": entity.solidArea.x += entity.speed; break;
          }

          if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)){ //fixed
            if(target[gp.currentMap][i] != null){ //fixed
              entity.collisionOn = true;
                index = i;
            }
          }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX; //fixed
        target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY; //fixed
        }
      }
      return index;
    }
    public boolean checkPlayer(Entity entity)
    {
      boolean contactPlayer = false;
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        
        //object solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch(entity.direction)
        {
          case "up": entity.solidArea.y -= entity.speed; break;
          case "down": entity.solidArea.y += entity.speed; break;
          case "left": entity.solidArea.x -= entity.speed; break;
          case "right": entity.solidArea.x += entity.speed; break;
        }
          if(entity.solidArea.intersects(gp.player.solidArea))
          {
            entity.collisionOn = true;
            contactPlayer = true;
          }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }
}
