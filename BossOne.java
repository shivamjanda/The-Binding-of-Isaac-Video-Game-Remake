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
public class BossOne extends Boss
{
    GreenfootSound shootSFX = new GreenfootSound("enemyshoot.mp3");
    private SimpleTimer startAttackT = new SimpleTimer();
    private SimpleTimer shootT = new SimpleTimer();
    private int speed;
    int jumpTimer = 100;
    boolean rightSide = false;
    boolean leftSide = true;
    boolean startTimer = true;
    boolean shootTimer = true;
    boolean addedHealthBar = true;
    boolean bombIsClose = false;
    /**
     * Act - do whatever the BossOne wants to do. This method is called whenever
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
                jumpTimer--;
                if (jumpTimer == 0){
                    jumpTimer = 100;
                }
                jump(); // jumps every .5 seconds
                checkDeath();
            } 
            if(shootTimer){
                shootT.mark();
                shootTimer = false;
            }
            if(shootT.millisElapsed() > 500){
                shoot(); // shoots every .5 seconds
                shootTimer = true;
            }
            if (addedHealthBar){
                healthBar();
                addedHealthBar = false;
            }
        }
    }

    // deals damage to health andchecks if enemy is dead 
    public void checkDeath() {
        Actor d = getOneIntersectingObject(HeroBullet.class);
        Hero h = ((Hero) getWorld().getObjects(Hero.class).get(0));
        BossOneHealthBar b = ((BossOneHealthBar) getWorld().getObjects(BossOneHealthBar.class).get(0));
        if (d!=null) {
            b.loseBullet();
            getWorld().removeObject(d);
        }
        if (!getObjectsInRange(200, Bomb.class).isEmpty()){
            b.bombDamage();
            bombIsClose = true; //for health bar class
        }
        else {
            bombIsClose = false;
        }
        if (b.healthAmount() <= 0) {
            getWorld().removeObject(this);
        }
    }
    // adds all the bosses bullets
    public void shoot(){
        EnemyBullet a = new EnemyBullet();
        EnemyBullet b = new EnemyBullet();
        EnemyBullet c = new EnemyBullet();
        a.setRotation(90);
        b.setRotation(60);
        c.setRotation(120);
        shootSFX.play();
        getWorld().addObject(a, getX(), getY());
        getWorld().addObject(b, getX(), getY());
        getWorld().addObject(c, getX(), getY());
    }
    // adds the health bar to the world     
    public void healthBar(){
        BossOneHealthBar b = new BossOneHealthBar();
        getWorld().addObject(b, 542, 625);
    }

    // jumps every couple of seconds back and fourth 
    public void jump(){
        int ground = getWorld().getHeight()/2  - getImage().getHeight() / 2;
        boolean isOnGround = (getY() == ground);
        // if the boss is above the ground
        if (!isOnGround && leftSide == true && rightSide == false){
            if (getX() == 900){
                leftSide = false;
                rightSide = true;
            }
            // add gravity  
            speed++;
            // boss falls down and moves to the left
            setLocation (getX() +10, getY() + speed);
            // if the boss lands on the ground
            if (getY()>=ground) {
                // put the boss on the ground
                setLocation(getX(), ground);
            }
        }
        else {
            // if jumptimer equals to one then it adds the speed and leaves the ground
            if (jumpTimer == 1){
                speed = -15;
                setLocation(getX(), getY() + speed);
            }
        }
        // if the player is on the right side then move the opposite
        if (!isOnGround && rightSide == true && leftSide == false){
            if (getX() == 100){
                rightSide = false;
                leftSide = true;
            }
            speed++;
            // boss falls down
            setLocation (getX() -10, getY() + speed);
            // if the boss lands on the ground
            if (getY()>=ground) {
                // put the boss on the ground
                setLocation(getX() - 10, ground);
            }
        }
        else {
            // if jumptimer equals to one then it adds the speed and leaves the ground
            if (jumpTimer == 1){
                speed = -15;
                setLocation(getX() -10, getY() + speed);
            }
        }
    }
}
