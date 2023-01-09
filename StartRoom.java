import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
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
public class StartRoom extends Main
{ 
    BombCounter counter = new BombCounter();
    static int worldSize = 7;
    int level = 1;
    int traceto = 0;
    int[][] arr = new int[worldSize][worldSize];
    public static Room[][] map = new Room[worldSize][worldSize];
    Random r = new Random();
    boolean nextLeft = false, nextRight = false, nextUp = false, nextDown = false, alreadyRun = false;
    boolean left = false, right = false, up = false, down = false;
    boolean isBoss = false, isStart = false, isItem = false;
    int indexBegin = 0;
    int doorType = 0;
    Hero hero = new Hero();
    /*
     *Constructs the world
     */
    public StartRoom()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        if (Level.level == 1){ //only create the properties file if the level is 1 (so that you can carry through your properties throughout levels	
            createProperties();
        }
        prepare();
        Greenfoot.setSpeed(50); //in case speed is 36 due to ending Wolrd
    }

    public static Room[][] returnMap(){ //this is used in the Room class
        return map;
    }

    public int [][] returnArr(){ //used in room class
        return arr;
    }

    public void act() {
        //this sets the world to the the starting point of the map
        //if there is a bug and it chooses wrong point, then picks first non-null cell in 2d array
        if (map[0][roomGenerator()] != null) {
            Greenfoot.setWorld(map[0][roomGenerator()]);
        } else {
            for (int i = 0; i < map[0].length; i++) {
                if (map[0][i] != null) {
                    Greenfoot.setWorld(map[0][i]);  
                }
                break;
            }
        }
        addObject(counter, 100, 50);
    }
    //creates the properties file used throughout the game with default values	
    public void createProperties(){
        Properties prop = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("Hero.properties");
            // set the properties value
            prop.setProperty("Music", "true");
            prop.setProperty("Health", "6");
            prop.setProperty("MaxHealth", "6");
            prop.setProperty("Damage", "-3.5");
            prop.setProperty("ShootSpeed", "600");
            prop.setProperty("Range", "500");
            prop.setProperty("EightInchNails", "false");
            prop.setProperty("SoyMilk", "false");
            prop.setProperty("Polyphemus", "false");
            prop.setProperty("PuplaDuplex", "false");
            prop.setProperty("BloodClot", "false");
            prop.setProperty("20/20", "false");
            prop.setProperty("ForeverAlone", "false");
            prop.setProperty("Leo", "false");
            prop.setProperty("Snack", "false");
            prop.setProperty("TheInnerEye", "false");
            prop.setProperty("CatONineTails", "false");
            prop.setProperty("Binky","false");
            prop.setProperty("Capricorn", "false"); 
            prop.setProperty("MomsEye", "false"); 
            prop.setProperty("Bombs", "5");
            prop.setProperty("HasBeenHit", "false");
            prop.setProperty("UpdateHearts", "true");

            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }
    // returns the counter
    public BombCounter getBombCounter(){
        return counter;
    }
    //fills 2d array with 0s
    public void populate(int[][] a) {
        for (int[] i : a) {
            for (int j = 0; j < i.length; j++) {
                i[j] = 0;
            }
        }
    }
    //picks one random cell at the top of the array and one random cell at the bottom and sets it to 1. These will be the starting and ending points
    public int setEndPoints(int[][] a) {
        int ra = r.nextInt(a[0].length);
        for (int i = 0; i < a[a.length-1].length; i++) {
            if (i == ra) {
                a[a.length-1][i] = 1;
            }
        }
        ra = r.nextInt(a[a.length-1].length);
        for (int i = 0; i < a[0].length; i++) {
            if (i == ra) {
                a[0][i] = 1;
                return ra;
            }
        }
        return ra;
    }
    //checks to see if the adjacent cells of a particular cell are occupied by 0s or 1s. Returns true if any of the adjacent cells is 1, else returns false
    public boolean checkAdjacent(int[][] a, int i, int j) {
        if ((j + 1) < a.length && a[i][j+1] == 1) {
            return true;
        } else if ((j - 1) >= 0 && a[i][j-1] == 1) {
            return true;
        } else if ((i + 1) < a.length && a[i+1][j] == 1) {
            return true;
        } else if ((i - 1) >= 0 && a[i-1][j] == 1) {
            return true;
        } else {
            return false;
        }
    }
    //pathfinding algorithm that connects the start point and the end point RANDOMLY. Not meant to pick shortest route
    public void pathfind(int[][] a) {
        int counter = 0, counter2 = 0;
        int save = 0;
        boolean[][] b = new boolean[a.length][a.length]; 
        int ra = 0, diff = 0;
        int starty = 0, startx = 0, endx = 0;
        boolean moveRight = false; //whether to map should move right or left to be towards goal
        boolean canMoveVert = true; //whether the map can make a room below or not
        int LRow = 0, RRow = 0, DRow = 0;
        while (counter < a.length) { //converts int[][] to room[][] with rooms at cells occupied by 1s
            for (boolean[] i : b) {
                for (int j = 0; j < i.length; j++) {
                    if (a[counter][j] == 1) {
                        i[j] = true;
                        save = j;
                    } else {
                        i[j] = false;
                    }
                }
                counter++; //counter is our i value for the end point
            }
        }

        counter = counter - 1; // counter needs to be subtracted by one to be value for end point

        for (int i = 0; i < a[0].length; i++) {
            if (a[0][i] == 1) {
                startx = i; //index of start point
            }
        }

        for (int i = 0; i < a[a.length-1].length; i++) {
            if (a[a.length-1][i] == 1) {
                endx = i; //index of end point
            }
        }

        diff = (endx-startx); //subtracts the index of the start and end point to check whether the map needs to create rooms to the left or right

        while (!checkAdjacent(a, counter, save)) {
            if (diff > 0) { //if diff is positive, map should move right
                moveRight = true;
            } else if (diff < 0) { //if negative, map should move left
                moveRight = false;
            }

            if (canMoveVert) { //if you can move downwards, pick a number bw 0 and 300
                ra = r.nextInt(300)+1;
            } else {
                ra = r.nextInt(200); //else between 0 - 199, so that it would try and go down when it can't
            }

            if (ra <= 200) { //if ra is below two hunded
                if (ra >= 100 && moveRight && RRow <= 3) { //if move right is true
                    if((startx+1) < a[startx].length) { //if possible to move right
                        if (RRow <= 3) {
                            startx = startx+1; //make a room to the right
                            a[starty][startx] = 1;
                            RRow++;
                            LRow = 0;
                            DRow = 0;
                        }
                    }
                } else if (ra < 100 && !moveRight) { //if move right ISN'T true
                    if (LRow <= 3) {
                        if ((startx-1) >= 0) { //do the same thing with left
                            startx = startx-1;
                            a[starty][startx] = 1;
                            LRow++;
                            RRow = 0;
                            DRow = 0;
                        }
                    }
                } 
            } else if (canMoveVert && ra > 200) { //only move down if you can
                if (DRow <= 2) {
                    if ((starty+1) < a[starty].length) {
                        starty = starty+1; 
                        a[starty][startx] = 1;
                        DRow++;
                        LRow = 0;
                        RRow = 0;
                    }
                }
            }
            if (starty+1 >= a.length) { //if moving down would give null exception
                canMoveVert = false; //set canMoveVert to false
            }
            diff = endx - startx; //rechecks the diff to see if the algorith should move right or left
        }
    }

    //turns room[][] into actual rooms in the world
    public int roomGenerator() {
        int v = 0;
        populate(arr);
        v = setEndPoints(arr);
        pathfind(arr);
        Random rand = new Random();
        int r = rand.nextInt(arr[arr.length-1].length);
        int s = rand.nextInt(arr.length-2)+1;
        boolean itemRoomChecked = false;

        do { 
            r = rand.nextInt(arr[arr.length-1].length);
        } while (!(arr[arr.length-1][r] == 1));

        //places an item room in the world
        for (int i = 0; i < arr[s].length; i++) {
            if (arr[s][i] == 1) {
                if ((i - 1) > 0) {
                    arr[s][i-1] = 2;
                    break;
                } else {
                    if (i + 1 < arr.length-1) {
                        if (arr[s][i+1] != 1) {
                            arr[s][i+1] = 2;
                            break;
                        } else {
                            int counter = i;
                            while (arr[s][counter] != 0) {
                                counter++;
                                if(counter + 1 > arr.length-1) {
                                    counter = 0;
                                    s++;
                                }
                            }
                            arr[s][counter] = 2;
                            break;
                        }
                    }
                }
            }
        }
        //checks to see wheres the doors should be etc, and passes that info to creates a new room based on that info
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == 1) {
                    //checks to see if a room has any doors next to it
                    if ((j + 1) < arr.length && arr[i][j+1] == 1) {
                        nextRight = true;
                    } 

                    if ((j - 1) >= 0 && arr[i][j-1] == 1) {
                        nextLeft = true;
                    }

                    if ((i + 1) < arr.length && arr[i+1][j] == 1) {
                        nextDown = true;
                    } 

                    if ((i - 1) >= 0 && arr[i-1][j] == 1) {
                        nextUp = true;
                    }

                    if (!itemRoomChecked) {
                        if ((j + 1) < arr.length && arr[i][j+1] == 2) {
                            nextRight = true;
                            doorType = 1;
                            itemRoomChecked = true;
                        } else if ((j - 1) >= 0 && arr[i][j-1] == 2) {
                            nextLeft = true;
                            doorType = 2;
                            itemRoomChecked = true;
                        } else if ((i + 1) < arr.length && arr[i+1][j] == 2) {
                            nextDown = true;
                            doorType = 3;
                            itemRoomChecked = true;
                        } else if ((i - 1) >= 0 && arr[i-1][j] == 2) {
                            nextUp = true;
                            doorType = 4;
                            itemRoomChecked = true;
                        }
                    }

                    if (i == 0 && j == v) {
                        isStart = true;
                    } else {
                        isStart = false;
                    }

                    if (i == (arr.length - 1) && j == r) {
                        isBoss = true;
                    } else {
                        isBoss = false;
                    }

                    //if yes, passes booleans of where to make doors to the room class
                    map[i][j] = new Room(nextUp, nextDown, nextLeft, nextRight, i, j, isStart, isBoss, isItem, doorType);
                    nextRight = false;
                    nextLeft = false;
                    nextDown = false;
                    nextUp = false;
                } else if (arr[i][j] == 2) {
                    if (doorType == 0) {
                        nextRight = true; 
                    }

                    if (doorType == 1) {
                        nextLeft = true;
                    } 

                    if (doorType == 2) {
                        nextRight = true;
                    } 

                    if (doorType == 3) {
                        nextUp = true;
                    } 

                    if (doorType == 4) {
                        nextDown = true;
                        System.out.println("in up");
                    }
                    map[i][j] = new Room(nextUp, nextDown, nextLeft, nextRight, i, j, false, false, true, 0);
                    nextRight = false;
                    nextLeft = false;
                    nextDown = false;
                    nextUp = false;
                    doorType = 0;
                }
            }
        }
        return v;
    }
}
