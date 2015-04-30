import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class RangedAttacker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RangedAttacker extends Human
{
    /**
     * Act - do whatever the RangedAttacker wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    private boolean aboutFace;
    private MCZombie z;
    private int ySpeed = 0, xSpeed = 0;
    private boolean onGround = false;
    private boolean shoot = false;
    private boolean hitDirection;
    private int health = 2;
    private int rotCounter = 0;
    private boolean fall = false;
    private int numImagesRight;
    private int numImagesLeft;
    private Bullet bullet;
    private boolean shot = false;
    private Random rand = new Random();
    private int counter = rand.nextInt();
    
    //sound effects
    GreenfootSound hit = new GreenfootSound("enemyhit0.wav");
    GreenfootSound shootGun = new GreenfootSound("gunsound0.wav");
    GreenfootSound smashing = new GreenfootSound("smashing.wav");
    GreenfootSound terminated = new GreenfootSound("terminated.wav");
    GreenfootSound huah = new GreenfootSound("huah.wav");
    GreenfootSound dieStupid = new GreenfootSound("diestupid.wav");
    
    public RangedAttacker(String image)
    {
        super(image);
        
        if (image.compareTo("HumanGunStandRight.png") == 0)
            aboutFace = false;
        else
            aboutFace = true;
           
    }
    
    public void act() 
    {
        move();
        z = getMC();
        
        if (z != null)
        {
            setGunImage(z);
            
            if (z.getX() + 50 < getX())
                aboutFace = true;
            else if (z.getX() - 50 > getX())
                aboutFace = false;
                
            getShoot(z);
        
            if (shoot)
            {
                if (counter++ % 75 == 0)
                {
                    attack(z);
                }
            }
            
            
            getHit();
        }
        
        
            
            
        
        
        if (fall)
                fallRanged();
        // Add your action code here.
    }
    
    /*private void AnimateLoad()
    {
        int numImage = 0;
        
        while (numImage < numImagesRight) {
            GreenfootImage addImage = new GreenfootImage("Human" + numImage + ".png");
            for (j = 0; j < imageOffset; j++) {
                imagesRight[i+j] = addImage;
            }
            i += j;
            numImage++;
        }    
        i = 0;
        numImage = 0;
        while (numImage < numImagesLeft) {
            GreenfootImage addImage = new GreenfootImage("Human" + numImage + ".png");
            for (j = 0; j < imageOffset; j++) {
                imagesLeft[i+j] = addImage;
            }
            i += j;
            numImage++;
        }
    }*/   
    
    public void move()
    {
        ySpeed++; // adds gravity
        if (xSpeed != 0 && onGround) xSpeed+=aboutFace?10:-10; // add friction
        setLocation(getX(), getY()+ySpeed/2);
        
        while(getOneObjectAtOffset(0, getImage().getHeight()/2+1, Platform.class)!=null)
        {
            setLocation(getX(), getY()-1); 
            onGround=true;
            ySpeed=0;
            
        }
        
    }
        
        //Check right and left low and high
    private MCZombie getMC()
    {
        List<MCZombie> mc = getObjectsInRange(600, MCZombie.class);
        
        if (mc.size() > 0)
            return mc.get(0);
        else
            return null;
    }
    
    private void getShoot(MCZombie zombie)
    {
        if (zombie == null)
            shoot = false;
        else
            shoot = true;
    }
    
    private void shoot(MCZombie zombie)
    {
    }
    
    public void getHit()
    {
        World world = getWorld();
        List<Arm> arm;
        List<Eye> eye;
        boolean p = false;
        int count = -1;
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
        
        if (arm.size() > 0)
        {
            if(intersects(arm.get(0)))
            {
                if (arm.get(0).getX() > getX())
                {
                    decrementHealth();
                    hitDirection = false;
                }
                else
                {
                    decrementHealth();
                    hitDirection = true;
                }
            }
            
        }
        
        if (eye.size() > 0 && count > -1)
        {
            if (intersects(eye.get(count)))
            {
                if (eye.get(count).getX() > getX())
                {
                    decrementHealth();
                    hitDirection = false;
                }
                
                else
                {
                    decrementHealth();
                    hitDirection = true;
                }
            }
        }
    }
    
    private void fallRanged()
    {
        
        
        World world = getWorld();
        if (rotCounter < 90)
        {
            if (rotCounter % 2 == 0)
                setLocation(getX(), getY() + 2);
            
            if (rotCounter < 15)
            {
                if (!aboutFace)
                {
                    setImage("HumanGunHurtRight.png");
                }
                else
                {
                    setImage("HumanGunHurtLeft.png");
                }
                
            }
            
            else if (rotCounter == 18)
            {
                if (!aboutFace)
                    setImage("HumanGunStandRight.png");
                else
                    setImage("HumanGunStandLeft.png");
            }
            
            rotCounter += 3;
            
            if (!hitDirection)
                setRotation(-rotCounter);
            else
                setRotation(rotCounter);
        }
        
        if (rotCounter == 90) {
           int soundCount = rand.nextInt();
        
            if (soundCount % 3 == 0)
            {
                smashing.play();
            }
            
            else if (soundCount % 3 == 1)
            {
                dieStupid.play();
            }
            
            else
            {
                terminated.play();
            }
           world.addObject(new Brain(), getX(), getY());
           world.removeObject(this);
        }
    }
    
    private void decrementHealth()
    {
        Random rand = new Random();
        
        if (rand.nextInt() % 2 == 0)
        {
            hit.play();
        }
            
        if (health > 0)
        {
            health--;
            if (aboutFace)
                setImage("HumanGunHurtLeft.png");
            else
                setImage("HumanGunHurtRight.png");
        }
        
        else
            fall = true;
    }
    
    private void attack(MCZombie zombie)
    {
        World world = getWorld();
        bullet = new Bullet();
        
        if (!shot && zombie != null)
        {
            shootGun.play();
            if (aboutFace)
            {
                if (Math.abs(zombie.getY() - getY()) < 100)
                    world.addObject(bullet, getX() - 70, getY());
                else if (zombie.getY() - getY() > 100)
                    world.addObject(bullet, getX() - 60, getY() + 30);
                else
                {
                    //System.out.println("BUTT");
                    world.addObject(bullet, getX() - 60, getY() - 30);
                }
            }
            
            else
            {
                
                if (Math.abs(zombie.getY() - getY()) < 100)
                    world.addObject(bullet, getX() + 70, getY());
                else if (zombie.getY() - getY() > 100)
                    world.addObject(bullet, getX() + 60, getY() + 30);
                else
                    world.addObject(bullet, getX() + 60, getY() - 30);
            }
            
            //System.out.println("zombie: " + zombie + "   this: " + this + "   bullet: " + bullet);
            bullet.startBullet(zombie, this, bullet.getX(), bullet.getY());
        }
    }
    
    public void setShot(boolean b)
    {
        shot = b;
    }
    
    private void setGunImage(MCZombie zombie)
    {
        if (Math.abs(zombie.getY() - getY()) < 100)
        {
            if (aboutFace)
                setImage("HumanGunAttackLeft0.png");
            else
                setImage("HumanGunAttackRight0.png");
        }
        
        else if (zombie.getY() - getY() > 100)
        {
            if (aboutFace)
                setImage("HumanGunStandLeft.png");
            else
                setImage("HumanGunStandRight.png");
        }
        
        else 
        {
            if (aboutFace)
                setImage("HumanGunAttackLeft1.png");
            else
                setImage("HumanGunAttackRight1.png");
              
        }
    }
    
    /*public boolean compareImage(String image1)
    {
        //System.out.println(image1);
        //System.out.println(getImage().toString());
            //return true;
        
            //System.out.println(getImage().toString());
        return false;
    }*/
    
}
