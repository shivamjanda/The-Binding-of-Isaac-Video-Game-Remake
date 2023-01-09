import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Leo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Leo extends PowerUp
{
    /**
     * Act - do whatever the Leo wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Leo(){
        getImage().scale(69,60); // resizes the image
    }

    public void act()  {
        remove();
    }
    //removes when picked up by hero
    public void remove(){
        Actor d = getOneIntersectingObject(Hero.class);
        if (d!=null) {
            getWorld().removeObject(this);
        }	
    }   
}
