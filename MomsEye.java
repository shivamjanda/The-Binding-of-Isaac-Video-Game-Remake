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
public class MomsEye extends PowerUp
{
    public MomsEye(){
        getImage().scale(40,40); // resizes the image
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
