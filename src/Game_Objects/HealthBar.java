/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

import Effect.CacheDataLoader;
import Effect.FrameImage;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Admin
 */
public class HealthBar extends ParticularObject {
    
    public static int TimeToUpdate = 100000;
    
    private ParticularObject ownerHealthBar;
    
    private int maxHealth;
    private int currentHealth;
    private int nextHealth;
    private int widthCurrent;
    
    private long lastTimeUpdate;
    
    private boolean isHealthUpdating;
    
    private FrameImage fullHealthBarImage;
    private FrameImage frameHealthBarImage;
    
    
    public HealthBar(float x, float y, int blood, ParticularObject obj, GameWorld gameWorld) {
        super(x, y, 100, 100, 0, blood, gameWorld);
        
        fullHealthBarImage = CacheDataLoader.getInstance().getFrameImage("health_bar");
        
        frameHealthBarImage = CacheDataLoader.getInstance().getFrameImage("frame_no_health");
        
        ownerHealthBar = obj;
        
        currentHealth = blood;
        
        nextHealth = blood;
        
        widthCurrent = blood;
        
        maxHealth = blood;
    }
    
    


    public void Update(long currentTime){

        //System.out.println("health : " + this.ownerHealthBar.getBlood());
        
        setPosX(getGameWorld().camera.getPosX() + getWidth() + 50);
        setPosY(getGameWorld().camera.getPosY() + 30);
        
        if(lastTimeUpdate == 0){
            lastTimeUpdate = currentTime;
        }

        if(nextHealth != ownerHealthBar.getBlood() && !isHealthUpdating){
            nextHealth= ownerHealthBar.getBlood();
            lastTimeUpdate = currentTime;
        }

        if(nextHealth < currentHealth){
            if(currentTime - lastTimeUpdate >= TimeToUpdate){
                currentHealth--;
                lastTimeUpdate = currentTime;
                widthCurrent -= (this.getWidth() / maxHealth);
            }
        }
        else if(nextHealth > currentHealth){
            if(currentTime - lastTimeUpdate >= TimeToUpdate){
                currentHealth++;
                lastTimeUpdate = currentTime;
                widthCurrent += (this.getWidth() / maxHealth);
            }
        }
        else{
                isHealthUpdating = false;
        }
            
        if(widthCurrent < 0){
            widthCurrent = 0;
        }
    }
    
    @Override
    public void attack() {}

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return new Rectangle(10,10,10,10);
    }

    @Override
    public void draw(Graphics2D g2) {

        int x = (int)getPosX() - (int)getGameWorld().camera.getPosX();
        
        int y = (int)getPosY() - (int)getGameWorld().camera.getPosY();
        
        
        g2.drawImage(frameHealthBarImage.getImage(), x - frameHealthBarImage.getImage().getWidth()/2, y - frameHealthBarImage.getImage().getHeight()/2, null);
        
        g2.drawImage(fullHealthBarImage.getImage(), x - (fullHealthBarImage.getImage().getWidth()/2), y - (fullHealthBarImage.getImage().getHeight()/2) + 1, widthCurrent, fullHealthBarImage.getImage().getHeight(), null);
    }

    @Override
    public void death() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
