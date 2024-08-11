/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

import Effect.Animation;
import Effect.CacheDataLoader;
import java.awt.Graphics2D;


/**
 *
 * @author Admin
 */
public class KeyItem extends Item{
    
    private Animation idleKeyAnim;

    public KeyItem(float x, float y, GameWorld gameWorld) {
        super(x, y, 14, 29, 1.0f, 100, "key001", gameWorld);
        
        idleKeyAnim = CacheDataLoader.getInstance().getAnimation("key_blue");
    }
    
    
    @Override
    public void Update(){
        
        super.Update();
        
        if(getIsLanding()){
            setIsLanding(false);
        }
        
    }

    @Override
    public void draw(Graphics2D g2){
        if(!this.getIsInTheBag()){
            idleKeyAnim.Update(System.nanoTime());
            idleKeyAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
        
        this.drawBoundForCollisionWithEnemy(g2);
    }

}
