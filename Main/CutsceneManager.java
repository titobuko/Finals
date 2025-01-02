package Main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import Entity.PlayerDummy;
import Monster.Mon_SkeletonLord;
import Object.OBJBlue_Heart;
import Object.OBJDoor_Iron;

public class CutsceneManager {
    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;
    int counter = 0;
    float alpha = 0f;
    int y;

    //scene Number
    public final int NA = 0;
    public final int skeletonLord = 1;
    public final int ending = 2;
    
    public CutsceneManager(GamePanel gp){
        this.gp = gp;
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;

        switch(sceneNum){
            case skeletonLord: scene_skeletonLord(); break;
            case ending: scene_ending(); break;
        }
    }
    public void scene_skeletonLord(){
        if(scenePhase == 0){
            gp.bossBattleOn = true;

            //shut down iron door
            for(int i = 0; i < gp.obj[1].length; i++){
                if(gp.obj[gp.currentMap][i] == null){
                    gp.obj [gp.currentMap][i] = new OBJDoor_Iron(gp);
                    gp.obj[gp.currentMap][i].worldX = gp.tileSize*25;
                    gp.obj[gp.currentMap][i].worldY = gp.tileSize*28;
                    gp.obj[gp.currentMap][i].temp = true;
                    gp.playSE(21);
                    break;
                }
            }
            //search a vacant slot for the dummy
           for(int i = 0; i < gp.NPC[1].length; i++){
                if(gp.NPC[gp.currentMap][i] == null){
                    gp.NPC[gp.currentMap][i] = new PlayerDummy(gp);
                    gp.NPC[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.NPC[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.NPC[gp.currentMap][i].direction = gp.player.direction;
                    break;
                }
           }

            gp.player.drawing = false;

            scenePhase++;
        }
        if(scenePhase == 1){
            gp.player.worldY -= 2;

            if(gp.player.worldY < gp.tileSize*16){
                scenePhase++;
            }
        } 
        if(scenePhase == 2){
            //seacrh boss
            for(int i = 0; i < gp.monster[1]. length; i++){
                if(gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].name == Mon_SkeletonLord.monName){
                    gp.monster[gp.currentMap][i].sleep = false;
                    gp.ui.NPC = gp.monster[gp.currentMap][i];
                    scenePhase++;
                    break;
                }
            }
        }
        if(scenePhase == 3){
            //the boss speaks
            gp.ui.drawDialogueScreen();
        }
        if(scenePhase == 4){
            //return to the player

            //search dummy 
            for(int i = 0; i < gp.NPC[1].length; i++){
                if(gp.NPC[gp.currentMap][i] != null && gp.NPC[gp.currentMap][i].name == PlayerDummy.npcName){
                    //restore the player position
                    gp.player.worldX = gp.NPC[gp.currentMap][i].worldX;
                    gp.player.worldY = gp.NPC[gp.currentMap][i].worldY;
                    //delete dummy
                    gp.NPC[gp.currentMap][i] = null;
                    break;
                }
            }
            //start drawing the player
            gp.player.drawing = true;
            
            //reset 
            sceneNum = NA;
            scenePhase = 0;
            gp.gameState = gp.playState;

            //change the music
            gp.stopMusic();
            gp.playSE(22);
        }
    }
    public void scene_ending(){
        if(scenePhase == 0){
            gp.stopMusic();
            gp.ui.NPC = new OBJBlue_Heart(gp);
            scenePhase++;
        }
        if(scenePhase == 1){
            //display dialogues
            gp.ui.drawDialogueScreen();
        }
        if(scenePhase == 2){
            //play fanfare
            gp.playSE(4);
        }
        if(scenePhase == 3){
            //waitr until the sound effect ends
            if(counterReached(300) == true){
                scenePhase++;
            }
        }   
        if(scenePhase == 4){

            //the screen gets darker
            alpha += 0.005f;
            if(alpha > 1f){
                alpha = 1f;
            }
            drawBlackBackground(alpha);

            if(alpha == 1f){
                alpha = 0;
                scenePhase++;                
            }
        }
        if(scenePhase == 5){
            drawBlackBackground(1f);
            //the screen gets darker
            alpha += 0.005f;
            if(alpha > 1f){
                alpha = 1f;
            }

            String text = "After the fierce battle with the Skeleton Lord, \n" 
                           + "Bean finally found the legendary treasure.\n"
                           + "But this is not the end of his journey.\n"
                           + "The adventurer Bean has just begun.";

            drawString(alpha, 38f, 200, text, 70);

            if(counterReached(600) == true){
                gp.playMusic(0);
                scenePhase++;
            }
        }
        if(scenePhase == 6){
            drawBlackBackground(1f);
            drawString(1f, 120f, gp.screenHeight/2, "Bean Adventure", 40);

            if(counterReached(600) == true){
                scenePhase++;
            }
        }
    }
    public boolean counterReached(int target){
        boolean counterReached = false;

        counter++;
        if(counter > target){
            counterReached = true;
        }
        return counterReached;
    }
    public void drawBlackBackground(float alpha){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(fontSize));

        for(String line: text.split("\n")){
            int x = gp.ui.getXforCenteredText(line);
            g2.drawString(line,  x, y);
            y += lineHeight;
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
