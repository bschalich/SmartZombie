import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class Eye here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Eye extends Weapon
{
    private int moveX = 9;
    private int grav = 1;
    private int moveY = 0;
    private MCZombie player;
    boolean direction; //TRUE - Right, FALSE - Left
    World world;
    int imageOffset = 3;
    int numImages = 5;
    private GreenfootImage[] images;
    private GreenfootImage eye;
    int imageIndex = 0;
    boolean posess;
    Random rand = new Random();
    boolean explFlag = true;
    
    //sound effects
    GreenfootSound explosion0 = new GreenfootSound("explosion0.wav");
    GreenfootSound explosion1 = new GreenfootSound("explosion1.wav");
    
    public Eye(boolean p)
    {
        setImage("Eyeball.png");
        posess = p;
    }
    
    public void act() 
    {
        if (posess)
        {
            moveEye();
            collisionCheck();
        }
        
        else
        {
            collectEye();
        }
    }    
    
    private void collisionCheck()
    {
        //Explode
        if((isTouching(Platform.class) || isTouching(DumbZombie.class) || isTouching(Human.class)) && !isTouching(MCZombie.class))
        {
            moveY = 0;
            moveX = 0;
            
            if (explFlag)
            {
                if (rand.nextInt() % 2 == 0)
                    explosion0.play();
                else
                    explosion1.play();
                explFlag = false;
            }
            
            if(imageIndex >= imageOffset * numImages)
            {       
                world.removeObject(this);
            }
            else
            {
                runAnimation();
            }
        }
    }
    
    private void runAnimation()
    {
        setImage(images[imageIndex]);
        imageIndex++;
    }
    
    private void moveEye()
    {
        setLocation(getX() + moveX, getY() - moveY);
        moveY = moveY - grav;
    }
    
    public void startEye()
    {
        List<MCZombie> mc;
        
        world = getWorld();
        mc = world.getObjects(MCZombie.class);
        player = mc.get(0);
        direction = player.getAboutFace();
        
        if(direction)
            moveX = -moveX;
            
        setImage("Eyeball.png");
        setLocation(player.getX(), player.getY());
        moveY = 20;
        
        //load animation
        images = new GreenfootImage[numImages*imageOffset];
        for(int i = 0; i < numImages; i++)
        {
            GreenfootImage addImage = new GreenfootImage("Explosion" + i + ".png");
            for (int j = 0; j < imageOffset; j++)
            {
                images[i*imageOffset + j] = addImage;
            }
        }
    }
    
    private void collectEye()
    {
        World world = getWorld();
        List<MCZombie> mc = world.getObjects(MCZombie.class);
        
        if(mc.size() > 0)
        {
            if (intersects(mc.get(0)))
            {
                world.removeObject(this);
                mc.get(0).incrementEyeCount();
                posess = true;
            }
        }
    }
    
    public boolean isPosessed()
    {
        return posess;
    }
}
