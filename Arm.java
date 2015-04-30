import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class ArmWeapon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Arm extends Weapon
{
    int imageIndex = 0;
    int imageOffset = 5;
    int numImagesLeft = 3;
    int numImagesRight = 3;
    int imageIndexLeft = 0;
    int imageIndexRight = 0;
    int armSpeed = 10;
    int returnTime = 60;
    int currentTime = 0;
    boolean direction; //TRUE - Left, FALSE - Right
    boolean isActive = false;
    boolean isOnWayBack = false;
    MCZombie player;
    private GreenfootImage[] imagesRight;
    private GreenfootImage[] imagesLeft;
    World world;
    GreenfootSound hit;
    
    public Arm()
    {
        hit = new GreenfootSound("whack.wav");
    }
    public void act() 
    {
        //startArm();
        if (isActive)
        {
            moveArm();
            collisionDetection();
            currentTime++;
            if(currentTime >= returnTime)
            {
                isOnWayBack = true;
            }
        }
    }    
    
    private void collisionDetection()
    {
        if(isOnWayBack)
        {
            //Offsets have arm go into player instead of dissapearing when images intersect
            if(player.getX() > getX() && getOneObjectAtOffset(-30, -30, MCZombie.class) != null)
            {
                isOnWayBack = false;
                world.removeObject(this);
            }
            else if(player.getX() < getX() && getOneObjectAtOffset(30, 30, MCZombie.class) != null)
            {
                isOnWayBack = false;
                world.removeObject(this);
            }
        }
        else if (intersects(player))
        {
            //player just throws object and it intersects with it
            //DO nothing
        }
        else if(isTouching(Platform.class) || isTouching(DumbZombie.class) || isTouching(Human.class))
        {
            isOnWayBack = true;
            direction = !direction;
            hit.play();
        }
    }
    
    private void moveArm()
    {
        int xDistance;
        int yDistance;
        
        //rotate
        setRotation(getRotation() + 20);
        
        if(isOnWayBack)
        {
            xDistance = player.getX() - getX();
            yDistance = player.getY() - getY();
            while(Math.abs(xDistance) > armSpeed || Math.abs(yDistance) > armSpeed)
            {
                xDistance *= 0.9;
                yDistance *= 0.9;
            }
            setLocation(getX() + xDistance , getY() + yDistance);
        }
        //Arm is going RIGHT
        else if(direction)
        {
            setLocation(getX()-armSpeed, getY());
        }
        //LEFT
        else
        {
            setLocation(getX()+armSpeed, getY());
        }
    }
    
    public void startArm()
    {
        List<MCZombie> mc;
        
        isActive = true;
        world = getWorld();
        mc = world.getObjects(MCZombie.class);
        player = mc.get(0);
        direction = player.getAboutFace();
        setImage("ArmBoomerang.png");
        setLocation(player.getX(), player.getY());
    }
}
