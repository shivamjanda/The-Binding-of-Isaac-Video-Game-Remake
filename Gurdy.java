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
public class Gurdy extends Boss
{
    GreenfootSound shootSFX = new GreenfootSound("enemyshoot.mp3");
    private SimpleTimer startAttackT = new SimpleTimer();
    private SimpleTimer spawnTimerTwo = new SimpleTimer();
    //booleans to do things only once
    boolean startTimer = true; 
    boolean tenti = true;
    boolean added = true;
    boolean bombIsClose = false;
    /**
     * Act - do whatever the Gurdy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Gurdy(){

    }

    public void act() 
    {
        if(getWorld().getObjects(Hero.class).get(0) != null){
            if (tenti){
                tenticles();
                tenti = false;
            }
            if(startTimer){
                startAttackT.mark();
                startTimer = false;
            }
            if(startAttackT.millisElapsed() > 4000){
                shoot(); // shoots ever 4 seconds
                startTimer = true;
            }
            if (added){
                healthBar();
                added = false;
            }
            checkDeath();
        }
    }    
    // deals damage to health, checks if enemy is dead 
    public void checkDeath() {
        Actor d = getOneIntersectingObject(HeroBullet.class);
        Hero h = ((Hero) getWorld().getObjects(Hero.class).get(0));
        GurdyHealthBar g = ((GurdyHealthBar) getWorld().getObjects(GurdyHealthBar.class).get(0));
        // code for health decrease
        if (d!=null) {
            g.loseBullet();
            getWorld().removeObject(d);
        }

        if (!getObjectsInRange(200, Bomb.class).isEmpty()){
            g.bombDamage();
            bombIsClose = true; //for health bar class
        }
        else {
            bombIsClose = false;
        }

        if (g.healthAmount() <= 0) {
            getWorld().removeObject(this);
        }

    }
    // spawns a attack fly
    public void spawnFly(){
        AttackFly a = new AttackFly();
        getWorld().addObject(a, getX(), getY());
    }
    // adds two tenticles
    public void tenticles(){
        Tenticles a = new Tenticles();
        Tenticles b = new Tenticles();
        getWorld().addObject(a, 150, 550);
        getWorld().addObject(b, 930, 550);
        a.getImage().scale(50,50);
        b.getImage().scale(50,50);
    }
    // adds the bosses health bar
    public void healthBar(){
        GurdyHealthBar h = new GurdyHealthBar();
        getWorld().addObject(h, 542, 625);
    }
    // adds all the bullets of the boss 
    public void shoot(){
        EnemyBullet a = new EnemyBullet();
        EnemyBullet b = new EnemyBullet();
        EnemyBullet c = new EnemyBullet();
        EnemyBullet d = new EnemyBullet();
        EnemyBullet e = new EnemyBullet();
        EnemyBullet f = new EnemyBullet();
        EnemyBullet g = new EnemyBullet();
        EnemyBullet h = new EnemyBullet();
        EnemyBullet i = new EnemyBullet();
        EnemyBullet j = new EnemyBullet();
        EnemyBullet k = new EnemyBullet();
        EnemyBullet l = new EnemyBullet();
        a.setRotation(90);
        b.setRotation(120);
        c.setRotation(150);
        d.setRotation(180);
        e.setRotation(210);
        f.setRotation(240);
        g.setRotation(270);
        h.setRotation(300);
        i.setRotation(330);
        j.setRotation(360);
        k.setRotation(390);
        l.setRotation(420);
        shootSFX.play();
        getWorld().addObject(a, getX(), getY());
        getWorld().addObject(b, getX(), getY());
        getWorld().addObject(c, getX(), getY());
        getWorld().addObject(d, getX(), getY());
        getWorld().addObject(e, getX(), getY());
        getWorld().addObject(f, getX(), getY());
        getWorld().addObject(g, getX(), getY());    
        getWorld().addObject(h, getX(), getY());    
        getWorld().addObject(i, getX(), getY());    
        getWorld().addObject(j, getX(), getY());    
        getWorld().addObject(k, getX(), getY());    
        getWorld().addObject(l, getX(), getY());    
    }
}
