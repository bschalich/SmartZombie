import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TitleScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EndScreen extends SWorld
{
    public GreenfootSound backgroundMusic = new GreenfootSound("megavirus.mp3");
    public MovingPlatform titleScroller;
    public MCZombie mc;
    public StartButton button;
    public int count = 0;
    private GreenfootImage imagesBG[];
    private int numImagesBG = 6;
    private int imageOffset = 10;
    private int imageIndexBG = 0;
    private int volume = 30;
    private EndObject end;
    /**
     * Constructor for objects of class TitleScreen.
     * 
     */
    public EndScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 720, 1, 720, 1800);
        Greenfoot.start();
        backgroundMusic.setVolume(volume);
        backgroundMusic.playLoop();
        titleScroller = new MovingPlatform("invisRoad.png", 50, 100, true);
        GreenfootImage bg = new GreenfootImage("EndingScreen0.png");
        setMainActor(titleScroller, 70, 300);
        mainActor.setLocation(60, 2600);
        setScrollingBackground(bg);  //270, -130
        AnimateLoad();
        addObject(new MCZombie(true), 400, 1128);
        end = new EndObject();
        addObject(end, 512, 620, false);//-150
    }
    
    public void lowerVolume() {
        if (volume > 0) {
            volume--;
            backgroundMusic.setVolume(volume);
        }
    }
    
    public int getCount() {
        return count;
    }
    
    public void incrementCount() {
        count++;
    }
    
    private void AnimateLoad() {
        int i = 0;
        int j = 0;
        int numImage = 0;
        imagesBG = new GreenfootImage[numImagesBG*imageOffset];
        
        while (numImage < numImagesBG) {
            GreenfootImage addImage = new GreenfootImage("EndingScreen" + numImage + ".png");
            for (j = 0; j < imageOffset; j++) {
                imagesBG[i+j] = addImage;
            }
            i += j;
            numImage++;
        }    
    }
    
    public void AnimateBG() {
        if (imageIndexBG < numImagesBG*imageOffset) {
            setScrollingBackground(imagesBG[imageIndexBG]);
        }
        else {
            Greenfoot.setWorld(new Sunset());
        }
        imageIndexBG++;
    }
    
    public void stopSound() {
        backgroundMusic.stop();
    }
    
    public void stopped() {
        removeObject(titleScroller);
        removeObject(mc);
        removeObject(button);
    }
}
