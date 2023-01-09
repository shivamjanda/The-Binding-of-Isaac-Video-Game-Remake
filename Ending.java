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
public class Ending extends World
{
    EndingVideo video1; // the ending cutscene
    CreditsVideo video2 = new CreditsVideo(); //credits
    boolean removedVideo = false; //to avoid actor not in world exceptions
    boolean creditsOnly = false; //for menu: if user selects credits options then this is set to true and will only play credits
    /**
     * Constructor for objects of class Ending.
     * 
     */
    public Ending(String s) { //takes in a string when
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1080, 720, 1); 
        video1 = new EndingVideo();
        addObject(video1,getWidth()/2,getHeight()/2);
        Greenfoot.setSpeed(36); //to sync music + video
        creditsOnly = s.equals("Credits"); //if the string is "Credits" then creditsOnly is true
    }

    public void act(){
        if (video1.stop && !removedVideo){ //if the cutscene finishe playing and hasnt been remove yet
            removeObject(video1); //remove the video
            addObject (video2,getWidth()/2,getHeight()/2); //add the creits
            removedVideo = true; //true b/c video1 has been removed
            Greenfoot.setSpeed(50); //set speed back to normal
        }
        if(creditsOnly){ // if only the credits should be played
            addObject (video2,getWidth()/2,getHeight()/2); //add the credits
        }
        if (video2.stop){ // if the credits are done
            removeObject(video2); //remove it, (so that you can watch the credits again
            World a = new IntroScreen(); 
            a.started();
            Greenfoot.setWorld(a); //set the world to the introscreen
        }
    }
}
