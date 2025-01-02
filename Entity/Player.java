package Entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import Main.GamePanel;
import Main.keyHandler;
import Object.OBJAxe;
import Object.OBJFireball;
import Object.OBJKey;
import Object.OBJShield_Wood;
import Object.OBJSword_Normal;

public class Player extends Entity 
{
    keyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean invisible = false;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;

    public Player(GamePanel gp, keyHandler keyH){

        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        // Set up collision area
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
    }
    public void setDefaultValues(){

        worldX = gp.tileSize * 23;
        worldY=  gp.tileSize * 21;

        //merchant map
        //gp.currentMap = 1;
        //worldX = gp.tileSize*12;
        //worldY = gp.tileSize*11;

        //dungeon entrance
        // worldX = gp.tileSize*12;
        // worldY = gp.tileSize*11;

        // //dungeon b2
        // gp.currentMap = 3;
        // worldX = gp.tileSize*25;
        // worldY = gp.tileSize*29;

        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";

        //Player Status
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 1; //more strength more damage
        dexterity = 1; //more dexterity he has the less damage he receives
        exp = 0;
        nextLevelExp = 5;
        coin = 500;
        currentWeapon = new OBJSword_Normal(gp);
        currentShield = new OBJShield_Wood(gp);
        currentLight = null;
        projectile = new OBJFireball(gp);
        attack = getAttack(); //total attack value = strenght and weapon 
        defense = getDefense(); //total defense value = dexterity and shield

        getImage();
        getAttackImage();
        getGuardImage();
        setItems();
        setDialogue();
    }
    public void setDefaultPosition(){

        gp.currentMap = 0;
        worldX = gp.tileSize*23;
        worldY = gp.tileSize*21;
        direction = "down";
    }
    public void setDialogue(){
        dialogues[0][0] = "You are level " + level + "now\n" 
                  + "You feel stronger";
    }
    public void restoreStatus(){
        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        invisible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockBack = false;
        lightUpdated = true;
    }
    public void setItems(){

        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJAxe(gp));
        inventory.add(new OBJKey(gp));
    }
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }
    public int getCurrentWeaponSlot(){
        int currentWeaponSlot = 0;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == currentWeapon){
              currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentShieldSlot(){
        int currentShieldSlot = 0;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == currentShield){
              currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }
    public void getImage() 
    {
        up1 = setUp("/res/PLAYER/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/res/PLAYER/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setUp("/res/PLAYER/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("/res/PLAYER/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setUp("/res/PLAYER/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setUp("/res/PLAYER/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setUp("/res/PLAYER/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setUp("/res/PLAYER/boy_right_2", gp.tileSize, gp.tileSize);
    }
    public void getPlayerSleepingImage(BufferedImage image){
        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2= image;
        right1 = image;
        right2 = image;
    }
    
    public void getAttackImage()
    {
        if(currentWeapon.type == type_sword){
            attackUp1 = setUp("/res/PLAYER/player_attack_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setUp("/res/PLAYER/player_attack_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setUp("/res/PLAYER/player_attack_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setUp("/res/PLAYER/player_attack_down_2",gp.tileSize, gp.tileSize*2);
            attackRight1 = setUp("/res/PLAYER/player_attack_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setUp("/res/PLAYER/player_attack_right_2", gp.tileSize*2, gp.tileSize);
            attackLeft1 = setUp("/res/PLAYER/player_attack_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setUp("/res/PLAYER/player_attack_left_2", gp.tileSize*2, gp.tileSize);
        }
        if(currentWeapon.type == type_axe){
            attackUp1 = setUp("/res/PLAYER/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setUp("/res/PLAYER/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setUp("/res/PLAYER/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setUp("/res/PLAYER/boy_axe_down_2",gp.tileSize, gp.tileSize*2);
            attackRight1 = setUp("/res/PLAYER/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setUp("/res/PLAYER/boy_axe_right_2", gp.tileSize*2, gp.tileSize);
            attackLeft1 = setUp("/res/PLAYER/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setUp("/res/PLAYER/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
        }
        if(currentWeapon.type == type_pickaxe){
            attackUp1 = setUp("/res/PLAYER/player_pick_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setUp("/res/PLAYER/palyer_pick_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setUp("/res/PLAYER/player_pick_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setUp("/res/PLAYER/player_pick_down_2",gp.tileSize, gp.tileSize*2);
            attackRight1 = setUp("/res/PLAYER/player_pick_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setUp("/res/PLAYER/player_pick_right_2", gp.tileSize*2, gp.tileSize);
            attackLeft1 = setUp("/res/PLAYER/player_pick_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setUp("/res/PLAYER/player_pick_left_2", gp.tileSize*2, gp.tileSize);
        }
    }
    public void getGuardImage(){
        guardUp = setUp("/res/PLAYER/player_guard_up", gp.tileSize, gp.tileSize);
        guardDown = setUp("/res/PLAYER/player_guard_down", gp.tileSize, gp.tileSize);
        guardLeft = setUp("/res/PLAYER/player_guard_left", gp.tileSize, gp.tileSize);
        guardRight = setUp("/res/PLAYER/player_guard_right", gp.tileSize, gp.tileSize);
    }
    public void update(){

        if(knockBack == true){

            collisionOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, true);
            gp.cChecker.checkEntity(this, gp.NPC);
            gp.cChecker.checkEntity(this, gp.monster);
            gp.cChecker.checkEntity(this, gp.iTile);


            if(collisionOn == true){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else if(collision == false){
                switch(knockBackDirection){
                    case "up":worldY-= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            knockBackCounter++;
            if(knockBackCounter == 10){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }

        }
        else if(attacking == true){
            attacking();
        }
        else if(keyH.spacePressed == true){
            guarding = true;
            guardCounter++;
        }
        else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true 
        || keyH.enterPressed == true){
            if (keyH.upPressed == true ){
                direction = "up";
            }
            else if(keyH.downPressed == true){
                direction = "down";
            }
            else if(keyH.leftPressed == true){
                direction = "left";
            }
            else if(keyH.rightPressed == true){
                direction = "right";
            }

            //check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //check object colllision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //Check NPC Collsion
            int NPCIndex = gp.cChecker.checkEntity(this, gp.NPC);
            interactNPC(NPCIndex);

            //Check monster collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //check interactive tile collision
            int iTileIndex =  gp.cChecker.checkEntity(this, gp.iTile);

            //Check event
            gp.eHandler.checkEvent();

            //if collision is false, player can move
            if(!collisionOn && !keyH.enterPressed){
                switch(direction){
                    case "up":worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            if(keyH.enterPressed == true && attackCanceled == false){
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;

                //decrease durability
                //currentWeapon.durability--;

            }
            attackCanceled = false;
            gp.keyH.enterPressed = false;
            guarding = false; 
            guardCounter = 0;

            spriteCounter++;
            if(spriteCounter > 10){
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }

        }
        else  // When not moving
        {
            spriteNum = 1;  // Reset to standing sprite
            spriteCounter = 0;
        }

        // Handle enter press separately
        if(keyH.enterPressed) {
            gp.playSE(7);
            attacking = true;
            gp.keyH.enterPressed = false;
        }

        if(gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30
            &&  projectile.haveResource(this)){

            //set default coordinates direction and user
            projectile.set(worldX, worldY, direction, true,this);

            //substract the cost (mana, ammo etc)
            projectile.substractResource(this);

            //check vacancy
            for(int i = 0; i < gp.projectile[1].length; i++){
                if(gp.projectile[gp.currentMap][i] == null){
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }

            shotAvailableCounter = 0;

            gp.playSE(9);
        }

        //outside of key if statement
        if(invisible == true){
            invisibleCounter++;
            if(invisibleCounter > 60){
                invisible = false;
                transparent = false;
                invisibleCounter = 0;
            }
        } 
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        } 
        if(life > maxLife){
            life = maxLife;
        }   
        if(mana > maxMana){
            mana = maxMana;
        }
        if(keyH.godModeOn == false){
            if(life <= 0){
                gp.gameState = gp.gameOverState;
                gp.ui.commandNum = -1;
                gp.stopMusic();
                gp.playSE(12);
            }    
        }
    }
    public void pickUpObject(int i){
        if (i != 999) {

             // Get name of object being interacted with
             String objectName = gp.obj[gp.currentMap][i].name;

             // Handle door interaction
             if(objectName.equals("Door")) {
                if(searchItemInInventory("Key") != 999) { // Player has key
                    gp.playSE(3); // Play door opening sound
                    
                    // Remove the key from inventory
                    int keyIndex = searchItemInInventory("Key");
                    if(inventory.get(keyIndex).amount > 1) {
                        inventory.get(keyIndex).amount--;
                    } else {
                        inventory.remove(keyIndex);
                    }
                    // Remove the door
                    gp.obj[gp.currentMap][i] = null;
                    gp.ui.addMessage("You opened the door!");
                 } else {
                    gp.ui.addMessage("You need a key!");
                 }
                 return;
             }
 
             // Handle other object types
             if(gp.obj[gp.currentMap][i].type == type_pickupOnly) {
                 gp.obj[gp.currentMap][i].use(this);
                 gp.obj[gp.currentMap][i] = null;
             }
             else if(gp.obj[gp.currentMap][i].type == type_obstacle) {
                 if(keyH.enterPressed == true) {
                     attackCanceled = true;
                     gp.obj[gp.currentMap][i].interact();
                 }
             }
             else {
                String text;
                if(canObtainItem(gp.obj[gp.currentMap][i]) == true) {
                    gp.playSE(1);
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                 } else {
                    text = "You cannot carry anymore";
                 }
                 gp.ui.addMessage(text);
                 gp.obj[gp.currentMap][i] = null;
            }
        }
    }
    public void interactNPC(int i){
        if(gp.keyH.enterPressed == true){
            if(i != 999){
                attackCanceled = true; 
                gp.NPC[gp.currentMap][i].speak();  //fixed 
                gp.NPC[gp.currentMap][i].move(direction);  
            }
        }
    }
    public void contactMonster(int i)
    {
        if(i != 999){
            if(invisible == false && gp.monster[gp.currentMap][i].died == false){ //fixed
                gp.playSE(6);
    
                // Calculate damage dealt by monster
                int damage = gp.monster[gp.currentMap][i].attack - defense; //fixed
                if(damage < 1){
                    damage = 1;
                }
                
                life -= damage;
                invisible = true;
                transparent = true;
            }
        }
    }
    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower)
        {
        if(i != 999) {
            if(gp.monster[gp.currentMap][i].invisible == false) {

                gp.playSE(5);

                if(knockBackPower > 0){
                    setKnockBack(gp.monster[gp.currentMap][i], attacker,knockBackPower);
                }
                if(gp.monster[gp.currentMap][i].offBalance == true){
                    attack *= 5;
                }

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if(damage < 0) {
                    damage = 0;
                }
    
                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                gp.monster[gp.currentMap][i].invisible = true;
                gp.monster[gp.currentMap][i].damageReaction();
    
                if(gp.monster[gp.currentMap][i].life <= 0) {                
                    gp.monster[gp.currentMap][i].died = true;
                    gp.ui.addMessage("Killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("Exp " + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                    
                    // Call checkDrop when monster dies
                    gp.monster[gp.currentMap][i].checkDrop();
                }
            }
        }
    }
    public void damageInteractiveTile(int i){
        if(i != 999 && gp.iTile[gp.currentMap][i].destructible == true 
       && gp.iTile[gp.currentMap][i].isCorrectItem(this) == true 
       && gp.iTile[gp.currentMap][i].invisible == false) {
        
        // Play sound effect
        gp.iTile[gp.currentMap][i].playSE();
        
        // Reduce life by 1
        gp.iTile[gp.currentMap][i].life--;
        
        // Make temporarily invisible for hit feedback
        gp.iTile[gp.currentMap][i].invisible = true;

        // Generate particle
        generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

        // Only destroy if life reaches 0
        if(gp.iTile[gp.currentMap][i].life <= 0){
            //gp.iTile[gp.currentMap][i].checkDrop();
            gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
        }
        
       }
    }
    public void damageProjectile(int i){
        if(i != 999){
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }
    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp*2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;

            dialogues[0][0] = "You are level " + level + "now\n" 
                  + "You feel stronger";
            setDialogue();
            startDialogue(this, 0);
        }
    }
    public void selectItem(){
        
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == type_sword || selectedItem.type == type_axe  || selectedItem.type == type_pickaxe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }
            if (selectedItem.type == type_shield) {
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_light){
                if(currentLight == selectedItem){
                    currentLight = null;
                }else{
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }   
            if(selectedItem.type == type_consumable){
                if(selectedItem.use(this) == true){
                    if(selectedItem.amount > 1){
                        selectedItem.amount--;
                    }else{
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }
    public int searchItemInInventory(String itemName){

        int itemIndex = 999;

        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Entity item){
        
        boolean canObtain = false;

        Entity newItem = gp.eGenerator.getObject(item.name);

        //check if stackble
        if(newItem.stackable == true){
            int index = searchItemInInventory(newItem.name);

            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }
            else{ //new item so need to check vacancy
                if(inventory.size() != maxInventorySize){
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        }
        else{ // not stackble so check vacancy
            if(inventory.size() != maxInventorySize){
                inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
    }
    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch(direction)
        {
            case "up":
                if(attacking == false) {
                    if(spriteNum == 1){image = up1;}
                    if(spriteNum == 2){image = up2;}
                }
                if(attacking == true){
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1){image = attackUp1;}
                    if(spriteNum == 2){image = attackUp2;}
                }
                if(guarding == true){
                    image = guardUp;        
                }
                break;
            case "down":
                if(attacking == false){
                    if(spriteNum == 1){image = down1;}
                    if(spriteNum == 2){image = down2;}
                }
                if(attacking == true){
                    if(spriteNum == 1){image = attackDown1;}
                    if(spriteNum == 2){image = attackDown2;}
                }
                if(guarding == true){
                    image = guardDown;        
                }
                break;
            case "left":
                if(attacking == false){
                    if(spriteNum == 1){image = left1;}
                    if(spriteNum == 2){image = left2;}
                }
                if (attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1){image = attackLeft1;}
                    if(spriteNum == 2){image = attackLeft2;}
                }
                if(guarding == true){
                    image = guardLeft;        
                }
                break;
            case "right":
                if(attacking == false){
                    if(spriteNum == 1){image = right1;}
                    if(spriteNum == 2){image = right2;}
                }
                if(attacking == true){
                    if(spriteNum == 1){image = attackRight1;}
                    if(spriteNum == 2){image = attackRight2;}
                }
                if(guarding == true){
                    image = guardRight;        
                }
                break;
        }
        if(transparent == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        if(drawing == true){
            g2.drawImage(image, tempScreenX, tempScreenY, null);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }
}
