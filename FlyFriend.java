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
public class FlyFriend extends Actor
{
    public double damage = -0.15;
    /**
     * Act - do whatever the FlyFriend wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
       moveAround();
    }   
    // moves around in a circle around the hero
    public void moveAround(){
        Hero h = ((Hero) getWorld().getObjects(Hero.class).get(0));
        move(3);
        turnTowards(h.getX(),h.getY());
        setLocation(h.getX(),h.getY());
        turn(180);
        move(80);
        turn(90);
    }
   
}
