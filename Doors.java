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
public class Doors extends Obstacle
{
    boolean isOpen = false;
    GreenfootSound openDoorSFX = new GreenfootSound("dooropen.mp3");
    /**
     * Act - do whatever the ItemDoor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        checkOpen();
    }

    //opens doors when enemies are dead
    public void checkOpen() {
        if (!isOpen && getWorld().getObjects(Enemy.class).isEmpty()) {
            openDoorSFX.play();
            isOpen = true;
        }     
    } 
}
