import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MovingPlatform here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovingPlatform2 extends Platform
{
    private int speed;
    private int maxX;
    private int minX;
    private boolean isVertical;
    private boolean direc = false; //false for right true for left
    private int counter;
    private int distance;

    public MovingPlatform2(String image, int dist, int sp, boolean vertical) 
    {
        super(image);
        isVertical = vertical;
        counter = 0;
        distance = dist;
        speed = sp;
    }
    /**
     * Act - do whatever the MovingPlatform wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (!isVertical)
        {
            setLocation(getX() + speed, getY());
            counter++;
            if(counter % (distance/speed) == 0)
            {
                speed = -speed;
            }
        }
        else
        {
            setLocation(getX(), getY() + speed);
            counter++;
            if(counter % (distance/speed) == 0)
            {
                speed = -speed;
            }
        }
    }
    
    public boolean getVertical()
    {
        return isVertical;
    }
    
    public int getSpeed()
    {
        return speed;
    }
    
}
