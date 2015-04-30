import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MovingPlatform here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovingPlatform extends Platform
{
    private int direction;
    private int maxX;
    private int minX;
    private boolean isVertical;

    public MovingPlatform(String image, int Xa, int Xb, boolean vertical) 
    {
        super(image);
        minX = Xa;
        maxX = Xb;
        direction = 1;
        isVertical = vertical;
    }
    /**
     * Act - do whatever the MovingPlatform wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (!isVertical) {
            if (getX() <= minX) direction = 1;
            if (getX() >= maxX) direction = -1;
            setLocation(getX() + direction, getY());
        }
        else {
            if (getY() <= minX) direction = 1;
            if (getY() >= maxX) direction = -1;
            setLocation(getX(), getY() + direction);
        }
    }    
}
