import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.lang.Integer;

/**
 * Write a description of class Score here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Score extends Actor
{
    /**
     * Act - do whatever the Score wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int enemies;
    public Score(int num) {
        enemies = num;
        setImage(new GreenfootImage(Integer.toString(enemies), 25, Color.WHITE, Color.BLACK));
    }
    public void act() 
    {
        
    } 
    
    public void setScore(int score) {
        enemies = score;
        setImage(new GreenfootImage(Integer.toString(enemies), 25, Color.WHITE, Color.BLACK));
    }    
}
