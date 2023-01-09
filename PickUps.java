import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Random;
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
public class PickUps extends PowerUp
{
    boolean droppedPickUp = false; //boolean to check if pickup has been dropped
    /**
     * Act - do whatever the PickUps wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (!droppedPickUp && getWorld().getObjects(Enemy.class).isEmpty()) { //if a pickup hasn't been dropped yet and all the enemies are dead
            getWorld().addObject(randomPickUp(), getX(), getY());
            droppedPickUp = true;
        }
    }    

    private Actor randomPickUp(){
        ArrayList<Actor> pickUps = new ArrayList<Actor>();
        //the possible powerups
        Actor RedHeart = new RedHeart();
        //add the powerups to the arraylist
        pickUps.add(RedHeart);
        //choose a random powerup and return it
        Random r = new Random();
        return pickUps.get((r.nextInt(pickUps.size())));
    }

}
