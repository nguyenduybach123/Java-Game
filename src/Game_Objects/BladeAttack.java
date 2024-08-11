/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Admin
 */
public class BladeAttack extends MeleeAttack {
    
    private boolean isTeleportable;
    
    private long delayTime;
    private long pathTime;
    private int speedAttack;
    
    public BladeAttack(float x, float y, float width, float height, float mass, long delayTime, long pathTime, int speedAttack, int damage, boolean isTeleportable, GameWorld gameWorld) {
        super(x, y, width, height, mass, damage, gameWorld);
        
        this.isTeleportable = isTeleportable;
        
        this.delayTime = delayTime;
        
        this.pathTime = pathTime;
        
        this.speedAttack = speedAttack;

    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    public long getPathTime() {
        return pathTime;
    }

    public void setPathTime(long pathTime) {
        this.pathTime = pathTime;
    }

    public int getSpeedAttack() {
        return speedAttack;
    }

    public void setSpeedAttack(int speedAttack) {
        this.speedAttack = speedAttack;
    }

    public boolean isIsTeleportable() {
        return isTeleportable;
    }

    public void setIsTeleportable(boolean isTeleportable) {
        this.isTeleportable = isTeleportable;
    }
    
    
    
    @Override
    public void attack() {}

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
         return getBoundForCollisionWithMap();
    }
    
    @Override
    public void Update(){
        
        super.Update();
        
        if(!isTeleportable){
            setPosX(getPosX() + getSpeedX());
            setPosY(getPosY() + getSpeedY());
        }

        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if(object!=null && object.getState() == ALIVE){
            setBlood(0);
            object.beHurt(getDamage());
            //System.out.println("Blade set behurt for enemy");
        }
    }
    
    
    @Override
    public void draw(Graphics2D g2) {
        Rectangle rect = getBoundForCollisionWithMap();
        //Color myColour = new Color(255, 0, 0, 0);
        g2.setColor(Color.BLUE);
        g2.fillRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    }

    @Override
    public void death() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
