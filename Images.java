import java.util.HashMap;
import greenfoot.*; 
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
public class Images  
{
    public static HashMap<String, GreenfootImage> imgs = new HashMap<String, GreenfootImage>();
    //basically, this class was made to reduce heap space errors

    //adds all pictures used in game to a hashmap
    static {
        imgs.put("DoorClosed.jpg", new GreenfootImage("DoorClosed.jpg"));
        imgs.put("ItemDoorClosed.jpg", new GreenfootImage("ItemDoorClosed.jpg"));
        imgs.put("BossDoorClosed.jpg", new GreenfootImage("BossDoorClosed.jpg"));
        imgs.put("ItemDoorOpen.jpg", new GreenfootImage("ItemDoorOpen.jpg"));
        imgs.put("BossDoorOpen.jpg", new GreenfootImage("BossDoorOpen.jpg"));
        imgs.put("fullheart.png", new GreenfootImage ("fullheart.png"));
        imgs.put("emptyheart.png", new GreenfootImage ("emptyheart.png"));
        imgs.put("Nail.png", new GreenfootImage ("Nail.png"));
        imgs.put("hero1.png", new GreenfootImage("hero1.png"));
        imgs.put("hero2.png", new GreenfootImage("hero2.png"));
        imgs.put("hero3.png", new GreenfootImage("hero3.png"));
        imgs.put("hero4.png", new GreenfootImage("hero4.png"));
        imgs.put("hero5.png", new GreenfootImage("hero5.png"));
        imgs.put("hero6.png", new GreenfootImage("hero6.png"));
        imgs.put("hero7.png", new GreenfootImage("hero7.png"));
        imgs.put("hero8.png", new GreenfootImage("hero8.png"));
        imgs.put("hero9.png", new GreenfootImage("hero9.png"));
        imgs.put("heroHurt.png", new GreenfootImage("heroHurt.png"));
        imgs.put("invisHero.png", new GreenfootImage("invisHero.png"));
        imgs.put("heroDead.png", new GreenfootImage("heroDead.png"));
        imgs.put("heroItem.png", new GreenfootImage("heroItem.png"));
        imgs.put("widen.png", new GreenfootImage("widen.png"));
    }
}