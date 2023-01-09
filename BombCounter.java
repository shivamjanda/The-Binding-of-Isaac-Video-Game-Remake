import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.awt.Font;

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
public class BombCounter extends Actor
{
    int bombs = 3; //3 is just a placeholder
    //get the amount of bombs the hero has
    public void getProperties(){
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("Hero.properties");
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            bombs = Integer.parseInt(prop.getProperty("Bombs"));
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

    public void act() 
    {
        getProperties();
        // creates the actual text of the bomb counter
        Font font = new Font("Comic sans",Font.BOLD,20);
        GreenfootImage image = new GreenfootImage("Bombs : " + bombs, 20,Color.WHITE, new Color(0,0,0,0));
        image.setFont(font);
        setImage(image);
    }  
}
