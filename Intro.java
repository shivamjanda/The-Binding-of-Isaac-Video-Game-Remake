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
public class Intro extends World
{
    static  GreenfootSound introSFX = new GreenfootSound("title.mp3");
    /**
     * Constructor for objects of class Intro.
     * 
     */
    public Intro() {    
        super(1080, 720, 1); 
    }
    //used in other classes
    //plays music
    public static void playMusic(){
        introSFX.playLoop();
    }
    //stops music
    public static void stopMusic(){
        introSFX.stop();
    }
}