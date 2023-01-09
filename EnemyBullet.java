import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
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
public class EnemyBullet extends Bullet
{
    /**
     * Act - do whatever the EnemyBullet wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        //moves it its current direction at speed 10
        move(10);
        remove();
    }   

    //removes bullet if it comes into contact with hero or edge of world
    public void remove(){
        // Actor d = getOneIntersectingObject(Hero.class);
        Actor d = getOneIntersectingObject(Obstacle.class);
        if (d!= null || isAtEdge()) { //if the bullet is touching an enemy or an obstacle
            getWorld().removeObject(this); //removes this specific instance of bullet
        }
    }
}
