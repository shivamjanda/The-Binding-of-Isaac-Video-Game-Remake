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
public class WalkingHost extends Enemy
{
    GreenfootSound shootSFX = new GreenfootSound("enemyshoot.mp3");
    private double health = 10;
    private int timer = 300;
    private SimpleTimer startAttackT = new SimpleTimer();
    boolean startTimer = true;
    /**
     * Act - do whatever the WalkingHost wants to do. This method is called whenever
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
                invincible(); // uses invincible method every .5 seconds
            }
        }
    }
    // adds invincibility for couple of seconds
    public void invincible(){
        GreenfootImage img;
        GreenfootImage imgTwo;
        timer--;
        img = new GreenfootImage("host.png");
        imgTwo = new GreenfootImage("hostdown.png");
        if (timer == 299){
            setImage(img); // change image
        }
        if  (timer > 230){
            checkDeath();
        }
        if (timer == 230){
            shoot();
        }
        if (timer == 200){
            setImage(imgTwo); // change image again
        }
        if (timer == 0){
            timer = 300;
        }
    }
     // shoots towards the main character
    public void shoot(){
        int dist = 500;
        Hero h = ((Hero) getWorld().getObjects(Hero.class).get(0));
        EnemyBullet a = new EnemyBullet();
        double hypLength; //length of hypotenuse (hero + this)
        double adjLength; //length of adjacent
        Double d_radAngle; //starting point  //angle in radians
        double radAngleUnbox;//unboxed d_radAngle
        int i_radAngle;//integer value of radAngleUnbox in radians
        Integer degAngle; //integer value of i_radAngle in degrees
        if(!getObjectsInRange(dist, Hero.class).isEmpty()){
            for (Object obj: getObjectsInRange(dist, Hero.class)) { //just in case
                Actor Hero = (Actor) obj;
                hypLength = (double)Math.sqrt(Math.pow((Hero.getX() - getX()), 2) + Math.pow((Hero.getY() - getY()), 2)); //gets the hypotenus
                adjLength = (double)Hero.getX() - getX(); //calculates adjacent
                d_radAngle = Math.acos(adjLength/hypLength) * 180/Math.PI; //starting point   //calculates angle, converts it into degrees
                radAngleUnbox = d_radAngle.doubleValue(); //unbox
                i_radAngle = (int) radAngleUnbox;//cast
                degAngle = Integer.valueOf(i_radAngle);//box
                shootSFX.play(); //play sound effect of shooting
                getWorld().addObject(a, getX(), getY()); //add bullet
                //rotate bullet towards player //the negative angle stuff is unit circle stuff
                if (Hero.getY() <= getY()){
                    a.setRotation(-degAngle); //rotate bullet towards player
                }
                else {
                    a.setRotation(degAngle);
                }
            }
        } 
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