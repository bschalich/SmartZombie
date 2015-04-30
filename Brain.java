import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Brain here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Brain extends Actor
{
    private MCZombie mc = null;
    private int ySpeed = 0, xSpeed = 0; // the initial vertical and horizontal speeds
    boolean aboutFace; // the direction (left or right) the actor is facing
    private boolean onGround; // the state of the actor being set on an object or not
    /**
     * Act - do whatever the Brain wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {   
        move();
        if (foundMC()) {
            mc.increaseHealth();
            MyWorld world = (MyWorld) getWorld();
            world.removeObject(this);
        }
    }
    
    public boolean foundMC() {
        Actor a;
        if ((a = getOneObjectAtOffset(getImage().getWidth()/2 - 5, 0, MCZombie.class))!=null)
        {
            mc = (MCZombie)a;
            return true;
        }
        // check to left of actor
        if ((a = getOneObjectAtOffset(-getImage().getWidth()/2 + 5, 0, MCZombie.class))!=null) 
        {
            mc = (MCZombie)a;
            return true;
        }
        
        return false;
    }
    
        private void move()
    {
        Actor obj;
        ySpeed++; // adds gravity
        if (xSpeed != 0 && onGround) xSpeed+=aboutFace?10:-10; // add friction
        setLocation(getX()+xSpeed/10, getY()+ySpeed/2);
        
        // check for obstacles
        onGround=false; // initialize value
        // check below the actor  
        while(getOneObjectAtOffset(0, getImage().getHeight()/2+1, Platform.class)!=null)
        {
            setLocation(getX(), getY()-1); 
            onGround=true;
            ySpeed=0;
            
        }
        
        while(getOneObjectAtOffset(0, getImage().getHeight()/2+1, Brain.class)!=null)
        {
            setLocation(getX(), getY()-1); 
            onGround=true;
            ySpeed=0;
            
        }
    }
}
