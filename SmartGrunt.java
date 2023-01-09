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
public class SmartGrunt extends Enemy
{
    GreenfootSound shootSFX = new GreenfootSound("enemyshoot.mp3");
    SimpleTimer shootTimer = new SimpleTimer();
    private double health = 10;
    private SimpleTimer startAttackT = new SimpleTimer();
    boolean startTimer = true;
    /**
     * Act - do whatever the SmartGrunt wants to do. This method is called whenever
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
                follow(); // follow, shoot and check death play every .5 seconds
                shoot();
                checkDeath();
            }
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

    private boolean movingVertically = Greenfoot.getRandomNumber(1) == 0;
    public void follow(){
        movingVertically = Greenfoot.getRandomNumber(1) == 0;
        Actor actor = (Actor)getWorld().getObjects(Hero.class).get(0);
        int dx = (int)Math.signum(actor.getX()-getX());
        int dy = (int)Math.signum(actor.getY()-getY());
        // do nothing if no movement
        if (dx == 0 && dy == 0){
            return;
        }
        // check one way and if blocked check other way
        int failCount = 0; // to count failures to move
        while (failCount < 2){
            // if on axis of movement, count as failure and change axis
            if ((movingVertically && dy == 0) || (!movingVertically && dx == 0)) {
                failCount++;
                movingVertically = !movingVertically;
            }
            // determine axial directions to check
            int x = 0, y = 0;
            if (movingVertically){
                y = dy; 
            }
            else {
                x = dx;
            }
            // make move along current axis
            setLocation(getX()+x*2, getY()+y*2);
            // do no more if not blocked by wall
            if (getOneIntersectingObject(Obstacle.class) == null) {
                break;
            }
            // move back, count as failure and change axis
            setLocation(getX()-x*2, getY()-y*2);
            failCount++;
            movingVertically = !movingVertically;
        }

    }
}
