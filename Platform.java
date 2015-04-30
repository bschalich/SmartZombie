import greenfoot.*;
import java.util.*;

/**
 * An object to act as an obstacle.
 */
public class Platform extends Actor
{
    /**
     * Creates the object and sizes its image.
     */
    private GreenfootImage platform;
    
    public Platform(String image)
    {
        if (image != null)
        {
            platform = new GreenfootImage(image);
            setImage(platform);
            getImage().scale(40, 40);
        }
            
    }
    
    public int getWidth()
    {
        return getImage().getWidth();
    }
    
    public int getHeight()
    {
        return getImage().getHeight();
    }
    
    public int Y()
    {
        return getY();
    }
    
    public int X()
    {
        return getX();
    }
    
    
}
