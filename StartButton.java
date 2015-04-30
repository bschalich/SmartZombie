import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StartButton extends Actor
{
    int startCheck = 0;
    int imageOffset = 10;
    int numImagesStart = 2;
    private GreenfootImage[] imagesStart;
    int imageIndexStart = 0;
    
    public StartButton() {
        AnimateLoad();  
        
    }
    /**
     * Act - do whatever the StartButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {   
       //REMOVE THIS AND UNCOMMENT THE BLOCK BELOW FOR FINAL
       if (Greenfoot.isKeyDown("enter")) {
               GreenfootSound select = new GreenfootSound("select.wav");
               select.play();
               TitleScreen ts = (TitleScreen) getWorld();
               ts.stopSound();
               //Greenfoot.setWorld(new MyWorld());
               Greenfoot.setWorld(new Tutorial());
       }
       if (startCheck > 1620) { //1620
           AnimateStart();
           /*if (Greenfoot.isKeyDown("enter")) {
               GreenfootSound select = new GreenfootSound("select.wav");
               select.play();
               TitleScreen ts = (TitleScreen) getWorld();
               ts.stopSound();
               ts.stopped();
               //Greenfoot.setWorld(new MyWorld());
               Greenfoot.setWorld(new Tutorial());
           }*/
       }
       startCheck++;
    }    
    
    private void AnimateLoad() {
        imagesStart = new GreenfootImage[numImagesStart*imageOffset];
        int numImage = 0;
        int i = 0;
        int j = 0;
        while (numImage < numImagesStart) {
            GreenfootImage addImage = new GreenfootImage("StartGame" + numImage + ".png");
            for (j = 0; j < imageOffset; j++) {
                imagesStart[i+j] = addImage;
            }
            i+=j;
            numImage++;
        }
    }
    
    private void AnimateStart() {
        if (imageIndexStart < numImagesStart*imageOffset) {
            setImage(imagesStart[imageIndexStart]);
        }
        else {
            imageIndexStart = 0;
        }
        imageIndexStart++;
    }
}
