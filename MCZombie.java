import greenfoot.*;
import java.util.*;

/**
 * A user-controlled actor that walks and jumps, and is pulled down by gravity.
 * <l><li>Left arrow moves actor left (toward left scroll limit)</li>
 * <li>Right arrow moves actor right (toward right scroll limit)</li>
 * <li>Up arrow makes the actor jump</li><l>
 */
public class MCZombie extends Actor
{
    final int jSpeed = 27; // the initial 'jump' speed
    private int ySpeed = 0, xSpeed = 0; // the initial vertical and horizontal speeds
    private boolean aboutFace; // the direction (left or right) the actor is facing
    private boolean onGround; // the state of the actor being set on an object or not
    int imageOffset = 5;
    int imageIdleOffset = 15;
    int numImagesLeft = 4;
    int numImagesRight = 4;
    int numImagesIdle = 4;
    int imageIndexLeft = 0;
    int imageIndexRight = 0;
    int imageIndexIdle = 0;
    int idleCheck = 0;
    boolean isTitle = false;
    int currentLevel = 1;
    
    Arm arm = null;
    Eye eye = null;
    int eyeCount = 0;
    int fireCounter = 23;
    World world;
    List<Arm> arms;
    List<Eye> eyes;
    private GreenfootImage[] imagesRight;
    private GreenfootImage[] imagesLeft;
    private GreenfootImage[] imagesNoArmsRight;
    private GreenfootImage[] imagesNoArmsLeft;
    private GreenfootImage[] imagesIdleLeft;
    private GreenfootImage[] imagesIdleRight;
    private int health = 8;
    private int numBrains = 0;
    private int rotCount = 0;
    private Random rand = new Random();
    
    //Sound effects
    GreenfootSound jump;
    GreenfootSound throwArm;
    GreenfootSound dieStupid;
    GreenfootSound kill[];

    GreenfootSound throwB = new GreenfootSound("throw0.wav");
    private GreenfootSound zombieHit = new GreenfootSound("zomiehit0.wav");

    int attackSoundCounter = 0;
    
    /** 
     * Checks for changes in direction and moves the main actor.
     */
    public MCZombie() {
        AnimateLoad();
        jump = new GreenfootSound("jump.wav");
        throwArm = new GreenfootSound("huah.wav");
        dieStupid = new GreenfootSound("diestupid.wav");
    }
    
    public MCZombie(boolean isTitleScreen) {
        AnimateLoad();
        isTitle = isTitleScreen;
        idleCheck = 1001;
    }
    
    public void act()
    {   
        checkHealth();
        //System.out.println("EyeCount: " + eyeCount);
        if (!isTitle) {
            world = getWorld();
            arms = world.getObjects(Arm.class);
            eyes = world.getObjects(Eye.class);
            if (idle()) {
                if (aboutFace) {
                    AnimateIdleLeft();
                }
                else if (!aboutFace) {
                    AnimateIdleRight();
                }
            }
            getDirection();
            move();
            attack();
            setStandingImage();
        }
        else {
            if (idle()) {
                if (!aboutFace) {
                    AnimateIdleLeft();
                }
            }
        }
    }
    
    private void checkHealth() {
        if (health <= 0) {
            MyWorld world = (MyWorld) getWorld();
            world.addObject(new GameOver("youdied.png"), 520, 350);
            Greenfoot.stop();
        }
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getEyeCount() {
        return eyeCount;
    }
    
    private void setStandingImage()
    {
        if(arms.size() > 0)
        {
            if (xSpeed == 0 && aboutFace && !idle()) {
                setImage("MCZombieStandLeftNoArm.png");
            }
            else if (xSpeed == 0 && !aboutFace && !idle()) {
                setImage("MCZombieStandRightNoArm.png");
            }
        }
        else
        {
            if (xSpeed == 0 && aboutFace && !idle()) {
                setImage("MCZombieStandLeft.png");
            }
            else if (xSpeed == 0 && !aboutFace && !idle()) {
                setImage("MCZombieStandRight.png");
            }
        }
    }
    
    private void attack()
    {    
        attackSoundCounter++;
        if(Greenfoot.isKeyDown("x") && arms.size() == 0)
        {
            throwB.play();
            
            arm = new Arm();
            world.addObject(arm, 100, 100);
            arm.startArm();
            //if(attackSoundCounter % 6 == 0)
                //throwArm.play();
        }
        
        else if(Greenfoot.isKeyDown("z") && eyeCount > 0)
        {
            if (!getActiveEyes())
            {
                eye = new Eye(true);
                world.addObject(eye, 100, 100);
                eye.startEye();
                decrementEyeCount();
                
                //if(attackSoundCounter % 6 == 0)
                    //dieStupid.play();
            }
        }
    
    }
    
    private boolean getActiveEyes()
    {
        List<Eye> eyes;
        Iterator<Eye> it;
        boolean answer = false;
        
        eyes = world.getObjects(Eye.class);
        it = eyes.iterator();
        while(it.hasNext())
        {
            Eye e = it.next();
            if(e.isPosessed())
                answer = true;
        }
        return answer;
    }
    
    private void AnimateLoad() {
        int i = 0;
        int j = 0;
        int numImage = 0;
        imagesRight = new GreenfootImage[numImagesRight*imageOffset];
        imagesLeft = new GreenfootImage[numImagesLeft*imageOffset];
        imagesNoArmsRight = new GreenfootImage[numImagesRight*imageOffset];
        imagesNoArmsLeft = new GreenfootImage[numImagesLeft*imageOffset];
        imagesIdleLeft = new GreenfootImage[numImagesIdle*imageIdleOffset];
        imagesIdleRight = new GreenfootImage[numImagesIdle*imageIdleOffset];
        
        while (numImage < numImagesRight) {
            GreenfootImage addImage = new GreenfootImage("MCZombieWalkRight" + numImage + ".png");
            for (j = 0; j < imageOffset; j++) {
                imagesRight[i+j] = addImage;
            }
            i += j;
            numImage++;
        }    
        i = 0;
        numImage = 0;
        while (numImage < numImagesLeft) {
            GreenfootImage addImage = new GreenfootImage("MCZombieWalkLeft" + numImage + ".png");
            for (j = 0; j < imageOffset; j++) {
                imagesLeft[i+j] = addImage;
            }
            i += j;
            numImage++;
        }
        i = 0;
        numImage = 0;
        
        //No arm images
        while (numImage < numImagesRight) {
            GreenfootImage addImage = new GreenfootImage("MCZombieWalkRightNoArm" + numImage + ".png");
            for (j = 0; j < imageOffset; j++) {
                imagesNoArmsRight[i+j] = addImage;
            }
            i += j;
            numImage++;
        }    
        i = 0;
        numImage = 0;
        while (numImage < numImagesLeft) {
            GreenfootImage addImage = new GreenfootImage("MCZombieWalkLeftNoArm" + numImage + ".png");
            for (j = 0; j < imageOffset; j++) {
                imagesNoArmsLeft[i+j] = addImage;
            }
            i += j;
            numImage++;
        }
        i = 0;
        numImage = 0;
        
        //Idle Images
        while (numImage < numImagesIdle) {
            GreenfootImage addImage = new GreenfootImage("MCZombieCigarLeft" + numImage + ".png");
            for (j = 0; j < imageIdleOffset; j++) {
                imagesIdleLeft[i+j] = addImage;
            }
            i += j;
            numImage++;
        }
        i = 0;
        numImage = 0;
        while (numImage < numImagesIdle) {
            GreenfootImage addImage = new GreenfootImage("MCZombieCigarRight" + numImage + ".png");
            for (j = 0; j < imageIdleOffset; j++) {
                imagesIdleRight[i+j] = addImage;
            }
            i += j;
            numImage++;
        }
    }    
    
    private void AnimateRight() {
        if(arms.size() > 0)
        {
            if (imageIndexRight < numImagesRight*imageOffset)
                setImage(imagesNoArmsRight[imageIndexRight]);
            else
                imageIndexRight = 0;
        }
        else
        {
            if (imageIndexRight < numImagesRight*imageOffset)
                setImage(imagesRight[imageIndexRight]);
            else
                imageIndexRight = 0;
        }
       imageIndexRight++;
    }
    
    private void AnimateLeft() {
        if(arms.size() > 0)
        {
            if (imageIndexLeft < numImagesLeft*imageOffset)
                setImage(imagesNoArmsLeft[imageIndexLeft]);
            else
                imageIndexLeft = 0;
            imageIndexLeft++;
        }
        else
        {
            if (imageIndexLeft < numImagesLeft*imageOffset)
                setImage(imagesLeft[imageIndexLeft]);
            else
                imageIndexLeft = 0;
            imageIndexLeft++;
        }
    }
        
    private void AnimateIdleLeft() {
        if (imageIndexIdle < numImagesIdle*imageIdleOffset)
            setImage(imagesIdleLeft[imageIndexIdle]);
        else
            imageIndexIdle = 0;
        imageIndexIdle++;
    }
    
    private void AnimateIdleRight() {
        if (imageIndexIdle < numImagesIdle*imageIdleOffset)
            setImage(imagesIdleRight[imageIndexIdle]);
        else
            imageIndexIdle = 0;
        imageIndexIdle++;
    }
    
    private boolean idle() {
        if (idleCheck > 500) { 
            return true;
        }
        else {
            idleCheck++;
            return false;
        }    
    }    
    
    /**
     * Moves the actor with appropriate image.  Checks for obstacles and adjusts
     * the position of the actor accordingly.
     */
    private void move()
    {
        Actor obj;
        ySpeed++; // adds gravity
        if (xSpeed != 0 && onGround) xSpeed+=aboutFace?10:-10; // add friction
        setLocation(getX()+xSpeed/10, getY()+ySpeed/2);
        // check for change in horizontal direction
        if((xSpeed>0 && aboutFace) || (xSpeed<0 && !aboutFace)) 
        {
            //getImage().mirrorHorizontally();
            arms = world.getObjects(Arm.class);
            //System.out.println(arms.size());
            if(arms.size() > 0)
            {
                //System.out.println("HERE");
                if (!aboutFace)
                    setImage("MCZombieStandLeftNoArm.png");
                else 
                    setImage("MCZombieStandRightNoArm.png");
                    
                aboutFace = !aboutFace;
            }
            else
            {
                if (!aboutFace)
                    setImage("MCZombieStandLeft.png");
                else 
                    setImage("MCZombieStandRight.png");
                    
                aboutFace = !aboutFace;
            }
        }
        
        // check for obstacles
        onGround=false; // initialize value
        // check below the actor  
        while(getOneObjectAtOffset(0, getImage().getHeight()/2+1, Platform.class)!=null)
        {
            setLocation(getX(), getY()-1); 
            onGround=true;
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
        while(getOneObjectAtOffset(getImage().getWidth()/2 - 25, -10, Platform.class)!=null || getOneObjectAtOffset(getImage().getWidth()/2 - 25, 10, Platform.class)!=null || getOneObjectAtOffset(getImage().getWidth()/2 - 25, 53, Platform.class)!=null || getOneObjectAtOffset(getImage().getWidth()/2 - 35, -52, Platform.class)!=null)
        {
            setLocation(getX()-1, getY());
            xSpeed = 0;
        }
        // check to left of actor
        while(getOneObjectAtOffset(-getImage().getWidth()/2 + 25, -10, Platform.class)!=null || getOneObjectAtOffset(-getImage().getWidth()/2 + 25, 10, Platform.class)!=null || getOneObjectAtOffset(-getImage().getWidth()/2 + 25, 53, Platform.class)!=null || getOneObjectAtOffset(-getImage().getWidth()/2 + 35, -52, Platform.class)!=null)
        {
            setLocation(getX()+1, getY());
            xSpeed = 0;
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
        
        //Moving platforms
        if(getOneObjectAtOffset(0, getImage().getHeight()/2+4, MovingPlatform2.class)!=null)
        {
            Actor p;
            p = getOneObjectAtOffset(0, getImage().getHeight()/2+4, MovingPlatform2.class);
            
            if(((MovingPlatform2)p).getVertical())
                setLocation(getX(), getY() + ((MovingPlatform2)p).getSpeed());
            else
                setLocation(getX() + ((MovingPlatform2)p).getSpeed(), getY());
            
        }
        
        //Fire
        if(getOneObjectAtOffset(0, getImage().getHeight()/2-5, Fire.class)!=null)
        {
            Actor p;
            p = getOneObjectAtOffset(0, getImage().getHeight()/2-5, Fire.class);
            fireCounter++;
            if (fireCounter % 10 == 0)
                decrementHealthFire();
        }
    }
    
    /**
     * Determines any changes in horizontal and vertical speeds for the actor.
     */
    private void getDirection()
    {
//         if (!onGround) return; // if not mid-air changes allowed
        // sets requested direction of move, or continues in current direction
        if (Greenfoot.isKeyDown("left")) 
        {
            AnimateLeft();
            idleCheck = 0;
            if (onGround)
            {
                xSpeed = -50;
               
            }
            else
                xSpeed = -30;
                
            
        }
        if (Greenfoot.isKeyDown("right")) 
        {
            AnimateRight();
            idleCheck = 0;
            if (onGround)
                xSpeed = 50; // check right
            else
                xSpeed = 30;
        }
        if ((Greenfoot.isKeyDown("up") && onGround)) // check jump
        {
            ySpeed -= jSpeed; // add jump speed
            jump.play();
        }
        else if (Greenfoot.isKeyDown("space") && onGround)
        {
            ySpeed -= jSpeed;
            jump.play();
        }
    }
    
    public boolean getAboutFace()
    {
        return aboutFace;
    }
    
    public int getHeight()
    {
        return getImage().getHeight();
    }
    
    public void decrementHealthBLG_MC(Bludgeoner blg)
    {
        zombieHit.play();
        
        if (health > 0)
        {
            health--;
            if (aboutFace)
                setImage("MCZombieHurtLeft.png");
            else
                setImage("MCZombieHurtRight.png");
        }
        
        else
        {
            if (blg.getX() > getX())
                FallMC(false);
            else
                FallMC(true);
        }
    }
    
    public void decrementHealthFire()
    {
        zombieHit.play();
        if (aboutFace)
            setImage("MCZombieHurtLeft.png");
        else
            setImage("MCZombieHurtRight.png");
        if (health > 0)
            health--;
        else
            FallMC(true);
    }
    
    public void increaseHealth() {
        if (health < 8) {
            health++;
        }
    }
    
    public void FallMC(boolean hitDirection)
   {
        
       while (rotCount <= 90)
       {
           if (rotCount % 2 == 0)
                setLocation(getX(), getY() + 2);
            
            if (rotCount < 15)
            {
                if (!aboutFace)
                {
                    setImage("MCZombieHurtRight.png");
                }
                else
                {
                    setImage("MCZombieHurtLeft.png");
                }
                
            }
            
            else if (rotCount == 18)
            {
                if (!aboutFace)
                    setImage("MCZombieStandRight.png");
                else
                    setImage("MCZombieStandLeft.png");
            }
            
            rotCount+=3;
            
            if (!hitDirection)
                setRotation(-rotCount);
            else
                setRotation(rotCount);
            
            
            if (rotCount == 90)
               world.removeObject(this);
        }
    }
    
    public int getCurrentLevel()
    {
        return currentLevel;
    }
    
    public void incrementLevel()
    {
        currentLevel++;
    }
    
    public void incrementEyeCount()
    {
        eyeCount++;
        MyWorld w = (MyWorld) getWorld();
        w.eyeCounter(eyeCount);  
    }
    
    public void decrementEyeCount()
    {
        eyeCount--;
        MyWorld w = (MyWorld) getWorld();
        w.eyeCounter(eyeCount);
    }
}
