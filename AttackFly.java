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
public class AttackFly extends Enemy
{
    /**
     * Act - do whatever the miniEnemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public double health = 5;
    GifImage gifImage = new GifImage("AttackFly.gif");
    public void act()
    {
        animation();
        followPlayer();
        checkDeath();
    }  
    // sets the enemy as a gif image
    public void animation(){
        setImage(gifImage.getCurrentImage());
    }
    //follows the player 
    public void followPlayer(){
        Hero h = getWorld().getObjects(Hero.class).get(0);
        turnTowards(h.getX(), h.getY());
        move(2);
    }
    // deals damage to health, checks if enemy is dead and deals knockback
    public void checkDeath() {
        Actor d = getOneIntersectingObject(HeroBullet.class);
        Actor f = getOneIntersectingObject(FlyFriend.class); //a powerup
        Hero h = ((Hero) getWorld().getObjects(Hero.class).get(0));
        // code for knock back and health decrease
        if (d!=null) {
            health(h.damage);
            int rotation = getRotation();
            setRotation(d.getRotation());
            if (getObjectsInRange(120, Obstacle.class).isEmpty()){ //if it isnt close to an obstacle (so that in doesnt go in it
                move(30);
            }
            setRotation(rotation);
            getWorld().removeObject(d);
        }
        //if flyfried powerup is hitting it
        if (f!=null){
            FlyFriend a = ((FlyFriend) getWorld().getObjects(FlyFriend.class).get(0));
            health(a.damage);
        }
        //if withing a bomb's range
        if (!getObjectsInRange(100, Bomb.class).isEmpty()){
            health(getObjectsInRange(100, Bomb.class).get(0).damage());
        }
        //removes if health is 0
        if (health <= 0) {
            getWorld().removeObject(this);
        }
    }
    // changes health to the new health when damage is dealt
    public void health(double healthLost){
        health = health + healthLost;
    }
}

