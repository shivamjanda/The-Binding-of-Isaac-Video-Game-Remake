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
public class Clotty extends Enemy
{
    GreenfootSound shootSFX = new GreenfootSound("enemyshoot.mp3");
    private double health = 10;
    private SimpleTimer startAttackT = new SimpleTimer();
    int timer = 100;
    int moveTimer = 20;
    boolean startTimer = true;
    /**
     * Act - do whatever the Clotty wants to do. This method is called whenever
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
                move(); // moves every .5 seconds
                timer --;
                moveTimer--;
                if (timer == 2){
                    shoot();
                    timer = 100;
                }
                checkDeath();
            }
        } 
    }
    // for every couple of seconds turn 90 degrees
    public void move(){
        move(2);
        if (moveTimer == 2){
            turn (90);
            getImage().rotate(-90);
            moveTimer = 20;
        }
    }
    // shoots all four directions
    public void shoot(){
        EnemyBullet a = new EnemyBullet();
        EnemyBullet b = new EnemyBullet();
        EnemyBullet c = new EnemyBullet();
        EnemyBullet d = new EnemyBullet();
        b.setRotation(90);
        c.setRotation(180);
        d.setRotation(270);
        shootSFX.play();
        getWorld().addObject(a, getX(), getY());
        getWorld().addObject(b, getX(), getY());
        getWorld().addObject(c, getX(), getY());
        getWorld().addObject(d, getX(), getY());
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