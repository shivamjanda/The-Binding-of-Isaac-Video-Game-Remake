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
public class GameOver extends Actor
{
    public GameOver (){
        getImage().scale(1080,720);
    }

    public void act(){
        if (BackgroundMusic.backgroundMusic.isPlaying()){ //stops background music
            BackgroundMusic.stopMusic();
        }
        if (BossMusic.Boss.isPlaying()){
            BossMusic.stopMusic();
        }
        GameOverMusic.playMusic();
        restart();
        mainMenu();
        getWorld().removeObjects(getWorld().getObjects(Bullet.class));
    }

    //if space is pressd then make new world
    public void restart(){
        if (Greenfoot.isKeyDown("space")){
            Greenfoot.setWorld(new StartRoom());
            GameOverMusic.stopMusic();
        }
    }

    //if excape is pressed then go to menu
    public void mainMenu(){
        if (Greenfoot.isKeyDown("escape")){
            Greenfoot.setWorld(new IntroScreen());
            GameOverMusic.stopMusic();
        }
    }
}
