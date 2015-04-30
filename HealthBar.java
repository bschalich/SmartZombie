import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HealthBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealthBar extends Actor
{
    private MCZombie mc;
    
    public HealthBar(MCZombie mainC) {
        mc = mainC;
    }

    public void act() 
    {
        setImage("health" + mc.getHealth() + ".png");
    }   
}
