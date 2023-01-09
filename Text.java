import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Font;
import java.awt.Color;
import java.io.InputStream;
import java.awt.FontMetrics;

import java.awt.Graphics2D;
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
public class Text extends ItemDescription
{
    String text;
    int width;
    String textType;	
    public Text(String str, String type){ //takes in the actual text and what type of text (description/name) and sets the location and size accordingly later
        text = str;
        textType = type;
    }

    /**
     * Act - do whatever the tesst wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        drawText();
    }    

    //gets a custom font (downloaded) and draw the text
    //(greenfoot forums helped)
    public void drawText(){    
        Font font = new Font("q",0,0); //new font
        InputStream is = Text.class.getResourceAsStream("upheavtt.ttf"); //get the new font file
        Font uniFont = null;
        int positionAdjust = 0;
        try {
            uniFont=Font.createFont(Font.TRUETYPE_FONT,is);
        }
        catch(java.awt.FontFormatException r){
            System.err.println("FontFormatException: " + r.getMessage()); 
        }
        catch(java.io.IOException r){
            System.err.println("FontFormatException: " + r.getMessage());
        }

        //draw the actual text
        Font dvs = new Font("Upheaval TT (BRK)", Font.PLAIN, 20);
        GreenfootImage image = new GreenfootImage (1080, 400);
        if (textType.equals("name")){
            dvs = uniFont.deriveFont(50F);//desired font size
            positionAdjust = 2; //used for positioning
        }
        else if (textType.equals("description")){
            dvs = uniFont.deriveFont(25F);//desired font size
            positionAdjust = 1;	 
        }
        image.setColor(java.awt.Color.white);  
        image.setFont(dvs);
        Graphics2D g = image.getAwtImage().createGraphics();
        FontMetrics fm = g.getFontMetrics();
        width = fm.charsWidth(text.toCharArray(), 0, text.length());
        image.drawString(text, getWorld().getWidth()/2 - positionAdjust*width , 200);
        int i = fm.stringWidth(text);
        setImage(image);
    }
}

