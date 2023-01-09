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
public class Tenticles extends Enemy
{
    private SimpleTimer spawnTimer = new SimpleTimer();
    boolean spawnT = true;
    double health = 20;
    /**
     * Act - do whatever the Tenticales wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(spawnT){
            spawnTimer.mark();
            spawnT = false;
        }
        if(spawnTimer.millisElapsed() > 5000){
            spawnFly(); // spawn ever 5 seconds
            spawnT = true;
        }
        checkDeath();
    }  
    // adds a attack fly to the world
    public void spawnFly(){
        AttackFly a = new AttackFly();
        getWorld().addObject(a, getX(), getY());
    }
    // deals damage to health, checks if enemy is dead and deals knockback
    public void checkDeath() {
        Actor d = getOneIntersectingObject(HeroBullet.class);
        Actor f = getOneIntersectingObject(FlyFriend.class); //a powerup
        Hero h = ((Hero) getWorld().getObjects(Hero.class).get(0));
        // code for knock back and health decrease
        if (d!=null) {
            health(h.damage);
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
