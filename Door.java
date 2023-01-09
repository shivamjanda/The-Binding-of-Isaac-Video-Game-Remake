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
public class Door extends Doors
{
    boolean isOpen = false;
    GreenfootSound openDoorSFX = new GreenfootSound("dooropen.mp3");
    GreenfootImage itemDoorClosed = (Images.imgs.get("ItemDoorClosed.jpg"));
    GreenfootImage bossDoorClosed = (Images.imgs.get("BossDoorClosed.jpg"));
    /**
     * Act - do whatever the Door wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        checkOpen();
    }

    //change the image to an open door image if the door is open
    public void checkOpen() {
        if (!isOpen && getWorld().getObjects(Enemy.class).isEmpty()) {
            if (getImage() == itemDoorClosed){
                setImage((Images.imgs.get("ItemDoorOpen.jpg")));
            }
            else if (getImage() == bossDoorClosed){
                setImage((Images.imgs.get("BossDoorOpen.jpg")));
            }
            else{
                setImage(new GreenfootImage("DoorOpen.jpg"));
            }
            openDoorSFX.play();
            isOpen = true;
        }     
    } 
}
