import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Sunset here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sunset extends SWorld
{
    public GreenfootSound backgroundMusic = new GreenfootSound("sunset.mp3");
    private TextBox text;
    private int count = 0;
    /**
     * Constructor for objects of class Sunset.
     * 
     */
    public Sunset()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 720, 1, 720, 1024);
        backgroundMusic.setVolume(30);
        backgroundMusic.playLoop();
        text = new TextBox("textbox2.png");
        addObject(text, 512, 100);
    }
    
    public void act() {
        if (count == 550) 
            removeObject(text);
        if (count == 600) {
            addObject(new TextBox("theend.png"), 300, 170);
        }
        count++;
    }
}
