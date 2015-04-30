import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EndObject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EndObject extends Actor
{
    EndScreen world;
    int count = 0;
    public EndObject() {
       setImage("textbox0.png");
    }
    /**
     * Act - do whatever the EndObject wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        world = (EndScreen) getWorld();
        if (world.getCount() > 1700) {
            world.AnimateBG();
            world.lowerVolume();
        }
        if (world.getCount() == 550) 
            setImage("invisRoad.png");
            
        if (world.getCount() == 800)
            setImage("textbox1.png");
            
        if (world.getCount() == 1350)
            setImage("invisRoad.png");
        world.incrementCount();
    }   
}
