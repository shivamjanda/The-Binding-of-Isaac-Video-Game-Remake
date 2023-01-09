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
public class Bomb extends Bullet
{
    SimpleTimer bombTime = new SimpleTimer();
    GifImage gifImage = new GifImage("bomb.gif");
    public int damage = -20;
    public void act() 
    {
        animation();
        damage();
        if (bombTime.millisElapsed() >= 1000){
            explode(); 
            getWorld().removeObject(this);
        }
    }
    // creates the explosion animation of the bomb
    public void animation(){
        setImage(gifImage.getCurrentImage());
        getImage().scale(200,200);
    }
    // removes rocks and pedestals that are within the radius of the bomb
    public void explode (){
        getWorld().removeObjects(getObjectsInRange(200, rock.class));
        getWorld().removeObjects(getObjectsInRange(200, Pedestal.class));
    }
    //deals damage if has exploded (1s)
    public int damage(){
        if (bombTime.millisElapsed() >= 1000){
            return damage;
        }
        return 0;
    }
}

