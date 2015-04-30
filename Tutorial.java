import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tutorial here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tutorial extends SWorld
{
    int count = 0;
    TextBox text;
    /**
     * Constructor for objects of class Tutorial.
     * 
     */
    public Tutorial()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 720, 1, 500, 500); 
        GreenfootImage bg = new GreenfootImage("tutorial2.png");
        setMainActor(new Platform("invisRoad.png"), 600, 600);
        setScrollingBackground(bg);
        addObject(new MCZombie(true), 650, 390);
        text = new TextBox("tutorialBox0.png");
        addObject(text, 512, 100);
    }
    
    public void act() {
       if (Greenfoot.isKeyDown("x")) {
           GreenfootSound select = new GreenfootSound("select.wav");
           select.play();
           MyWorld world = new MyWorld();
           Greenfoot.setWorld(world);
           world.createLevel1();
       }
       
       if (count == 550) 
            text.setImage("tutorialBox1.png");
            
       if (count == 1100)
            text.setImage("tutorialBox2.png");
            
       if (count == 1650) {
           text.setImage("pressx.png");
       }
            
       count++;
       
    }
}
