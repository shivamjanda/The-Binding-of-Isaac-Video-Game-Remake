import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * Write a description of class BossOneHealthBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BossOneHealthBar extends BossOne
{
    int barWidth = 500;
    int barHeight = 15;
    double health = 150;
    double healthPoint = barWidth/health;
    /**
     * Act - do whatever the BossOneHealthBar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public BossOneHealthBar(){
        makeHealthBar();
    }

    public void act() 
    {
        makeHealthBar();
    } 
    // deals bullet damage to health
    public void loseBullet(){
        Hero h = ((Hero) getWorld().getObjects(Hero.class).get(0));
        if (!getObjectsInRange(100, Bomb.class).isEmpty()){
            health = health + h.damage + getObjectsInRange(100, Bomb.class).get(0).damage();
        }
        else{ 
            health = health + h.damage;
        }
    }
    // deals bomb damage to health
    public void bombDamage(){
        BossOne h = ((BossOne) getWorld().getObjects(BossOne.class).get(0)); 
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
