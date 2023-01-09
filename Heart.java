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
public class Heart extends Actor
{
    GreenfootImage full = Images.imgs.get("fullheart.png");
    GreenfootImage empty = Images.imgs.get("emptyheart.png");

    /**
     * Act - do whatever the FullHeart wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (getImage() == empty){
            getImage().scale(41, 37); //since the actual picture size isnt the same as a full heart
        }
    }    
}
