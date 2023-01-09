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
public class EndingVideo extends Actor
{
    int frame = 0;
    int endingNum = Greenfoot.getRandomNumber (2) + 1; //random ending number
    String name ="Ending"+endingNum+"/tmp-"; //goes into the file of that ending
    boolean stop = false; 
    //GreenfootSound backgroundMusic = new GreenfootSound("Ending"+endingNum+".mp3");
    /**
     * Act - do whatever the video wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {        
        EndingMusic.playMusic(endingNum); //play music associated to that ending
        if(!stop){
            try{
                setImage(name+frame+".gif"); 
                frame ++;
            }
            catch (java.lang.IllegalArgumentException iae){
                stop = true;
                EndingMusic.stopMusic();
            }
        }
        getImage().scale(1080, 720);
    }    

}
