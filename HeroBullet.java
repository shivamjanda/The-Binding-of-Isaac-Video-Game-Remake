import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Properties;
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
public class HeroBullet extends Bullet
{
    private double damage = -1;
    private boolean notAlreadySet = true, notAlreadySet2 = true;
    String key = "";
    int whichDirection = 0;
    double accelR = 2.5, accelL = -2.5, accelU = -2.5, accelD = 2.5;
    int rightValue = 0;
    int leftValue = 0;
    int upValue = 0;
    int downValue = 0;
    int timer = 2;

    int x1 = 0;
    int y1 = 0;
    double distanceTravelled = 0;
    double range;
    boolean initialCoordObtained = false;
    GifImage gifImage = new GifImage("tearCroppedOnce.gif"); //bullet destroy animation
    //gets the range of bullet from properties file
    public void getProperties(){
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("Hero.properties");
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            range = Double.parseDouble(prop.getProperty("Range"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Act - do whatever the HeroBullet wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        timer--;
        setDamage();
        getExactKey();
        setBoolean();
        if (!checkRemove()){
            curve();
            move(10);
        }
        remove();
    }
    // remove if the bullet hits an ostacles
    // remove if the bullet is at the edge of the world
    SimpleTimer removeTimer = new SimpleTimer();
    boolean hasMarked = false;

    //returns true if bullet has reached its max range or if intersects with obstacles
    public boolean checkRemove(){
        if ((Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("down") || Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("left")) && !initialCoordObtained){ 
            x1 = getX();
            y1 = getY();
            getProperties();
            initialCoordObtained = true;
        }

        int x2 = getX();
        int y2 = getY();
        distanceTravelled = Math.sqrt(Math.pow((x2 - x1), 2) +  Math.pow((y2 - y1), 2)); //distance formula for checking range
        Actor f = getOneIntersectingObject(Obstacle.class);
        if (getWorld().getObjects(Hero.class).get(0).hasPickedUp_PuplaDuplex){ //if tears are transcendant (pupla duplex powerup), no cllision with rocks
            if (atWorldEdge() || distanceTravelled >= range) {
                return true;
            }
        }
        else{
            if (f != null || atWorldEdge() || distanceTravelled >= range) {
                return true;
            }
        }
        return false;
    }

    //plays gif once tear is removed
    public void remove(){
        if (checkRemove()){
            setImage(gifImage.getCurrentImage());
            if (!hasMarked){
                removeTimer.mark();
                hasMarked = true;
            }
            if (removeTimer.millisElapsed() >= 280){
                getWorld().removeObject(this); //removes this specific instance of bullet
            }
        }
    }

    //checks if bullet is at the world edge
    public boolean atWorldEdge(){
        if(getX() < 10 || getX() > getWorld().getWidth() - 10)
            return true;
        if(getY() < 10 || getY() > getWorld().getHeight() - 10)
            return true;
        else
            return false;
    }

    //sets the damage according to the damge set in hero
    public void setDamage(){
        damage = getWorld().getObjects(Hero.class).get(0).damage;
    }
    
    //creates artificial 'gravity' to make bullets curve, in the direction the hero is moving
    public void curve() {
        Hero h = ((Hero) getWorld().getObjects(Hero.class).get(0));
        if (whichDirection == 1 && !(key.equals("left"))) {
            setLocation(getX()+rightValue, getY());
            rightValue+=accelR;
            accelR-=0.5;
            if (accelR < 0) {
                accelR = 0;
            }
        } else if (whichDirection == 2 && !(key.equals("right"))) {
            setLocation(getX()-leftValue, getY());
            leftValue-=accelL;
            accelL+=0.5;
            if (accelL > 0) {
                accelL = 0;
            }
        } else if (whichDirection == 3 && !(key.equals("down"))) {
            setLocation(getX(), getY()-upValue);
            upValue-=accelU;
            accelU+=0.5;
            if (accelU > 0) {
                accelU = 0;
            } 
        } else if (whichDirection == 4 && !(key.equals("up"))) {
            setLocation(getX(), getY()+downValue);
            downValue+=accelD;
            accelD-=0.5;
            if (accelD < 0) {
                accelD = 0;
            } 
        } else {

        }
    }

    /*
     *following are helper methods for the bullet curve, basically gets information from the hero class
     */
    public void setBoolean() {
        Hero h = ((Hero) getWorld().getObjects(Hero.class).get(0));
        if (notAlreadySet) {
            if (h.movingDirection(1)) {
                whichDirection = 1;
            } else if (h.movingDirection(2)) {
                whichDirection = 2;
            } else if (h.movingDirection(3)){
                whichDirection = 3;
            } else if (h.movingDirection(4)) {
                whichDirection = 4;
            }
            notAlreadySet = false;
        }
    }

    public void getExactKey() {
        Hero h = ((Hero) getWorld().getObjects(Hero.class).get(0));
        if (notAlreadySet2) {
            key = h.returnV();//System.out.println(key);
            notAlreadySet2 = false;
        }
    }
}
