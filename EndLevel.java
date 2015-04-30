import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class EndLevel1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EndLevel extends AreaSensor
{
    MyWorld world;
    MCZombie mc;
    
    public EndLevel()
    {
        GreenfootImage image = new GreenfootImage("invisRoad.png");
        //GreenfootImage i = new GreenfootImage("Dark green platform.png");
        //i.scale(50,1000);
        image.scale(50, 1000);
        setImage(image);
    }
    public void act() 
    {
        if(isTouching(MCZombie.class))
        {
            GreenfootImage bg;
            List<MCZombie> mcs;
            world = (MyWorld) getWorld();
            world.stopSound();
            mcs = world.getObjects(MCZombie.class);
            mc = mcs.get(0);
            
            if(mc.getCurrentLevel() < 2)
            {
                bg = new GreenfootImage("bg City.png");
            }
            else if(mc.getCurrentLevel() < 4)
                bg = new GreenfootImage("bg city3.png");
            else
                bg = new GreenfootImage("bg graveyard.gif");
            mc.incrementLevel();
            
            if(mc.getCurrentLevel() > 6) //end game
            {
                world.stopSound();
                EndScreen es = new EndScreen();
                Greenfoot.setWorld(es);
            }
            else
            {
                world = new MyWorld(mc, bg);
                Greenfoot.setWorld(world);
                
                for(int i = 0; i < 9; i++)
                {
                    if(mc.getCurrentLevel() == i)
                        world.createLevel(i);
                }
            }
        }
    }    
}
