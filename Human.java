import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Human here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class Human extends Actor
{
    /**
     * Act - do whatever the Human wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int xSpeed = 0;
    private boolean aboutFace;
    
    private GreenfootImage human;
    
    public Human(String image)
    {
        human = new GreenfootImage(image);
        human.drawImage(human, 0, 0);
        setImage(image);
    }
    
    public void act() 
    {
        // Add your action code here.
    }    
    
    
    
    public int findPlatform()
    {
        if (getOneObjectAtOffset(-getImage().getWidth()/2 - 10, 0, DumbZombie.class) == null)
        {
            
            if ((getOneObjectAtOffset(-getImage().getWidth()/2 - 10, 0, Platform.class) != null) || (getOneObjectAtOffset(-getImage().getWidth()/2 - 10, 30, Platform.class) != null) 
                || (getOneObjectAtOffset(-getImage().getWidth()/2 - 10, -20, Platform.class) != null))
            {
                //System.out.println("1");
                return 1;
            }
        }
        
        if (getOneObjectAtOffset(getImage().getWidth()/2 + 10, 0, DumbZombie.class) == null) 
        {
            
            //System.out.println(getOneObjectAtOffset(getImage().getWidth()/2 + 10, 30, Platform.class));
            if ((getOneObjectAtOffset(getImage().getWidth()/2 + 10, 0, Platform.class)!=null) || (getOneObjectAtOffset(getImage().getWidth()/2 + 10, 30, Platform.class) != null)
                 || (getOneObjectAtOffset(getImage().getWidth()/2 + 10, -20, Platform.class) != null)) 
            {
                //System.out.println("2");
                return 2;
            }
                
        }
        
        return 0;
    }
        
        
    
    public void attack()
    {
        //System.out.println();
    }
}
