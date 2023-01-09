import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Random;

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
public class Room extends Main
{
    Hero hero = new Hero();
    BombCounter counter = new BombCounter();
    int x = 0, y  = 0;
    boolean left = false, right = false, up = false, down = false;

    Door DoorRight;
    Door DoorLeft;
    Door DoorUp;
    Door DoorDown;

    Room map[][];
    int v = 0 , w = 0, changeTime = 30;
    private SimpleTimer timer = new SimpleTimer();
    boolean isStartRoom = false, isBossRoom = false, isItemRoom = false;
    int doorType = 0;
    StartRoom s = new StartRoom();
    boolean checkItem = true;
    boolean checkBoss = true;
    boolean updateHearts = true; //to update hearts everyroom
    /**
     * Constructor for objects of class Room.
     * 
     */
    public Room(boolean north, boolean south, boolean west, boolean east, int i, int j, boolean start, boolean boss, boolean item, int door){    
        isStartRoom = start;
        isBossRoom = boss;
        isItemRoom = item;
        doorType = door;
        prepare();      
        left = west;
        right = east;
        up = north;
        down = south;
        map = s.returnMap();
        makeDoor(left, right, up, down, i, j);  
        v = i;
        w = j;
        // set the background to the startroom background if the room is the startroom
        if (isStartRoom){
            setBackground("BackgroundStart.jpg");
        }

    }

    public void act() {
        getProperties();
        checkChangeWorld(v, w);
        makeItemRoomDoor(left, right, up, down, v, w);
        makeBossRoomDoor(v, w);
        nextLevel();
        if(isBossRoom){ //if boss room then plays boss room music and stops the normal background music
            BackgroundMusic.stopMusic();
            BossMusic.playMusic();
            if(getObjects(Boss.class).isEmpty()){ //stop the boss music when its dead
                BackgroundMusic.playMusic();
                BossMusic.stopMusic();
            }
        }
        else{
            BackgroundMusic.playMusic();
            BossMusic.stopMusic();
        }
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        addObject(hero,getWidth()/2,getHeight()/2);
        setLayout();
        //add the walls
        addObject(new VerticalWall(),969,357);
        addObject(new VerticalWall(),112,362);
        addObject(new HorizontalWall(),538,619);
        addObject(new HorizontalWall(),540,101);
        addObject(new Doors(), 0, 0);
        addObject(counter, 100, 50);
    }

    // returns the counter
    public BombCounter getCounter(){
        return counter;
    }

    //gets the properties of the hero from properties file
    public void getProperties(){
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("Hero.properties");
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            hero.playMusic = Boolean.parseBoolean(prop.getProperty("Music"));
            hero.health = Double.parseDouble(prop.getProperty("Health"));
            hero.maxHealth = Double.parseDouble(prop.getProperty("MaxHealth"));
            hero.damage = Double.parseDouble(prop.getProperty("Damage"));
            hero.shootSpeed = Double.parseDouble(prop.getProperty("ShootSpeed"));
            hero.range = Double.parseDouble(prop.getProperty("Range"));
            hero.hasPickedUp_EightInchNails = Boolean.parseBoolean(prop.getProperty("EightInchNails"));
            hero.hasPickedUp_TwentyTwenty = Boolean.parseBoolean(prop.getProperty("20/20"));
            hero.hasPickedUp_Snack = Boolean.parseBoolean(prop.getProperty("Snack"));
            hero.hasPickedUp_Leo = Boolean.parseBoolean(prop.getProperty("Leo"));
            hero.hasPickedUp_PuplaDuplex = Boolean.parseBoolean(prop.getProperty("PuplaDuplex"));
            hero.hasPickedUp_Polyphemus = Boolean.parseBoolean(prop.getProperty("Polyphemus"));
            hero.hasPickedUp_SoyMilk = Boolean.parseBoolean(prop.getProperty("SoyMilk"));
            hero.hasPickedUp_TheInnerEye = Boolean.parseBoolean(prop.getProperty("TheInnerEye"));	
            hero.hasPickedUp_CatONineTails = Boolean.parseBoolean(prop.getProperty("CatONineTails"));
            hero.hasPickedUp_ForeverAlone = Boolean.parseBoolean(prop.getProperty("ForeverAlone"));
            hero.hasPickedUp_Binky = Boolean.parseBoolean(prop.getProperty("Binky"));
            hero.hasPickedUp_Capricorn = Boolean.parseBoolean(prop.getProperty("Capricorn"));
            hero.hasPickedUp_MomsEye = Boolean.parseBoolean(prop.getProperty("MomsEye"));
            hero.hasPickedUp_BloodClot = Boolean.parseBoolean(prop.getProperty("BloodClot"));
            hero.numBomb = Integer.parseInt(prop.getProperty("Bombs"));
            hero.hasBeenHit = Boolean.parseBoolean(prop.getProperty("HasBeenHit"));
            updateHearts = Boolean.parseBoolean(prop.getProperty("updateHearts"));
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
    //gets booleans from the constructor in the startRoom, and makes doors where needed
    public void makeDoor(boolean left, boolean right, boolean up, boolean down, int i, int j) {
        if (left) { // if there is a connection to the left
            //make door on left
            DoorLeft = new Door();
            addObject(DoorLeft, 60, 360);
        }
        if (right) {
            DoorRight = new Door();
            DoorRight.setRotation(180);
            addObject(DoorRight, 1020, 360);
        }
        if (up) {
            DoorUp = new Door();
            DoorUp.setRotation(90); 
            DoorUp.getImage().scale(105, 128);
            addObject(DoorUp, 541, 53);
        }
        if (down) {
            DoorDown = new Door();
            DoorDown.getImage().scale(105, 128);
            DoorDown.setRotation(270);
            addObject(DoorDown, 541, 667);
        }
    }

    public void makeItemRoomDoor(boolean left, boolean right, boolean up, boolean down, int i, int j){
        if(checkItem){//if it has to checked if there is an item room adjacent to the current room or if the current room is an item room
            if (left){ //if there is a connection to the left
                if(map[i][j-1].isItemRoom || isItemRoom) { //check if the left room is an item room or the current room is an item roomwith a left connection
                    DoorLeft.setImage(Images.imgs.get("ItemDoorClosed.jpg")); //set the image to an item room door
                }
            }
            if (right){
                if (map[i][j+1].isItemRoom || isItemRoom) {
                    DoorRight.setImage(Images.imgs.get("ItemDoorClosed.jpg"));
                }
            }
            if (up){
                if(map[i-1][j].isItemRoom || isItemRoom) {
                    DoorUp.setImage(Images.imgs.get("ItemDoorClosed.jpg"));
                    DoorUp.getImage().scale(105, 128);
                }
            }
            if (down){
                if(map[i+1][j].isItemRoom || isItemRoom) {
                    DoorDown.setImage(Images.imgs.get("ItemDoorClosed.jpg"));
                    DoorDown.getImage().scale(105, 128);
                }
            }
            checkItem = false; //the check has completed, so set to false
        }
    }

    public void makeBossRoomDoor(int i, int j){
        //all code is the same as item room, but for boss now
        if(checkBoss){
            if (left){
                if(map[i][j-1].isBossRoom || isBossRoom) {
                    DoorLeft.setImage(Images.imgs.get("BossDoorClosed.jpg"));//set the image to a boss room door
                }
            }
            if (right){
                if (map[i][j+1].isBossRoom || isBossRoom) {
                    DoorRight.setImage(Images.imgs.get("BossDoorClosed.jpg"));
                }
            }
            if (up){
                if(map[i-1][j].isBossRoom || isBossRoom) {
                    DoorUp.setImage(Images.imgs.get("BossDoorClosed.jpg"));
                    DoorUp.getImage().scale(105, 128);
                }
            }
            if (down){
                if(map[i+1][j].isBossRoom || isBossRoom) {
                    DoorDown.setImage(Images.imgs.get("BossDoorClosed.jpg"));
                    DoorDown.getImage().scale(105, 128);
                }
            }
            checkBoss = false;
        }
    }

    public void checkChangeWorld(int i , int j) {
        //if at the door, change world according to the position of Isaac
        if(getObjects(Doors.class).get(0).isOpen){ // check if at least one door is open (if one door is open then all the other doors must be open since they open in the same conditions)
            if (up && Greenfoot.isKeyDown("w")) { //check if there is a connection up and if hero wants through the door
                if (Math.abs(hero.getX()- DoorUp.getX()) <= 40 && Math.abs(hero.getY() - DoorUp.getY()) <= 90){ //check if hero is close enough to the door
                    Room nextRoom = map[i-1][j]; //set variable next room to the room up
                    nextRoom.hero.setLocation(getWidth()/2 , getHeight() - DoorUp.getImage().getHeight() - hero.getImage().getHeight()/2 - 10); //set the location of the hero in the next room near the bottom door
                    hero.updateHearts = true; //udpate hearts because hero entered new room
                    Greenfoot.setWorld(nextRoom); //set the world to the next room
                } 
            }
            if(down && Greenfoot.isKeyDown("s")){
                if (Math.abs(hero.getX()- DoorDown.getX()) <= 40 && Math.abs(hero.getY() - DoorDown.getY()) <= 90){ //down
                    Room nextRoom = map[i+1][j];
                    nextRoom.hero.setLocation(getWidth()/2 , hero.getImage().getHeight()/2 + DoorDown.getImage().getHeight() + 10);
                    hero.updateHearts = true;
                    Greenfoot.setWorld(nextRoom);
                } 
            }
            if(right && Greenfoot.isKeyDown("d")){
                if (Math.abs(hero.getX()- DoorRight.getX()) <= 90 && Math.abs(hero.getY() - DoorRight.getY()) <= 40){ //right
                    Room nextRoom = map[i][j+1];
                    nextRoom.hero.setLocation(hero.getImage().getWidth()/2 + DoorRight.getImage().getWidth() + 10, getHeight()/2);
                    hero.updateHearts = true;
                    Greenfoot.setWorld(nextRoom);
                } 
            }
            if(left && Greenfoot.isKeyDown("a")){
                if(Math.abs(hero.getX()- DoorLeft.getX()) <= 90 && Math.abs(hero.getY() - DoorLeft.getY()) <= 40){//left
                    Room nextRoom = map[i][j-1];
                    nextRoom.hero.setLocation(getWidth() - DoorLeft.getImage().getWidth() - hero.getImage().getWidth()/2  - 10, getHeight()/2); 
                    hero.updateHearts = true;
                    Greenfoot.setWorld(nextRoom);
                }
            }
        }
    }

    //adds rocks, enemies, items to the world from 2d array
    public void setLayout(){
        ArrayList layouts = new ArrayList();
        Random r = new Random();
        int [][] layout0 = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //dont put objects here
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //or here
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //or here
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 4, 2, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 2, 4, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 3, 0, 0, 2, 0, 0, 3, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            };
        int [][] layout1 =  {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //dont put objects here
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //or here
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //or here
                {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1},
                {1, 0, 2, 4, 2, 0, 0, 0, 0, 0, 2, 4, 2, 0, 1},
                {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1},
                {1, 0, 2, 4, 2, 0, 0, 0, 0, 0, 2, 4, 2, 0, 1},
                {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            };

        int [][] layout2 =  {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //dont put objects here
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //or here
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //or here
                {1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1},
                {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1},
                {1, 0, 0, 0, 2, 0, 0, 3, 0, 0, 2, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1},
                {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1},
                {1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            };

        int [][] layout3 =  {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //dont put objects here
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //or here
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //or here
                {1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //or here
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            };

        // add the possible layouts to the arraylist
        layouts.add(layout0);
        layouts.add(layout1);
        layouts.add(layout2);
        layouts.add(layout3);
        // get a random layout
        int l = r.nextInt(layouts.size());
        int[][] roomLayout = (int[][]) layouts.get(l);
        //only adds rocks/ enemies if the room isnt an item room, boss room or the start room
        if (!isStartRoom && !isBossRoom && !isItemRoom) {
            for (int i = 0; i < roomLayout.length; i++){
                for (int j = 0; j < roomLayout[i].length; j++){
                    if (roomLayout[j][i] == 2){
                        addObject(new rock(),i*78, j*48);
                    }
                    if (roomLayout[j][i] == 3){
                        addObject(randomEnemy(),i*78,j*48);
                    }
                    if (roomLayout[j][i] == 4 && Greenfoot.getRandomNumber(2 + 1) == 0){ //33% chance to drop a pickup
                        addObject(new RedHeart(),i*78,j*48);
                    }
                }
            }
        }
        //if the room is the boss room, then add a boss
        if (isBossRoom) {
            addObject(randomBoss(), getWidth()/2, 300);
            addObject(new Screen(), getWidth()/2, getHeight()/2);
        }
        //if the room is the item room, add a pedestal and add a random item
        if (isItemRoom) {
            addObject(new Pedestal(), getWidth()/2, getHeight()/2);
            addObject(randomPowerUp(), getWidth()/2, getHeight()/2 - (randomPowerUp().getImage().getHeight() / 2));
        }
    }

    //gets a random powerup and returns it
    private Actor randomPowerUp(){
        ArrayList<Actor> powerUps = new ArrayList<Actor>();
        //the possible powerups
        Actor TwentyTwenty = new TwentyTwenty();
        Actor TheInnerEye = new TheInnerEye();
        Actor CatONineTails = new CatONineTails();
        Actor EightInchNails = new EightInchNails();
        Actor Binky = new Binky();
        Actor Polyphemus = new Polyphemus();
        Actor Capricorn = new Capricorn();
        Actor PuplaDuplex = new PuplaDuplex();
        Actor SoyMilk = new SoyMilk();
        Actor Snack = new Snack();
        Actor Leo = new Leo();
        Actor MomsEye = new MomsEye();
        Actor BloodClot = new BloodClot();
        Actor ForeverAlone = new ForeverAlone();

        //add the powerups to the arraylist
        powerUps.add(TwentyTwenty);
        powerUps.add(TheInnerEye);
        powerUps.add(CatONineTails);
        powerUps.add(EightInchNails);
        powerUps.add(Binky);
        powerUps.add(Polyphemus);
        powerUps.add(Capricorn);
        powerUps.add(PuplaDuplex);
        powerUps.add(SoyMilk);
        powerUps.add(Snack);
        powerUps.add(Leo);
        powerUps.add(MomsEye);
        powerUps.add(ForeverAlone);
        powerUps.add(BloodClot);

        //choose a random powerup and return it
        Random r = new Random();
        return powerUps.get((r.nextInt(powerUps.size())));
    }

    private Actor randomEnemy(){
        ArrayList<Actor> enemies = new ArrayList<Actor>();
        //the possible enemies
        Actor Clotty = new Clotty();
        Actor Grunt = new Grunt();
        Actor WalkingHost = new WalkingHost();
        Actor SmartGrunt = new SmartGrunt();
        Actor Mulligan = new Mulligan();
        //add the enemies to the arraylist
        enemies.add(Clotty);
        enemies.add(Grunt);
        enemies.add(WalkingHost);
        enemies.add(SmartGrunt);
        enemies.add(Mulligan);
        //choose a random powerup and return it
        Random r = new Random();
        return enemies.get((r.nextInt(enemies.size())));
    }

    private Actor randomBoss(){
        ArrayList<Actor> boss = new ArrayList<Actor>();
        //the possible bosses
        Actor BossOne = new BossOne();
        Actor Gurdy = new Gurdy();
        //add the enemies to the arraylist
        boss.add(BossOne);
        boss.add(Gurdy);
        //choose a random enemy and return it
        Random r = new Random();
        return boss.get((r.nextInt(boss.size())));
    }

    public void nextLevel(){
        // add a trop door if boss is dead leading to next level
        if (getObjects(Boss.class).isEmpty() && isBossRoom) {
            addObject(new TrapDoor(), getWidth()/2, getHeight()/2); 
        } 
    }
}

