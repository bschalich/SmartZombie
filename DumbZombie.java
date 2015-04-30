import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class DumbZombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class DumbZombie extends Actor
{
    final int jSpeed = 27; // the initial 'jump' speed
    private int ySpeed = 0, xSpeed = 0; // the initial vertical and horizontal speeds
    boolean aboutFace; // the direction (left or right) the actor is facing
    private boolean onGround; // the state of the actor being set on an object or not
    int imageOffset = 15;
    int numImagesLeft = 3;
    int numImagesRight = 3;
    int imageIndexLeft = 0;
    int imageIndexRight = 0;
    int l, r;
    private GreenfootImage[] imagesRight;
    private GreenfootImage[] imagesLeft;
    private World world = getWorld();
    private boolean hitWithWeapon = false;
    private int rotCount = 0;
    private boolean hitDirection;
    private int health = 6;
    private Random rand = new Random();
    
    //sound effects
    private GreenfootSound brains = new GreenfootSound("rains0.wav");
    
    
    /** 
     * Checks for changes in direction and moves the main actor.
     */
    public DumbZombie(String image, int left, int right)
    {
        //super(image);
        setImage(image);
        getImage().scale(100, 100);
        AnimateLoad();
        if (image.compareTo("ZombieWalkRight0.png") == 0)
            aboutFace = false;
        else
            aboutFace = true;
    }
    
    public void act()
    {
        World w = getWorld();
        List<MCZombie> list = w.getObjects(MCZombie.class);
        if (list.size() > 0)
        {
            if ((rand.nextInt() % 750 == 0) && Math.abs(list.get(0).getX() - getX()) < 500 && Math.abs(list.get(0).getY() - getY()) < 500)
                brains.play();
        }
            
        if (xSpeed > 0)
            aboutFace = false;
        else if (xSpeed < 0)
            aboutFace = true;
        
        if (!hitWithWeapon)
        {
            getDirection();
            move();
            hitDirection = getHit();
        }
        
        if (xSpeed == 0 && aboutFace) 
        {
            setImage("ZombieWalkLeft0.png");
        }
        else if (xSpeed == 0 && !aboutFace) 
        {
            setImage("ZombieWalkRight0.png");
        }
        
        
        
        if (hitWithWeapon)
            killZombie();
            
        /*if (hitWithWeapon && rotCount < 90)
        {
            //System.out.println(aboutFace);
            if (rotCount <= 15)
            {
                if (aboutFace)
                    setImage("ZombieHurtLeft.png");
                 else
                    setImage("ZombieHurtRight.png");
                
                rotCount += 3;
                
            }
            
            else if (rotCount == 18)
            {
                if (aboutFace)
                    setImage("ZombieWalkLeft0.png");
                else
                    setImage("ZombieWalkRight0.png");
                
                rotCount += 3;
            }
            else 
            {
                
                setRotation(rotCount+=3);
                if (rotCount % 2 == 0)
                    setLocation(getX(), getY() + 2);
              
            }
        }
        
        if (rotCount >= 90)
            rotCount++;
        
        if (rotCount == 100) {
            ((MyWorld)world).enemyCounter();
           world.removeObject(this);
        }*/

        //System.out.println(onGround);
    }
    
    private void AnimateLoad() {
        int i = 0;
        int j = 0;
        int numImage = 0;
        imagesRight = new GreenfootImage[numImagesRight*imageOffset];
        imagesLeft = new GreenfootImage[numImagesLeft*imageOffset];
        while (numImage < numImagesRight) {
            GreenfootImage addImage = new GreenfootImage("ZombieWalkRight" + numImage + ".png");
            for (j = 0; j < imageOffset; j++) {
                imagesRight[i+j] = addImage;
            }
            i += j;
            numImage++;
        }    
        i = 0;
        numImage = 0;
        while (numImage < numImagesLeft) {
            GreenfootImage addImage = new GreenfootImage("ZombieWalkLeft" + numImage + ".png");
            for (j = 0; j < imageOffset; j++) {
                imagesLeft[i+j] = addImage;
            }
            i += j;
            numImage++;
        }
    }    
    
    private void AnimateRight() {
        if (imageIndexRight < numImagesRight*imageOffset)
            setImage(imagesRight[imageIndexRight]);
        else
            imageIndexRight = 0;
       imageIndexRight++;
    }
    
    private void AnimateLeft() {
        if (imageIndexLeft < numImagesLeft*imageOffset)
            setImage(imagesLeft[imageIndexLeft]);
        else
            imageIndexLeft = 0;
        imageIndexLeft++;
    }
        
    /**
     * Moves the actor with appropriate image.  Checks for obstacles and adjusts
     * the position of the actor accordingly.
     */
    private void move()
    {
        Actor obj;
        ySpeed++; // adds gravity
        /*if (xSpeed != 0 && onGround) 
        {
            xSpeed+=aboutFace?10:-10;
        }// add friction*/
            
        setLocation(getX()+xSpeed/15, getY()+ySpeed/2);
         
        // check for change in horizontal direction
        if((xSpeed>0 && aboutFace) || (xSpeed<0 && !aboutFace)) 
        {
            //getImage().mirrorHorizontally();
            if (aboutFace)
                setImage("ZombieWalkLeft0.png");
            else 
                setImage("ZombieWalkRight0.png");
                
            aboutFace = !aboutFace;
        }
        
        // check for obstacles
        onGround=false; // initialize value
        // check below the actor
        
        //System.out.println(getOneObjectAtOffset(0, getImage().getHeight()/2+1, null));
  
        while(getOneObjectAtOffset(0, getImage().getHeight()/2+1, Platform.class)!=null)
        {
            onGround=true;
            setLocation(getX(), getY()-1); 
            ySpeed=0;
        }
        // check above the actor
        while((obj = getOneObjectAtOffset(0, -getImage().getHeight()/2-1, Platform.class))!=null) 
        {
            
            setLocation(getX(), getY()+1);
            ySpeed = 0;
            
        }
        
        //Check right and left low and high
        // check to right of actor
        while(getOneObjectAtOffset(getImage().getWidth()/2 - 25, -10, Platform.class)!=null || getOneObjectAtOffset(getImage().getWidth()/2 - 25, 10, Platform.class)!=null || getOneObjectAtOffset(getImage().getWidth()/2 - 25, 53, Platform.class)!=null || getOneObjectAtOffset(getImage().getWidth()/2 - 25, -44, Platform.class)!=null)
        {
            setLocation(getX()-1, getY());
            xSpeed = -15;
           
        }
        // check to left of actor
        while(getOneObjectAtOffset(-getImage().getWidth()/2 + 25, -10, Platform.class)!=null || getOneObjectAtOffset(-getImage().getWidth()/2 + 25, 10, Platform.class)!=null || getOneObjectAtOffset(-getImage().getWidth()/2 + 25, 53, Platform.class)!=null || getOneObjectAtOffset(-getImage().getWidth()/2 + 25, -44, Platform.class)!=null)
        {
            setLocation(getX()+1, getY());
            xSpeed = 15;
        } 
        
        //System.out.println(ySpeed);
    }
    
    private boolean getHit()
    {
        List<Arm> arm = null;
        List<Eye> eye = null;
        
        int count = -1;
        
        
        
        
        if (this != null)
        {
            world = getWorld();
            
            if (world != null)
            {
                arm = world.getObjects(Arm.class);
                eye = world.getObjects(Eye.class);
                
                for (int i = 0; i < eye.size(); i++)
                {
                    if (eye.get(i).isPosessed())
                    {
                        count = i;
                        break;
                    }
                }
            }
            
            if (arm != null && arm.size() > 0)
            {
                if(intersects(arm.get(0)))
                {
                    hitWithWeapon = true;
                    
                   
                    
                    if (arm.get(0).getX() > getX())
                    {
                        
                        return false;
                    }
                    else
                    {
                        
                        return true;
                    }
                }
                
                    
            }
            else if (eye != null && eye.size() > 0 && count > -1)
            {
                if(intersects(eye.get(count)))
                {
                    
                        
                    hitWithWeapon = true;
                    
                    if (eye.get(count).getX() > getX())
                    {
                        return false;
                    }
                    else
                    {
                        return true;
                    }
                }
            }
        }
        //System.out.println("3");
        return false;
            
    }
    
    
    
    /**
     * Determines any changes in horizontal and vertical speeds for the actor.
     */
    private void getDirection()
    {
//         if (!onGround) return; // if not mid-air changes allowed
        // sets requested direction of move, or continues in current direction
         Random rn = new Random();
         int i = rn.nextInt() % 100;
         
         if (i < 0)
            i = -i;
            
        // System.out.println(i);
         
         if (i >= 99)
         {
            if (xSpeed == 0)
            {
                if (aboutFace == true)
                    xSpeed = -15;
                
                else
                    xSpeed = 15;
            }
                   
                xSpeed = -xSpeed;
                //aboutFace = !aboutFace;
            
            
        }
        
        else if (i <=1)
            xSpeed = 0;
        
        if (xSpeed > 0)
                AnimateRight();
        else
                AnimateLeft();
        
        
    }
    
    private void killZombie()
    {   
        
        if (rotCount < 90)
        {
            
            if (rotCount % 2 == 0)
                setLocation(getX(), getY() + 2);
            
            if (rotCount < 15)
            {
                if (!aboutFace)
                    setImage("ZombieHurtRight.png");
                else
                    setImage("ZombieHurtLeft.png");
            }
            
            else if (rotCount == 18)
            {
                if (!aboutFace)
                    setImage("ZombieWalkRight0.png");
                else
                    setImage("ZombieWalkLeft0.png");
            }
            
            rotCount+=3;
            
            
            
            if (!hitDirection)
            {
                setRotation(-rotCount);
            }
            else
                setRotation(rotCount);
        }
        
        if (rotCount == 90) {
           int x = getX();
           int y = getY();
           world.removeObject(this);
           Random rand = new Random();
           if (rand.nextInt() % 2 == 0)
           {
                world.addObject(new Eye(false), x, y);
           
           }
        }
    }
    
    public boolean getAboutFace()
    {
        return aboutFace;
    }
    
    public void decrementHealthBLG_DZ(Bludgeoner bludg)
    {
        World world = getWorld();
        
        //System.out.println("Health: " + health);
        if (health > 0)
        {
            health--;
            if (aboutFace)
                setImage("ZombieHurtLeft.png");
            else
                setImage("ZombieHurtRight.png");
        }
            
        if (health == 0)
        {
            if (bludg.getX() < getX())
            {
                hitDirection = true;
                
            }
            else if (bludg.getX() > getX())
            {
                
                hitDirection = false;
            }
                
            hitWithWeapon = true;
            
            killZombie();
        }
    }
}
    

