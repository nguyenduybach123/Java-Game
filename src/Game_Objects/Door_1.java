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
public class Door_1 extends ParticularObject {
    
    FrameImage doorImage;
    
    private String keyCode;
    
    private boolean isPassDoor;
    
    public Door_1(float x, float y, GameWorld gameWorld) {
        super(x, y, 98, 136, 0, 0, gameWorld);
        
        setTeamType(NEUTRAL_TEAM);
        
        keyCode = "key001";
        
        doorImage = CacheDataLoader.getInstance().getFrameImage("door_1");

    }

    public boolean getIsPassDoor() {
        return isPassDoor;
    }

    public void setIsPassDoor(boolean isPassDoor) {
        this.isPassDoor = isPassDoor;
    }

    public boolean isCanOpenDoor(){
        if(getGameWorld().dk.getBoundForCollisionWithEnemy().intersects(this.getBoundForCollisionWithEnemy()) && getGameWorld().dk.getIsOpenDoor()){            
            return true;
        }
        return false;
    }
    
    @Override
    public void Update(){
        
        if(isCanOpenDoor()){
            if(getGameWorld().dk.getItemInTheBag(keyCode) != null){
                System.out.println("next map");
                getGameWorld().dk.closeTheDoor();
            }
            else{
                System.out.println("you need the key");
                getGameWorld().dk.closeTheDoor();
            }    
        }
    }
    
    @Override
    public void attack() {}

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        
        Rectangle rect = getBoundForCollisionWithMap();
        
        rect.x = (int) getPosX() - (int)(this.getWidth() / 2) + 20;
        rect.y = (int) getPosY() - (int)(this.getHeight() / 2) + 20;
        rect.width = (int) this.getWidth() - 40;
        rect.height = (int) this.getHeight() - 20;
        
        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {
        
        int x = (int)getPosX() - (int)getGameWorld().camera.getPosX();
        
        int y = (int)getPosY() - (int)getGameWorld().camera.getPosY();
        
        g2.drawImage(doorImage.getImage(), x - doorImage.getImage().getWidth()/2, y - doorImage.getImage().getHeight()/2, null);
        
        this.drawBoundForCollisionWithEnemy(g2);

    }

    @Override
    public void death() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
