package Main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound 
{
    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public static final int BACKGROUND_MUSIC = 0;
    public static final int COIN_SOUND = 1;
    public static final int POWERUP_SOUND = 2;
    public static final int UNLOCK_SOUND = 3;
    public static final int FANFARE_SOUND = 4;
    public static final int HIT_MONSTER = 5;
    public static final int RECEIVE_DAMAGE = 6;
    public static final int SWING_WEAPON = 7;


    public Sound()
   {
      //game sound
      soundURL[0] = getClass().getResource("/res/sounds/BlueBoyAdventure.wav");
      soundURL[1] = getClass().getResource("/res/sounds/coin.wav");
      soundURL[2] = getClass().getResource("/res/sounds/powerup.wav");
      soundURL[3] = getClass().getResource("/res/sounds/unlock.wav");
      soundURL[4] = getClass().getResource("/res/sounds/fanfare.wav");

      //combat sound
      soundURL[5] = getClass().getResource("/res/sounds/hitmonster.wav");
      soundURL[6] = getClass().getResource("/res/sounds/receivedamage.wav");
      soundURL[7] = getClass().getResource("/res/sounds/swingweapon.wav");
      soundURL[8] = getClass().getResource("/res/sounds/levelup.wav");
      soundURL[9] = getClass().getResource("/res/sounds/cursor.wav");
      soundURL[10] = getClass().getResource("/res/sounds/burning.wav");
      soundURL[11] = getClass().getResource("/res/sounds/cuttree.wav");
      soundURL[12] = getClass().getResource("/res/sounds/gameover.wav");
      soundURL[13] = getClass().getResource("/res/sounds/stairs.wav");
      soundURL[14] = getClass().getResource("/res/sounds/sleep.wav");
      soundURL[15] = getClass().getResource("/res/sounds/blocked.wav");
      soundURL[16] = getClass().getResource("/res/sounds/parry.wav");
      soundURL[17] = getClass().getResource("/res/sounds/speak.wav");
      soundURL[18] = getClass().getResource("/res/soundsMechant.wav");
      soundURL[19] = getClass().getResource("/res/sounds/Dungeon.wav");
      soundURL[20] = getClass().getResource("/res/sounds/chipwall.wav");
      soundURL[21] = getClass().getResource("/res/sounds/dooropen.wav");
      soundURL[22] = getClass().getResource("/res/sounds/FinalBattle.wav");
    }

    public void setFile(int i) 
   {
      try
       {
          if (soundURL[i] == null) {
            System.out.println("Warning: Sound file at index " + i + " not found");
            return;
           }
           AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
           clip = AudioSystem.getClip();
           clip.open(ais);
           fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
           checkVolume();
        }
        catch(Exception e)
        {
           System.out.println("Error loading sound file: " + e.getMessage());
        }
    }
    public void play()
    {
        clip.start();
    }
    public void loop()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop()
    {
        clip.stop();
    }
    public void checkVolume(){
        switch(volumeScale){
            case 0:volume =-80f; break;
            case 1:volume =-20f; break;
            case 2:volume =-12f; break;
            case 3:volume =-5f; break;
            case 4:volume = 1f; break;
            case 5:volume = 6f; break;
        }
        fc.setValue(volume);
    }
}
