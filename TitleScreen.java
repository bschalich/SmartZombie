import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TitleScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TitleScreen extends SWorld
{
    public GreenfootSound backgroundMusic = new GreenfootSound("megaman.mp3");
    public MovingPlatform titleScroller;
    public MCZombie mc;
    public StartButton button;
    /**
     * Constructor for objects of class TitleScreen.
     * 
     */
    public TitleScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 720, 1, 720, 1800);
        Greenfoot.start();
        backgroundMusic.setVolume(30);
        backgroundMusic.playLoop();
        titleScroller = new MovingPlatform("invisRoad.png", 50, 100, true);
        GreenfootImage bg = new GreenfootImage("titlescreen.png");
        setMainActor(titleScroller, 70, 300);
        mainActor.setLocation(50, 2600);
        mc = new MCZombie(true);
        addObject(mc, 650, -150);
        setScrollingBackground(bg);  //270, -130
        button = new StartButton();
        addObject(button, 260, 140);
    }
    
    public void stopSound() {
        backgroundMusic.stop();
    }
    
    public void stopped() {
        removeObject(titleScroller);
        removeObject(mc);
        removeObject(button);
    }
}
