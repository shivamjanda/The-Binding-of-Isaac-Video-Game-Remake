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
public class Mulligan extends Enemy
{
    /**
     * Act - do whatever the Mulligan wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    double health = 5;
    public Mulligan(){
        // changes the image size
        getImage().scale(69,69);
    }

    public void act() 
    {
        moveAway();
        checkDeath();
    } 
    // moves away from the player if in range
    public void moveAway(){
        if(getObjectsInRange(200, Obstacle.class).isEmpty()){
            Hero a = ((Hero) getWorld().getObjects(Hero.class).get(0));
            turnTowards(a.getX(), a.getY());
            setRotation(180);
            move(2);
        }
        else{
            if(randomMoveTimer.millisElapsed() <= 2000){
                moveRandomly();
            }
            else {
                randomMoveTimer.mark();
            }
        }
    }
    SimpleTimer timer = new SimpleTimer();
    SimpleTimer randomMoveTimer = new SimpleTimer();
    // enemy moves randomly 
    public void moveRandomly(){
        //countinously moving in the direction its facing at speed 2
        move(2); 
        //greenfoot continously pulls a random number. If the random number is less than 10 turns the grunt a random angle between 45 and -45 
        if (Greenfoot.getRandomNumber(100) < 10){ 
            turn(Greenfoot.getRandomNumber(90 + 1) - 45);
        }       
        //if colliding with any obstacle, turns a complete 180 degrees
        if (timer.millisElapsed() > 90){
            if (isColliding()){ 
                turn(180);
                timer.mark();
            }
        }
    }
    //checks if the grunt is colliding with any object of class Obstacle and returns true if it is.
    public boolean isColliding(){
        Actor d = getOneIntersectingObject(Enemy.class);
        Actor f = getOneIntersectingObject(Obstacle.class);
        if (d!= null || f != null){
            return true;
        }
        return false;
    }

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
        // adds three attack flys and one plooter once dead
        if (health <= 0) {
            AttackFly a = new AttackFly();
            AttackFly b = new AttackFly();
            AttackFly c = new AttackFly();           
            Plooter e = new Plooter();
            getWorld().addObject(a, getX() + 20, getY());
            getWorld().addObject(b, getX() - 20, getY());
            getWorld().addObject(c, getX(), getY() + 20);
            getWorld().addObject(e, getX(), getY() - 20);
            getWorld().removeObject(this);
        }
    }
    // changes health to the new health when damage is dealt
    public void health(double healthLost){
        health = health + healthLost;
    }

}

