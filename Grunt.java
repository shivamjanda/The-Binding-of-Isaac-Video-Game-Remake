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
public class Grunt extends Enemy
{
    SimpleTimer timer = new SimpleTimer();
    double turntime = 50;
    private double health = 10;
    int moveBack = 0;
    GreenfootSound shootSFX = new GreenfootSound("enemyshoot.mp3");
    private SimpleTimer startAttackT = new SimpleTimer();
    boolean startTimer = true;
    /**
     * Act - do whatever the Grunt wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {         
        if(getWorld().getObjects(Hero.class).get(0) != null){
            if(startTimer){
                startAttackT.mark();
                startTimer = false;
            }
            if(startAttackT.millisElapsed() > 500){
                if (!isCollidingWithHero()){
                    moveRandomly();
                }
                checkDeath();
            }    
        }
    }
    //moves the grunt randomly
    public void moveRandomly(){
        //countinously moving in the direction its facing at speed 5
        move(5); 
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
    //check if grunt is colliding with hero
    public boolean isCollidingWithHero(){
        Actor d = getOneIntersectingObject(Hero.class);
        return d != null;
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
            if (getObjectsInRange(150, Obstacle.class).isEmpty()){ //if it isnt close to an obstacle (so that in doesnt go in it
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
