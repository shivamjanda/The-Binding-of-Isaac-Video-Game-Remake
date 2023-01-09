import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CreditsVideo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CreditsVideo extends Actor
{
    int frame = 1;
    int endingNum = Greenfoot.getRandomNumber (1+1) + 1;
    boolean stop = false;
    String name = "Credits/";
    SimpleTimer timer = new SimpleTimer();
    public CreditsVideo(){
        setImage("Credits/1.gif");
    }

    /**
     * Act - do whatever the video wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {        
        CreditsMusic.playMusic();
        if (EndingMusic.backgroundMusic.isPlaying()){
            EndingMusic.stopMusic();
        }
        if(!stop){
            try{
                if (timer.millisElapsed() >= 6000){ //change the pic every 6 seconds
                    setImage("Credits/"+frame+".gif"); 
                    frame ++;
                    timer.mark();
                }
            }
            catch (java.lang.IllegalArgumentException iae){
                stop = true;
                CreditsMusic.stopMusic();
            }
        }
        getImage().scale(1080, 720);
    }    
}
