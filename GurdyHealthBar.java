import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
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
public class GurdyHealthBar extends Gurdy
{
    int barWidth = 500;
    int barHeight = 15;
    double health = 150;
    double healthPoint = barWidth/health; //scales health to bar
    /**
     * Act - do whatever the GrudyHealthBar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public GurdyHealthBar(){
        makeHealthBar();
    }

    public void act() 
    {
        makeHealthBar();
    } 
    // deals bullet damage to health
    public void loseBullet(){
        Hero h = ((Hero) getWorld().getObjects(Hero.class).get(0)); 
        health = health + h.damage;
    }
    // deals bomb damage to health
    public void bombDamage(){
        Gurdy h = ((Gurdy) getWorld().getObjects(Gurdy.class).get(0)); 
        if (h.bombIsClose){
            health = health + getWorld().getObjects(Bomb.class).get(0).damage();
        }
    }
    // returns the current health
    public double healthAmount(){
        return health;
    }
    // creates the actual health bar
    public void makeHealthBar(){
        setImage (new GreenfootImage(barWidth + 2, barHeight + 2)); // creates a image with the dimensions stated above
        GreenfootImage img = getImage();
        img.setColor(Color.BLACK);// sets the color to black
        img.drawRect(0,0,barWidth + 1, barHeight + 1); // draws another rectangle with a smaller dimension
        img.setColor(Color.RED); // sets the color to red
        double change = health * healthPoint;
        int i = (int) change; // converts double to int;
        img.fillRect(1,1,i, barHeight); // changes the length of the health bar once dealt damage
    }

}
