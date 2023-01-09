import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Computer Science Final Project
 * Teacher: Mr.Chan
 * Akathian Santhakumar, Ahmad Shah and Shivam Janda
 * 
 * January 19, 2017
 * 
 * Credits:
 * Greenfoot.org
 * ^
 * rkr profile on Greenfoot.org is Shivams profile (asked questions about code,
 * had to paste part of our own code when asking the question)
 * https://www.mkyong.com/java/java-properties-file-examples/
 */
public class EndingMusic  
{
    static int endingNum;
    static GreenfootSound backgroundMusic;
    static boolean hasChosenEnding = false;
    //plays music according to which ending
    public static void playMusic(int n){ //gets the ending number
        endingNum = n;
        if (!hasChosenEnding){
            backgroundMusic = new GreenfootSound("Ending"+endingNum+".mp3");
            hasChosenEnding = true;
        }
        backgroundMusic.playLoop();// play the intro music
    }
    //stops music
    public static void stopMusic(){
        backgroundMusic.stop();
    }
}

