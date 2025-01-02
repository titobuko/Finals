package Main;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import Object.OBJCoin_Bronze;
import Object.OBJHeart;
import Object.OBJManaCrystal;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import Entity.Entity;

public class UI
{
    GamePanel gp;
    Graphics2D g2;
    public Font maruMonica;
    Font purisaB;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int  titleScreenState = 0; // 0:firstScreen 1:secondScreen
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int NPCSlotCol = 0;
    public int NPCSlotRow = 0;
    int subState = 0;
    int counter = 0;
    public Entity NPC;
    int charIndex = 0;
    String combinedText = "";

    public UI(GamePanel gp){

        this.gp = gp;
        try{

            InputStream is = getClass().getResourceAsStream("/res/font/Purisa Bold.ttf");  // Note the .ttf extension
            if (is == null) {
                System.out.println("Could not find purisaB font file");
            } else {
                purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
            }
            
            is = getClass().getResourceAsStream("/res/font/x12y16pxMaruMonica.ttf");  // Note the .ttf extension
            if (is == null) {
                System.out.println("Could not find maru monica font file");
            } else {
                maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            }
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //create hub object
        Entity heart = new OBJHeart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        Entity crystal = new OBJManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2; 
        Entity bronzecoin = new OBJCoin_Bronze(gp);
        coin = bronzecoin.down1;
    }
    public void addMessage(String text)
    {
        message.add(text);
        messageCounter.add(0);
    }
    public void draw(Graphics2D g2)
    {
       this.g2 = g2;

       g2.setFont(maruMonica);
       //g2.setFont(purisaB);
       g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       g2.setColor(Color.WHITE);

       //TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
       //playState
       if(gp.gameState == gp.playState){
          drawPlayerLife();
          drawMonsterLife();
          drawMessage();
       }
       //pauseState
       if(gp.gameState == gp.pauseState){
          drawPlayerLife();
       }
       //dialogueState
       if(gp.gameState == gp.dialogueState){
          drawPlayerLife();
          drawDialogueScreen();
       }
       //characterState
       if(gp.gameState == gp.characterState){
          drawCharacterScreen();
          drawInventory(gp.player, true);
       }
       //option state
       if(gp.gameState == gp.optionsState){
          drawOptionScreen();
       }
        //game over state
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }  
        //transition state
        if(gp.gameState == gp.transitionState){
           drawTransitions();
        }  
        //trade state
        if(gp.gameState == gp.tradeState){
            drawTradeScreen();
        }  
        //sleep state
        if(gp.gameState == gp.sleepState){
            drawSleepScreen();
        }  
    } 
    public void drawPlayerLife()
    {
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        int iconSize = 32;
        int manaStartX = (gp.tileSize/2)-5;
        int manaStartY = 0;
        
        //draw blank heart
        while(i < gp.player.maxLife/2){
            gp.drawImage(heart_blank, x, y, null);
            i++; 
            x += iconSize;
            manaStartY = y + 32;

            if(i % 8 == 0){
                x = gp.tileSize/2;
                y += iconSize;
            }
        } 
        //reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //draw current life
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life)
            {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }

        //draw max mana
        x = (gp.tileSize/2) - 5;
        y = (int) (gp.tileSize*1.5);
        i = 0;
        while(i < gp.player.maxMana){
            gp.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }

        //draw mana
        x = (gp.tileSize/2) - 5;
        y = (int) (gp.tileSize*1.5);
        i = 0;
        while(i < gp.player.mana){
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
        }
    } 
    public void drawMonsterLife(){
        
        for(int i = 0; i < gp.monster[1].length; i++){
        
        Entity monster = gp.monster[gp.currentMap][i];

        if(monster != null && monster.inCamera() == true){
                if(monster.hpBarOn == true && monster.boss == false){

                    double oneScale = (double)gp.tileSize/monster.maxLife;
                    double hpBarValue = oneScale*monster.life;
                
                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(monster.getScreenX() - 1, monster.getScreenY() - 16, gp.tileSize + 2, 12);
                                 
                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int)hpBarValue, 10);
                
                    monster.hpBarCounter++;
                    if(monster.hpBarCounter > 600){
                        monster.hpBarCounter = 0;
                        monster.hpBarOn = false;
                    }
                }
                else if(monster.boss == true){
                    double oneScale = (double)gp.tileSize*8/monster.maxLife;
                    double hpBarValue = oneScale*monster.life;

                    int x = gp.screenWidth/2 - gp.tileSize * 4;
                    int y = gp.tileSize*10;
                
                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(x-1, y-1, gp.tileSize * 8 + 2, 12);
                                 
                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(x, y, (int)hpBarValue, 10);

                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
                    g2.setColor(Color.white);
                    g2.drawString(monster.name, x + 4, y - 10);
                }
            }
        }
    }
    public void drawMessage(){
        int messageX = gp.tileSize;
        int messageY = gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for(int i = 0; i < message.size(); i++){

            if(message.get(i) != null){
                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;//message counter++
                messageCounter.set(i, counter);//set counter to array
                messageY += 50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }
    public void drawTitleScreen(){
        if(titleScreenState == 0){
            g2.setColor(new Color(0, 0, 0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 98F));
            String text = " Adventure";
            int x =  getXforCenteredText(text);
            int y = gp.tileSize*3;
        
            //shadow
            g2.setColor(Color.GRAY);
            g2.drawString(text, x + 5, y + 5);
         
            //Main Color
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y); 

            //BOY Image
            x = gp.screenWidth/2 - (gp.tileSize*2)/2;
            y += gp.tileSize*2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            //Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "NEW GAME";
            x =  getXforCenteredText(text);
            y += gp.tileSize*3.5;
            g2.drawString(text, x, y);
            if(commandNum == 0){
               g2.drawString(">", x-gp.tileSize, y);
            }

            text = "LOAD GAME";
            x =  getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
               g2.drawString(">", x-gp.tileSize, y);
            }

            text = "QUIT";
            x =  getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
               g2.drawString(">", x-gp.tileSize, y);
            }
        }
        else if(titleScreenState == 1){
            //selection Screen 
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));
            
            String text = "Select your class!";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Figther";
            x = getXforCenteredText(text);
            y = gp.tileSize*5;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-gp.tileSize, y);
            }
            text = "Thief";
            x = getXforCenteredText(text);
            y = gp.tileSize*6;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Sorcerer";
            x = getXforCenteredText(text);
            y = gp.tileSize*7;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y = gp.tileSize*10;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x-gp.tileSize, y);
            }
            
        }
    }
    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));        
        String text =  "Paused";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }
    public void drawDialogueScreen(){
        //window
        int x = gp.tileSize*3;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*6);
        int height = gp.tileSize*4;
       
        drawSubWindow(x, y, width, height);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;

       if(NPC.dialogues[NPC.dialogueSet][NPC.dialogueIndex] != null){
            //currentDialogue = NPC.dialogues[NPC.dialogueSet][NPC.dialogueIndex];

            char characters[] = NPC.dialogues[NPC.dialogueSet][NPC.dialogueIndex].toCharArray();
            if(charIndex < characters.length){
                gp.playSE(17);
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialogue = combinedText;
                charIndex++;
            }

            if(gp.keyH.enterPressed == true){

                charIndex = 0;
                combinedText = "";

                if(gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState){
                    NPC.dialogueIndex++;
                    gp.keyH.enterPressed = false;
                }
            }
       }
       else{ //if no text is in the array
            NPC.dialogueIndex = 0;
            if(gp.gameState == gp.dialogueState){
                gp.gameState = gp.playState;
            }
            if(gp.gameState == gp.cutsceneState){
                gp.csManager.scenePhase++;
            }
       }


        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;   
        }
    }
    public void drawCharacterScreen(){
        //Create frame
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //text
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;

        //names
        g2.drawString("Level", textX, textY);textY += lineHeight;
        g2.drawString("Life", textX, textY);textY += lineHeight;
        g2.drawString("Mana", textX, textY);textY += lineHeight;
        g2.drawString("Strength", textX, textY);textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);textY += lineHeight;
        g2.drawString("Attack", textX, textY);textY += lineHeight;
        g2.drawString("Defense", textX, textY);textY += lineHeight;
        g2.drawString("Exp", textX, textY);textY += lineHeight;
        g2.drawString("Next Level", textX, textY);textY += lineHeight;
        g2.drawString("Coin", textX, textY);textY += lineHeight + 10;
        g2.drawString("Weapon", textX, textY);textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);textY += lineHeight;

        //values
        int tailX = (frameX + frameWidth) - 30;
        //reset
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp .tileSize, textY - 24, null);
        textY +=  gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 24, null);
 
    }
    public void drawInventory(Entity entity, boolean cursor){

    int frameX = 0;
    int frameY = 0;
    int frameWidth = 0;
    int frameHeight = 0;
    int slotCol = 0;
    int slotRow = 0;

    if(entity == gp.player){
        frameX = gp.tileSize*12;
        frameY = gp.tileSize;
        frameWidth = gp.tileSize*6;
        frameHeight = gp.tileSize*5;
        slotCol = playerSlotCol;
        slotRow = playerSlotRow;
    }
    else{
        frameX = gp.tileSize*2;
        frameY = gp.tileSize;
        frameWidth = gp.tileSize*6;
        frameHeight = gp.tileSize*5;
        slotCol = NPCSlotCol;
        slotRow = NPCSlotRow;
    }

    //frame
    drawSubWindow(frameX, frameY, frameWidth, frameHeight);

    //slot
    final int slotXstart = frameX + 20;
    final int slotYstart = frameY + 20;
    int slotX = slotXstart;
    int slotY = slotYstart;
    int slotSize = gp.tileSize + 3;

    // Check if entity and inventory exist
    if(entity != null && entity.inventory != null) {
        //draw player items
        for(int i = 0; i < entity.inventory.size(); i++){
            if(entity.inventory.get(i) != null) {  // Add null check for inventory item

                //calculate the row and position column
                int row = i / 5;  // 5 items per row
                int col = i % 5;
                
                // Calculate pixel positions
                int x = slotXstart + (col * slotSize);
                int y = slotYstart + (row * slotSize);

                //equip cursor - Draw highlight first
                if(entity.inventory.get(i) == entity.currentWeapon || 
                    entity.inventory.get(i) == entity.currentShield ||
                    entity.inventory.get(i) == entity.currentLight){
                    g2.setColor(new Color(240, 190, 90));
                    g2.fillRoundRect(x, y, gp.tileSize, gp.tileSize, 10, 10);
                }

                //display amount
                if(entity == gp.player && entity.inventory.get(i).amount > 1){
                    g2.setFont(g2.getFont().deriveFont(32F));
                    int amountX;
                    int amountY;

                    String s = "" + entity.inventory.get(i).amount;
                    amountX = getXforAlignToRightText(s, x + 44);
                    amountY = y + gp.tileSize;

                    // Draw amount shadow
                    g2.setColor(new Color(60, 60, 60));
                    g2.drawString(s, amountX, amountY);
                    // Draw amount number
                    g2.setColor(Color.white);
                    g2.drawString(s, amountX-3, amountY-3);
                }
                
                // Draw the item sprite
                if(entity.inventory.get(i).down1 != null) {  // Add null check for item sprite
                    g2.drawImage(entity.inventory.get(i).down1, x, y, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }

    //cursor
    if(cursor == true){
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;
    
        //draw cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        //description frame
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize*3;

        //draw description text
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));

        int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

        if(itemIndex < entity.inventory.size() && entity.inventory.get(itemIndex) != null){
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
            for(String line: entity.inventory.get(itemIndex).description.split("\n")){
                g2.drawString(line, textX, textY);
                textY += 32;
            }

            g2.drawString("Durability:" + entity.inventory.get(itemIndex).durability, textX, textY+100);
        }
    }
        
}
    public void drawGameOverScreen(){
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);;

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "Game Over";
        //Shadow
        g2.setColor(Color.BLACK);
        x = getXforCenteredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y); 
        //Main
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        //retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y); 
        if(commandNum == 0){
            g2.drawString(">", x-40, y);
        }

        //back to the title screen
        text = "Quit";
        x = getXforCenteredText(text);
        y +=  55; 
        g2.drawString(text, x, y); 
        if(commandNum == 1){
            g2.drawString(">", x-40, y);
        }
    }
    public void drawOptionScreen(){
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        //subwindow
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch(subState){
            case 0: options_top(frameX, frameY); break;
            case 1: option_fullScreenNotification(frameX, frameY); break;
            case 2: option_control(frameX, frameY); break;
            case 3: options_endGameConfirmation(frameX, frameY); break;
        }
        gp.keyH.enterPressed = false;
    }
    public void options_top(int frameX, int frameY){

        int textX;
        int textY;

        //title
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //full screen on/off 
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2; 
        g2.drawString("FullScreen", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                if(gp.fullScreenOn == false){
                    gp.fullScreenOn = true;
                }
                else if(gp.fullScreenOn == true){
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }

        //Music
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY); 
        if(commandNum == 1){
            g2.drawString(">", textX-25, textY);
        }

        //SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if(commandNum == 2){
            g2.drawString(">", textX-25, textY);
        }

        //Control
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if(commandNum == 3){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 2;
                commandNum = 0;
            }
        }

        //End Game
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if(commandNum == 4){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 3;
                commandNum = 0;
            }
        }

        //Back
        textY += gp.tileSize*2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }

        //full screen check box
        textX = frameX + (int) (gp.tileSize*4.5);
        textY = frameY + gp.tileSize*2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if(gp.fullScreenOn == true){
            g2.fillRect(textX, textY, 24, 24);
        }

        //music volume 
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);//120/5 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        //se volume 
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();

    }
    public void option_fullScreenNotification(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "The change will take \neffect after restarting \nthe game.";

        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40; 
        }

        //Back
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX -25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
            }
        }
    }
    public void option_control(int frameX, int frameY){
        int textX;
        int textY;

        //title
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY); textY += gp.tileSize;

        textX = frameX + gp.tileSize*6;
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
        g2.drawString("F", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY); textY += gp.tileSize;

        //back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);    
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;  
                commandNum = 3;  
            }
        }

    }
    public void options_endGameConfirmation(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize *3;

        currentDialogue = "Quit the Game and \nReturn to the title screen? ";

        for(String line: currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        //yes 
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                gp.gameState = gp.titleState;
                gp.resetGame(true);
            }
        }
        //no
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true){
                subState = 0;
                commandNum = 4;
            }
        }
    }
    public void drawTransitions(){

        counter++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if(counter == 50){ // the transition is done
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
            gp.changeArea();
        }
    }
    public void drawTradeScreen(){
        int frameX = gp.tileSize * 2;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 10;
    
        switch (subState) {
            case 0:trade_select(frameX, frameY, frameWidth, frameHeight);break;
            case 1:trade_buy(frameX, frameY, frameWidth, frameHeight);break;
            case 2:trade_sell(frameX, frameY, frameWidth, frameHeight);break;
        }
    
        gp.keyH.enterPressed = false;
}
    public void trade_select(int frameX, int frameY, int frameWidth, int frameHeight){

        NPC.dialogueSet = 0;
        drawDialogueScreen();
        
        drawSubWindow(frameX + gp.tileSize * 13, frameY + gp.tileSize * 2, gp.tileSize * 3, (int) (gp.tileSize * 3.5));

        // Draw text
        int x = frameX + gp.tileSize * 14;
        int y = frameY + gp.tileSize * 3;
        g2.drawString("Buy", x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed) {
                subState = 1;
            }
        }
        y += gp.tileSize;
    
        g2.drawString("Sell", x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed) {
                subState = 2;
            }
        }
        y += gp.tileSize;
    
        g2.drawString("Leave", x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed) {
                commandNum = 0;
                NPC.startDialogue(NPC,1);
            }
        }

    }
    public void trade_buy(int frameX, int frameY, int frameWidth, int frameHeight){
        //draw player inventory
        drawInventory(gp.player, false);

        //draw npc inventory
        drawInventory(NPC, true);

        //draw hint window
        int x = gp.tileSize*2;
        int y = gp.tileSize*9;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x+24, y+60); 

        //draw playercoin window
        x = gp.tileSize*12;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60); 

        //draw price window
        int itemIndex = getItemIndexOnSlot(NPCSlotCol, NPCSlotRow);
        if(itemIndex < NPC.inventory.size()){
            x = (int)(gp.tileSize*5.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x+10, y+8, 32, 32, null);

            int price = NPC.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize*8-20);
            g2.drawString(text, x,y+32);
            
            //buy an item
            if(gp.keyH.enterPressed == true){
                if(NPC.inventory.get(itemIndex).price > gp.player.coin){
                    subState = 0;
                    NPC.startDialogue(NPC,2);  
                    drawDialogueScreen();
                }
                else{
                    if(gp.player.canObtainItem(NPC.inventory.get(itemIndex)) == true){
                        gp.player.coin -= NPC.inventory.get(itemIndex).price;
                    }
                    else{
                        subState = 0;
                        NPC.startDialogue(NPC,3);
                    }
                }
            }
        }
    }
    public void trade_sell(int frameX, int frameY, int frameWidth, int frameHeight){
        //draw player inventory
        drawInventory(gp.player, true);
        int x;
        int y;
        int width;
        int height;

        //draw hint window
        x = gp.tileSize*2;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x+24, y+60); 

        //draw playercoin window
        x = gp.tileSize*12;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coin: " + gp.player.coin, x + 24, y + 60); 

        //draw price window
        int itemIndex = getItemIndexOnSlot(playerSlotRow, playerSlotRow);
        if(itemIndex < gp.player.inventory.size()){
            x = (int)(gp.tileSize*15.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x+10, y+8, 32, 32, null);

            int price = gp.player.inventory.get(itemIndex).price/2;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize*18-20);
            g2.drawString(text, x,y+32);
            
            //sell an item
            if(gp.keyH.enterPressed == true){
                if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
                 gp.player.inventory.get(itemIndex) == gp.player.currentShield){

                    commandNum = 0;
                    subState = 0;
                    NPC.startDialogue(NPC,4);
                }
                else{
                    if(gp.player.inventory.get(itemIndex).amount > 1){
                        gp.player.inventory.get(itemIndex).amount--;
                    }
                    else{
                        gp.player.inventory.remove(itemIndex);
                    }
                    gp.player.coin += price;
                }
            }
        }
    }
    public void drawSleepScreen(){
        counter++;

        if(counter < 120){
            gp.eManager.lighting.filterAlpha += 0.01f;
            if(gp.eManager.lighting.filterAlpha > 1){
                gp.eManager.lighting.filterAlpha = 1f;
            }
        }
        if(counter >= 120){
            gp.eManager.lighting.filterAlpha -= 0.01f;
            if(gp.eManager.lighting.filterAlpha <= 0){
                gp.eManager.lighting.filterAlpha = 0f;
                counter = 0;
                gp.eManager.lighting.dayState = gp.eManager.lighting.day;
                gp.eManager.lighting.dayCounter = 0; 
                gp.gameState = gp.playState;
                gp.player.getImage();
            }
        }
    }
    public int getItemIndexOnSlot(int slotCol, int slotRow){
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex; 
    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        //window border
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width - 10, height - 10, 25, 25);
    }
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    
    } 
    public int getXforAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    
    } 
}