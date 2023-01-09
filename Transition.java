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
public class Transition extends Intro
{
    GreenfootSound transitionSFX = new GreenfootSound("transition.mp3");
    private SimpleTimer timer = new SimpleTimer();
    /**
     * Constructor for objects of class transition.
     * 
     */
    public Transition()
    {
        transitionSFX.play();
        timer.mark();
    }

    public void act(){
        changeWorld();
    }

    public void changeWorld(){
        if (timer.millisElapsed() > 4000){ //waits for the music to stop playing then sets to the game
            Greenfoot.setWorld(new StartRoom());
        }
    }
}
