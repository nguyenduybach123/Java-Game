/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

import Effect.CacheDataLoader;
import Effect.FrameImage;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Admin
 */
public class StaminaBar extends ParticularObject{
    
    public static int TimeToUpdate = 100000;
    
    private ParticularObject ownerStaminaBar;
    private int widthCurrent;
    private int maxStamina;
    
    private int currentStamina;
    private int nextStamina;
    private int count = 0;
    
    private long lastTimeUpdate;
    
    private boolean isStaminaUpdating;
    
    private FrameImage fullStaminaBarImage;
    
    public StaminaBar(float x, float y, ParticularObject obj, GameWorld gameWorld) {
        super(x, y, 248, 11, 0.1f,0, gameWorld);
        
        ownerStaminaBar = obj;
        
        currentStamina = nextStamina = obj.getStamina();
        
        fullStaminaBarImage = CacheDataLoader.getInstance().getFrameImage("stamina_bar");
        
        widthCurrent = (int) this.getWidth();
        
        maxStamina = obj.getStamina();

    }
    
    
    public void Update(long currentTime){
        
        setPosX(getGameWorld().camera.getPosX() + this.getWidth() / 2 + 25);
        setPosY(getGameWorld().camera.getPosY() + 60);
        
        
        if(lastTimeUpdate == 0){
            lastTimeUpdate = currentTime;
        }

        if(nextStamina != ownerStaminaBar.getStamina() && !isStaminaUpdating){
            nextStamina = ownerStaminaBar.getStamina();
            lastTimeUpdate = currentTime;
        }

        if(nextStamina < currentStamina){
            if(currentTime - lastTimeUpdate >= TimeToUpdate){
                currentStamina--;
                lastTimeUpdate = currentTime;
                widthCurrent -= (this.getWidth() / maxStamina);
            }
        }
        else if(nextStamina > currentStamina){
            if(currentTime - lastTimeUpdate >= TimeToUpdate){
                currentStamina++;
                lastTimeUpdate = currentTime;
                widthCurrent += (this.getWidth() / maxStamina);
            }
        }
        else{
                isStaminaUpdating = false;
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

        g2.drawImage(fullStaminaBarImage.getImage(), x - (fullStaminaBarImage.getImage().getWidth()/2), y - (fullStaminaBarImage.getImage().getHeight()/2) + 1, this.widthCurrent, fullStaminaBarImage.getImage().getHeight(), null);
        
    }

    @Override
    public void death() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
