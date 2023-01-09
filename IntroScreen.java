import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color; 
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
public class IntroScreen extends Intro
{
    GreenfootSound pageTurnSFX = new GreenfootSound("pageturn.mp3");
    //various timers, explained later
    private SimpleTimer timer = new SimpleTimer();
    private SimpleTimer timer2 = new SimpleTimer();
    private SimpleTimer timer3 = new SimpleTimer();
    boolean hasChangedtoIntro = false; //used for only showing "press start' screen once
    boolean hasChangedtoIntro2 = false; //used for changing to screen with "new run" and back
    boolean hasChangedtoOptions = false;//used for changing to screen with options and back
    boolean hasChangedtoControls = false;//used for changing to screen that shows the controls and back
    boolean started = false;
    /**
     * Constructor for objects of class introScreen.
     * 
     */
    public IntroScreen(){    
        if (GameOverMusic.GameOver.isPlaying()){ //if the gameover music is playing 
            GameOverMusic.stopMusic(); //then stop the gameover music
            Intro.playMusic(); //play the intro music
        }
        prepare();
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }

    //changes greenfoot start implementation so that music starts when the program starts
    public void started(){
        if (!Intro.introSFX.isPlaying()){ //if intro music isnt playing
            Intro.playMusic();
            started = true;
        }
    }

    public void act(){
        nextScreen();
        changeIntro2();
        changeOptions();
        changeControls();
    }

    //changed the screen to intro2 (the one with new run and options)
    public void nextScreen(){
        if ((Greenfoot.isKeyDown("Space") || Greenfoot.isKeyDown("Enter"))  && !hasChangedtoIntro){ //check if user has pressed space or enter to proceed to next screen
            hasChangedtoIntro = true; // so that you don't hear the sound effect every time you hit space
            hasChangedtoIntro2 = true; 
            GreenfootImage intro2 = new GreenfootImage("intro2.jpg"); 
            pageTurnSFX.play(); //play a sound eefect when the screen is changed
            setBackground(intro2); //set the image of the world to the next screen
            addObject(new pointer(), 376,182); //adds a pointer to the screen
            timer2.mark(); //starts a timer (explained later)
        }
    }

    public void changeIntro2(){
        if(hasChangedtoIntro2){
            pointer p = new pointer(); //used to get a reference to the pointer in the world
            if(!getObjects(pointer.class).isEmpty()){
                p = (pointer) getObjects(pointer.class).get(0);  //used to get a reference to the pointer in the world
            }
            if (timer.millisElapsed() > 200){ //so that when you press up/down the pointer doesnt spas out
                if (Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("down")){
                    if(p.getX() == 376){ //if is pointing to 'new run'
                        p.setLocation(417,559); //point it to 'options
                        timer.mark(); //reset the timer
                    }
                    else if (p.getX() == 417){ //if is pointing to 'options'
                        p.setLocation (376,182); //point it to 'new run'
                        timer.mark();//reset the timer
                    }
                }
            }

            // time b/w pressed space at first screen and now (so that you don't auto skip to the gameplay)
            if (timer2.millisElapsed() > 850){
                if (p.getX() == 376 && (Greenfoot.isKeyDown("Space") || Greenfoot.isKeyDown("Enter"))){ //if pointer is pointing to 'new run'
                    introSFX.stop(); //stop the music 
                    Greenfoot.setWorld(new Transition()); //set the world to the transition screen (just for decoration)
                }
                if (p.getX() == 417 && ((Greenfoot.isKeyDown("Space") || Greenfoot.isKeyDown("Enter")))){ //check if user has pressed space or enter to proceed to next screen
                    hasChangedtoOptions = true; // so that you don't hear the sound effect every time you hit space
                    hasChangedtoIntro2 = false; //set to false so that you can come back to this screen
                    GreenfootImage options = new GreenfootImage("Options.jpg"); //set the background to options background
                    pageTurnSFX.play(); //play a sound effect when the screen is changed
                    p.setLocation(436,193); //point to 'controls'
                    setBackground(options); //set the image of the world to the next screen
                    timer2.mark();
                }
            }
        }
    }

    public void changeOptions(){
        if(hasChangedtoOptions){
            pointer p = new pointer(); //same as intro2
            if(!getObjects(pointer.class).isEmpty()){
                p = (pointer) getObjects(pointer.class).get(0); //same as intro2
            }
            if (timer.millisElapsed() > 200){ //so that when you press up/down the pointer doesnt spas out
                if (Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("down")){
                    if(p.getX() == 436){ //if pointing to 'controls'
                        p.setLocation(431,312); //point to 'credits'
                        timer.mark();
                    }
                    else if (p.getX() == 431){ // if pointing to 'credits'
                        p.setLocation (436,193); //point to 'controls'	
                        timer.mark();
                    }
                }
            }

            // time b/w pressed space at intro2 and now (so that you don't auto skip to the controls	
            if (timer2.millisElapsed() > 850){
                if (Greenfoot.isKeyDown("escape") || Greenfoot.isKeyDown("backspace")){ //go back
                    GreenfootImage intro2 = new GreenfootImage("intro2.jpg"); //set image back to intro2
                    pageTurnSFX.play(); //play a sound eefect when the screen is changed
                    setBackground(intro2); //set the image of the world to the next screen
                    p.setLocation(376,182); //adds a pointer to the screen
                    hasChangedtoOptions = false; //so you can come back to options
                    hasChangedtoIntro2 = true;
                    timer2.mark();
                }
                if (p.getX() == 436 && (Greenfoot.isKeyDown("Space") || Greenfoot.isKeyDown("Enter"))){ //pointing to 'controls'
                    GreenfootImage controls = new GreenfootImage("Controls.jpg"); 
                    pageTurnSFX.play(); //play a sound eefect when the screen is changed
                    setBackground(controls); //set the image of the world to the next screen
                    hasChangedtoOptions = false; //see if statement above
                    hasChangedtoControls = true;
                    timer2.mark();
                }
                if (p.getX() == 431 && (Greenfoot.isKeyDown("Space") || Greenfoot.isKeyDown("Enter"))){ //pointing to 'credits'
                    Greenfoot.setWorld(new Ending("Credits"));
                    stopMusic();
                }
            }
        }
    }

    public void changeControls(){ 
        if(hasChangedtoControls){
            pointer p = new pointer(); //same as intro2
            if(!getObjects(pointer.class).isEmpty()){
                p = (pointer) getObjects(pointer.class).get(0); //same as intro2
            }
            removeObject(p);
            if (Greenfoot.isKeyDown("escape") || Greenfoot.isKeyDown("backspace")){ //same as options
                GreenfootImage intro2 = new GreenfootImage("intro2.jpg"); 
                pageTurnSFX.play(); //play a sound eefect when the screen is changed
                setBackground(intro2); //set the image of the world to the next screen
                addObject(new pointer(),376,182);
                hasChangedtoControls = false;
                hasChangedtoIntro2 = true;
                timer2.mark();
            }
        }
    }
}