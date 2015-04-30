import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Point;
import java.util.*;
/**
 * Write a description of class Bullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

 
 
public class Bullet extends Actor
{
    /**
     * Act - do whatever the Bullet wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    private boolean isActive = false;
    private boolean direction;
    private MCZombie z = null;
    private int xDistance;
    private int yDistance;
    private int speed;
    private final int bulletSpeed = 1; 
    private boolean gotVector = false;
    private Random rand = new Random();
    private int count = rand.nextInt();
    private RangedAttacker rAtt;
    private World myWorld;
    private int myX, myY;
    private int rAttX, rAttY;
    
    public Bullet()
    {
        
    }
    
    public void act() 
    {
        if(isActive)
        {
            getMC();
            
            setLocation(getX() + xDistance / 3, getY() + yDistance / 3);
            
            if (z != null && Math.abs(z.getX() - getX()) < ((z.getImage().getWidth() / 2) - 20) && Math.abs(z.getY() - getY()) < ((z.getImage().getHeight() / 2) - 20))
            {
                z.decrementHealthFire();
                myWorld.removeObject(this);
            }
            
            else if (rAtt != null)
            {
                rAtt.setShot(false);
                
                if (this != null && rAtt != null && (Math.sqrt((rAttX - getX()) * (rAttX - getX()) + (rAttY - getY()) * (rAttY - getY()))  > 700) 
                    || isTouching(Platform.class))
                {
                    myWorld.removeObject(this);
                }
                
                
            }
            
            else
            {
                myWorld.removeObject(this);
            }
        }
        
         
        // Add your action code here.
    }    
    
    private void getBulletVector()
    {
        yDistance = z.getY() - myY;
        xDistance = z.getX() - myX;
        
        if (xDistance == 0)
            xDistance = 1;
        if (yDistance == 0)
            yDistance = 1;
            
        int a = (int)Math.sqrt(xDistance * xDistance + yDistance * yDistance);
        
        //System.out.println("1xDistance: " + xDistance + "   1yDistance: " + yDistance);
        
        xDistance = xDistance / (a / 10);
        yDistance = yDistance / (a / 10);
        
        //System.out.println("2xDistance: " + xDistance + "   2yDistance: " + yDistance);
        
        gotVector = true;
        
        setImage("bullet.png");
        double b = (double)yDistance / (double)xDistance;
        setRotation((int)Math.toDegrees(Math.atan((double)b)));
        //System.out.println("getRotation(): " + Math.toDegrees(Math.atan(b)));
    }
        
    public void startBullet(MCZombie zombie, RangedAttacker r, int x, int y)
    {
        isActive = true;
        z = zombie;
        
        getBulletVector();
        r.setShot(true);
        rAtt = r;
        
        rAttX = r.getX();
        rAttY = r.getY();
        
        myX = x;
        myY = y;
        
        getBulletVector();
    }
    
    public void getMC()
    {
        World world = getWorld();
        myWorld = world;
        List<MCZombie> list = world.getObjects(MCZombie.class);
        
        if (list.size() > 0)
            z = list.get(0);
    }
}
