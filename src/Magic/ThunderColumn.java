/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Magic;

import Effect.Animation;
import Effect.CacheDataLoader;
import Game_Objects.GameWorld;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Admin
 */
public class ThunderColumn extends Spell {

    public static final long TIME_TO_MARK = 1000000000;
    private int tileSize;
    
    private Animation thunderMarkAnim;
    private Animation thunderColumnSpellAnim;
    private Animation thunderExplodeAnim;
    
    private int lastPosX, lastPosY;
    
    private boolean isThunderMark;
    private boolean isThunderActive;
    private boolean isFinishToTheMark;
    
    private long lastTimeToMark;
    
    public ThunderColumn(int startPosX, GameWorld gameWorld) {
        
        super(startPosX, 0, gameWorld);
        
        lastPosX = startPosX;
        
        lastPosY = 0;
        
        isThunderActive = false;
        
        isThunderMark = false;
        
        isFinishToTheMark = false;
        
        lastTimeToMark = 0;
        
        tileSize = gameWorld.physicalMap.getTileSize();

        thunderColumnSpellAnim = CacheDataLoader.getInstance().getAnimation("thunder_column");
        
        thunderMarkAnim = CacheDataLoader.getInstance().getAnimation("thunder_mark");
        
        thunderExplodeAnim = CacheDataLoader.getInstance().getAnimation("thunder_strike");
        
        setDamage(150);
        
        setState(NOBEHURT);
        
        setTypeSpell(ATTACK_SPELL);
        
    }
    
    public int getLastPosX() {
        return lastPosX;
    }

    public void setLastPosX(int lastPosX) {
        this.lastPosX = lastPosX;
    }

    public int getLastPosY() {
        return lastPosY;
    }

    public void setLastPosY(int lastPosY) {
        this.lastPosY = lastPosY;
    }

    
    @Override
    public void Update() {
        super.Update();  
        if(isThunderActive){
            thunderHitToMarkPos();
        }
        else{
            if(getLastPosY() == 0){
                determineMarkPos();
            }
            else {
                if(lastTimeToMark == 0){
                    lastTimeToMark = System.nanoTime();
                }
                
                if(System.nanoTime() - lastTimeToMark > TIME_TO_MARK){
                    setState(EXIST);
                    isThunderActive = true;
                }
            }
        }
    }
    
    @Override
    public void draw(Graphics2D g2) {
        if(isThunderActive){
            if(!isFinishToTheMark){
                thunderColumnSpellAnim.Update(System.nanoTime());
                thunderColumnSpellAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }
            else{
                thunderExplodeAnim.Update(System.nanoTime());
                thunderExplodeAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }
            
            drawBoundForCollisionWithEnemy(g2);
        }
        else{
            if(getLastPosY() != 0){
                thunderMarkAnim.Update(System.nanoTime());
                thunderMarkAnim.draw((int) (getLastPosX() - getGameWorld().camera.getPosX()), (int) getLastPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }
        }
    }

    private void thunderHitToMarkPos(){
        if(!isFinishToTheMark){
            if(thunderColumnSpellAnim.isLastFrame()){
                if(getPosY() + thunderColumnSpellAnim.getCurrentImage().getHeight() / 2 != lastPosY + thunderMarkAnim.getCurrentImage().getHeight() / 2){
                    setPosY(getPosY() + thunderColumnSpellAnim.getCurrentImage().getHeight());
                }
                else{
                    setPosY((lastPosY + thunderMarkAnim.getCurrentImage().getHeight() / 2) - thunderExplodeAnim.getCurrentImage().getHeight() / 2);
                    isFinishToTheMark = true;
                }
            }
        }
        else{
            if(thunderExplodeAnim.isLastFrame()){
                setState(Spell.END);
            }
        }
    }
    
    private void determineMarkPos(){
        if(!getGameWorld().dk.getIsLanding() && !getGameWorld().dk.getIsJumping() && getGameWorld().dk.getSpeedY() == 0){
            float distancePosY = ((getGameWorld().dk.getPosY() + getGameWorld().dk.getHeight() / 2) - thunderColumnSpellAnim.getCurrentImage().getHeight() / 2) - thunderColumnSpellAnim.getCurrentImage().getHeight();
            setPosY(distancePosY);
            
            float distanceLastPosY = (getGameWorld().dk.getPosY() + getGameWorld().dk.getHeight() / 2) - thunderMarkAnim.getCurrentImage().getHeight() / 2;
            setLastPosY((int) distanceLastPosY);
        }    
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        
        Rectangle rectCollision = new Rectangle();

        if(isFinishToTheMark){
            rectCollision.x = (int) getPosX() - thunderExplodeAnim.getCurrentImage().getWidth() / 2;
            rectCollision.y = (int) getPosY() - thunderExplodeAnim.getCurrentImage().getHeight() / 2;
            
            rectCollision.width = thunderExplodeAnim.getCurrentImage().getWidth();
            rectCollision.height = thunderExplodeAnim.getCurrentImage().getHeight();
        }
        else if(isThunderActive){
            rectCollision.x = (int) getPosX() - thunderColumnSpellAnim.getCurrentImage().getWidth() / 2;
            rectCollision.y = (int) getPosY() - thunderColumnSpellAnim.getCurrentImage().getHeight() / 2;
            
            rectCollision.width = thunderColumnSpellAnim.getCurrentImage().getWidth();
            rectCollision.height = thunderColumnSpellAnim.getCurrentImage().getHeight();
        }
        
        return rectCollision;
    }
    
    
    
}
