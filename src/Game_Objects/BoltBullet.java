/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

import Effect.Animation;
import Effect.CacheDataLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Admin
 */
public class BoltBullet extends Bullet{
    
    private Animation boltForwardBulletAnim, boltBackBulletAnim;
    private Animation boltHitAnim;
    
    public BoltBullet(float x, float y, GameWorld gameWorld) {
        
        super(x, y, 120, 80, 0, 200, gameWorld);
        
        boltBackBulletAnim = CacheDataLoader.getInstance().getAnimation("bolt_bullet");
        boltForwardBulletAnim = CacheDataLoader.getInstance().getAnimation("bolt_bullet");
        boltForwardBulletAnim.flipAllImage();
        
        boltHitAnim = CacheDataLoader.getInstance().getAnimation("bolt_hits");

    }
    
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        
        Rectangle rect = getBoundForCollisionWithMap();
        
        /*rect.x = (int) getPosX() - 20;
        rect.y = (int) getPosY() - 20;
        rect.width = 110;
        rect.height = 60;*/
        
        return rect;
    }
    
    @Override
    public void Update(){
        
        switch(getState()){
        
            case ALIVE:
                super.Update();
                break;
                
            case DEATH:
                death();
                break;
                
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        
        switch(getState()){
        
            case ALIVE:
                if(getSpeedX() > 0){
                    boltForwardBulletAnim.Update(System.nanoTime());
                    boltForwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }else{
                    boltBackBulletAnim.Update(System.nanoTime());
                    boltBackBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
                break;
                
            case DEATH:
                
                if(getSpeedX() > 0){
                    boltHitAnim.Update(System.nanoTime());
                    boltHitAnim.draw((int) (getPosX() + (getWidth() / 2) - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }else{
                    boltHitAnim.Update(System.nanoTime());
                    boltHitAnim.draw((int) (getPosX() - (getWidth() / 2) - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
                break;
        
        }

        this.drawBoundForCollisionWithEnemy(g2);
    }

    @Override
    public void attack() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void death() {
        if(boltHitAnim.isLastFrame())
            setState(DELETE);
    }

}
