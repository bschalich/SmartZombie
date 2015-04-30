import greenfoot.*; 
import java.util.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bludgeoner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bludgeoner extends Human
{
    /**
     * Act - do whatever the Bludgeoner wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    final int jSpeed = 27; // the initial 'jump' speed
    private int ySpeed = 0, xSpeed = 0; // the initial vertical and horizontal speeds
    boolean aboutFace; // the direction (left or right) the actor is facing
    private boolean onGround; // the state of the actor being set on an object or not
    int imageOffset = 9;
    int imageAttackOffset = 19; //18
    int numImagesLeft = 4;
    int numImagesRight = 4;
    int imageAttackIndexLeft = 0;
    int imageAttackIndexRight = 0;
    int imageIndexLeft = 0;
    int imageIndexRight = 0;
    int numImagesAttackRight = 2;
    int numImagesAttackLeft = 2;
    int l, r;
    private GreenfootImage[] imagesRight;
    private GreenfootImage[] imagesLeft;
    private GreenfootImage[] imagesAttackRight;
    private GreenfootImage[] imagesAttackLeft;
    private World world = getWorld();
    private boolean hitWithWeapon = false;
    private int rotCount = 0;
    int canJump = 0;
    boolean hitDirection;
    int count = 0;
    int attackCountDZ = 0;
    int attackCountMC = 0;
    DumbZombie dz;
    MCZombie mc;
    int right, left;
    private int health = 3;
    private MCZombie mcZombie;
    
    //sound effects
    GreenfootSound hit = new GreenfootSound("enemyhit0.wav");
    
    public Bludgeoner(String image, int l, int r)
    {
        super(image);
        AnimateLoad();
        AnimateLoadAttack();
        if (image.compareTo("HumanStandRight.png") == 0)
            aboutFace = false;
        else
            aboutFace = true;
            
        left = l;
        right = r;
    }
    
    public void act()
    {
        count++;
        mcZombie = getMCZombie();
        //System.out.println("health: " + health);
        
        if (health <= 0) {
            FallHuman();
        }
        else
        {
            if (findAttackDZ())
            {
                if (aboutFace && dz.getX() < getX())
                    AnimateAttackLeft();
                else
                    AnimateAttackRight();
                if (attackCountDZ++ % 40 == 0)
                {
                    dz.decrementHealthBLG_DZ(this);
                }
            }
            else if (findAttackMC())
            {
                    if (aboutFace)
                    {
                        AnimateAttackLeft();
                    }
                    else
                    {
                        AnimateAttackRight();
                    }
                if (attackCountMC++ % 40 == 0)
                {   
                        
                    mc.decrementHealthBLG_MC(this);
                }
            }

            if ((canJump = super.findPlatform()) > 0)
            {
                /*System.out.println("canJump: " + canJump);
                System.out.println("aboutFace: " + aboutFace);
                System.out.println("OnGroud: " + onGround);*/
                if (canJump == 1 && aboutFace && onGround)
                {
                    jump();
                }
                if (canJump == 2 && !aboutFace && onGround)
                {
                    //System.out.println("JUMP");
                    jump();
                    
                }
            }
                
            if (xSpeed > 0)
                aboutFace = false;
            else if (xSpeed < 0)
                aboutFace = true;
            
            
            if (health == 1)
                hitDirection = getHit();
            
            else
                getHit();
                
            if (health > 0)
            {
                getDirection();
                move();
            }
        }
        //System.out.println(xSpeed);
    }
    
    private void AnimateLoad() {
        int i = 0;
        int j = 0;
        int numImage = 0;
        imagesRight = new GreenfootImage[numImagesRight*imageOffset];
        imagesLeft = new GreenfootImage[numImagesLeft*imageOffset];
        while (numImage < numImagesRight) {
            GreenfootImage addImage = new GreenfootImage("HumanWalkRight" + numImage + ".png");
            for (j = 0; j < imageOffset; j++) {
                imagesRight[i+j] = addImage;
            }
            i += j;
            numImage++;
        }    

        i = 0;
        numImage = 0;
        while (numImage < numImagesLeft) {
            GreenfootImage addImage = new GreenfootImage("HumanWalkLeft" + numImage + ".png");
            for (j = 0; j < imageOffset; j++) {
                imagesLeft[i+j] = addImage;
            }
            i += j;
            numImage++;
        }
    } 
    
    private void AnimateLoadAttack() {
        int i = 0;
        int j = 0;
        int numImage = 0;
        imagesAttackRight = new GreenfootImage[numImagesAttackRight*imageAttackOffset];
        imagesAttackLeft = new GreenfootImage[numImagesAttackLeft*imageAttackOffset];
        while (numImage < numImagesAttackRight) {
            GreenfootImage addImage = new GreenfootImage("HumanAttackRight" + numImage + ".png");
            for (j = 0; j < imageAttackOffset; j++) {
                imagesAttackRight[i+j] = addImage;
            }
            i += j;
            numImage++;
        }    
        i = 0;

        numImage = 0;
        while (numImage < numImagesAttackLeft) {
            GreenfootImage addImage = new GreenfootImage("HumanAttackLeft" + numImage + ".png");
            for (j = 0; j < imageAttackOffset; j++) {
                imagesAttackLeft[i+j] = addImage;
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
    
    private void AnimateAttackRight() {
        if (imageAttackIndexRight < numImagesAttackRight*imageAttackOffset)
            setImage(imagesAttackRight[imageAttackIndexRight]);
        else
            imageAttackIndexRight = 0;
       imageAttackIndexRight++;
    }
    
    private void AnimateAttackLeft() {
        if (imageAttackIndexLeft < numImagesAttackLeft*imageAttackOffset)
            setImage(imagesAttackLeft[imageAttackIndexLeft]);
        else
            imageAttackIndexLeft = 0;
       imageAttackIndexLeft++;
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
        
        if ((count % 4 > 0))
            setLocation(getX()+xSpeed/10, getY()+ySpeed/2);
         
        // check for change in horizontal direction
        if((xSpeed>0 && aboutFace) || (xSpeed<0 && !aboutFace)) 
        {
            //getImage().mirrorHorizontally();
            if (aboutFace)
                setImage("HumanStandLeft.png");
            else 
                setImage("HumanStandRight.png");
                
            aboutFace = !aboutFace;
        }
        
        // check for obstacles
        onGround=false; // initialize value
        // check below the actor
        
        //System.out.println(getOneObjectAtOffset(0, getImage().getHeight()/2+1, null));
  
        while(getOneObjectAtOffset(0, getImage().getHeight()/2+5, Platform.class)!=null)
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
           
        }
        // check to left of actor
        while(getOneObjectAtOffset(-getImage().getWidth()/2 + 25, -10, Platform.class)!=null || getOneObjectAtOffset(-getImage().getWidth()/2 + 25, 10, Platform.class)!=null || getOneObjectAtOffset(-getImage().getWidth()/2 + 25, 53, Platform.class)!=null || getOneObjectAtOffset(-getImage().getWidth()/2 + 25, -44, Platform.class)!=null)
        {
            setLocation(getX()+1, getY());
            
        } 
        
        //DumbZombie collision detection
        //Check below actor
        while(getOneObjectAtOffset(0, getImage().getHeight()/2-25, DumbZombie.class)!=null)
        {
            setLocation(getX(), getY()-1); 
            onGround=true;
            ySpeed=0;
            
        }
        // check above the actor
        while((obj = getOneObjectAtOffset(0, -getImage().getHeight()/2-50, DumbZombie.class))!=null) 
        {
            
            setLocation(getX(), getY()+1);
            ySpeed = 0;
            
        }
        
        //Check right and left low and high
        // check to right of actor
        while(getOneObjectAtOffset(getImage().getWidth()/2 - 42, -10, DumbZombie.class)!=null || getOneObjectAtOffset(getImage().getWidth()/2 - 42, 10, DumbZombie.class)!=null || getOneObjectAtOffset(getImage().getWidth()/2 - 42, -44, DumbZombie.class)!=null)
        {
            setLocation(getX()-1, getY());
            xSpeed = 0;
        }
        // check to left of actor
        while(getOneObjectAtOffset(-getImage().getWidth()/2 + 42, -10, DumbZombie.class)!=null || getOneObjectAtOffset(-getImage().getWidth()/2 + 42, 10, DumbZombie.class)!=null || getOneObjectAtOffset(-getImage().getWidth()/2 + 42, -44, DumbZombie.class)!=null)
        {
            setLocation(getX()+1, getY());
            xSpeed = 0;
        } 
        
        //System.out.println(ySpeed);
    }
    
    private boolean getHit()
    {
        List<Arm> arm;
        List<Eye> eye;
        world = getWorld();
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
                    return false;
                }
                else
                {
                    decrementHealth();
                    hitDirection = true;
                    return true;
                }
            }
        }
        else if (eye.size() > 0 && count > -1)
        {
            if(intersects(eye.get(count)))
            {
                if (eye.get(count).getX() > getX())
                {
                    health--;
                    return false;
                }
                else
                {
                    health--;
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Determines any changes in horizontal and vertical speeds for the actor.
     */
    private void getDirection()
    {
        World world = getWorld();
        List<MCZombie> mc;
        MCZombie z;
        mc = world.getObjects(MCZombie.class);
        z = mc.get(0);
        
        int y = Math.abs(z.getY() - getY());
        int x = Math.abs(z.getX() - getX());
        
        
        if (findAttackDZ())
            xSpeed = 0;
            
        else if ((z.getX() + (z.getImage().getWidth() - 10) < getX()) && y < 300 && x < 500)
        {
            xSpeed = -30;
            aboutFace = true;
            AnimateLeft();
        }
                
        else if ((z.getX() - (z.getImage().getWidth() - 10) > getX()) && y < 300 & x < 500)
        {
            xSpeed = 30;
            aboutFace = false;
            AnimateRight();
        }
        
        else if (y < 100)
        {
            xSpeed = 0;
            super.attack();
        }
        
        else
            xSpeed = 0;
            
        
        
        if (z.getX() + 10 > getX())
        {
            aboutFace = false;
        }
        
        if (z.getX() - 10 < getX())
        {
            aboutFace = true;
        }
        

        
    }
    
    private void dieHuman()
    {
        if (hitWithWeapon == false)
            getHit();
        
    }
    
    public boolean getAboutFace()
    {
        return aboutFace;
    }
    
    public int getHeight()
    {
        return getImage().getHeight();
    }
    
    private void jump()
    {
        ySpeed = -jSpeed;
    }
    
   public void FallHuman()
   {

        
        //System.out
        if (rotCount < 90)
        {
            if (rotCount % 2 == 0)
                setLocation(getX(), getY() + 2);
            
            if (rotCount < 15)
            {
                if (!aboutFace)
                {
                    setImage("HumanHurtRight.png");
                }
                else
                {
                    setImage("HumanHurtLeft.png");
                }
                
            }
            
            else if (rotCount == 18)
            {
                if (!aboutFace)
                    setImage("HumanStandRight.png");
                else
                    setImage("HumanStandLeft.png");
            }
            
            rotCount+=3;
            
            if (!hitDirection)
                setRotation(-rotCount);
            else
                setRotation(rotCount);
        }
        
        if (rotCount == 90) {
           world.addObject(new Brain(), getX(), getY());
           world.removeObject(this);
        }
    }
    
    private boolean findAttackDZ()
    {
        Actor a;
        
        if ((a = getOneObjectAtOffset(getImage().getWidth()/2 - 10, 0, DumbZombie.class))!=null && !aboutFace)
        {
            dz = (DumbZombie)a;
            return true;
        }
        // check to left of actor
        if ((a = getOneObjectAtOffset(-getImage().getWidth()/2 + 10, 0, DumbZombie.class))!=null && aboutFace) 
        {
            dz = (DumbZombie)a;
            return true;
        }
        
        return false;
    }
    
    public boolean findAttackMC()
    {
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
    
    private MCZombie getMCZombie()
    {
        World world = getWorld();
        List<MCZombie> mc = world.getObjects(MCZombie.class);
        
        return mc.get(0);
    }
    
    private void decrementHealth()
    {
        Random rand = new Random();
        if (rand.nextInt() % 2 == 0)
            hit.play();
            
        health--;
        if (aboutFace)
            setImage("HumanHurtLeft.png");
        else
            setImage("HumanHurtRight.png");
    }
        
}
    

   

