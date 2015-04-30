import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ground here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ground extends Platform
{
    
    public Ground(String s)
    {
        super(s);
        GreenfootImage road = new GreenfootImage("invisRoad.png");
        GreenfootImage image = new GreenfootImage(4000, road.getHeight());
        int w=road.getWidth();
        for(int offset=0; offset<4000; offset+=w) image.drawImage(road, offset, 0);
        setImage(image);
    }
}
