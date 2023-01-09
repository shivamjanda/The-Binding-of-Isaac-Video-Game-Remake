import greenfoot.*;  
/**
 * Write a description of class BossMusic here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BossMusic  
{
    static GreenfootSound Boss = new GreenfootSound("Boss.mp3");
    //plays music
    public static void playMusic(){
        Boss.playLoop();
    }
    //stops music
    public static void stopMusic(){
        Boss.stop();
    }
}
