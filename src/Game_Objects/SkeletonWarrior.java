/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

import Effect.Animation;
import Effect.CacheDataLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class SkeletonWarrior extends Enemy {
    
    public static long TimeRiseOfDead = 5000 * 1000000;
    
    private Animation idleForwardAnim, idleBackAnim;
    private Animation walkForwardAnim, walkBackAnim;
    private Animation attackForwardAnim, attackBackAnim;
    private Animation deathForwardAnim, deathBackAnim;
    private Animation stunedForwardAnim, stunedBackAnim;
    
    private ArrayList<Item> bagItems; 
    
    private BladeAttackPath normalAttack;
    
    private long lastTimeAttack;
    private long lastTimeDeath;
    
    private int bloodDefault;
    private float heightDefault;
    private float posYLanding;

    private boolean isFallingDown;
    private boolean isRiseOfDead;
    
    public SkeletonWarrior(float x, float y, GameWorld gameWorld) {
        
        super(x, y, 70, 78, 1.0f, 100,500, gameWorld);
        
        bagItems = new ArrayList<Item>();
        
        setTimeForNoBeHurt(0);
        
        setDamage(50);
        
        setSpeedX(-1);
        
        setVisionRangeX(250);
        setVisionRangeY(20);
        
        setAttackRangeX(32);
        setAttackRangeY(20);
        
        setIsLocationKeeper(false);

        isFallingDown = true;
        
        isRiseOfDead = false;
        
        posYLanding = this.getPosY();
        
        bloodDefault = this.getBlood();
        
        heightDefault = this.getHeight();
        
        
        idleForwardAnim = CacheDataLoader.getInstance().getAnimation("skeleton_idle");
        idleBackAnim = CacheDataLoader.getInstance().getAnimation("skeleton_idle");
        idleBackAnim.flipAllImage();
        
        walkForwardAnim = CacheDataLoader.getInstance().getAnimation("skeleton_walk");
        walkBackAnim = CacheDataLoader.getInstance().getAnimation("skeleton_walk");
        walkBackAnim.flipAllImage();
        
        attackForwardAnim = CacheDataLoader.getInstance().getAnimation("skeleton_attack");
        attackBackAnim = CacheDataLoader.getInstance().getAnimation("skeleton_attack");
        attackBackAnim.flipAllImage();
        
        deathForwardAnim = CacheDataLoader.getInstance().getAnimation("skeleton_death");
        deathBackAnim = CacheDataLoader.getInstance().getAnimation("skeleton_death");
        deathBackAnim.flipAllImage();
        
        beHurtForwardAnim = CacheDataLoader.getInstance().getAnimation("skeleton_takehit");
        beHurtBackAnim = CacheDataLoader.getInstance().getAnimation("skeleton_takehit");
        beHurtBackAnim.flipAllImage();
        
        stunedForwardAnim = CacheDataLoader.getInstance().getAnimation("skeleton_stuned");
        stunedBackAnim = CacheDataLoader.getInstance().getAnimation("skeleton_stuned");
        stunedBackAnim.flipAllImage();
        
        normalAttack = new BladeAttackPath(0,false);
        normalAttack.add(new BladeAttack(getPosX() + getWidth() / 2, getPosY() - getHeight() / 2, 35, 20, 1.0f, 600 * 1000000, 100 * 1000000,10, this.getDamage(),false, getGameWorld()));
        normalAttack.add(new BladeAttack(getPosX() + getWidth() / 2, getPosY() + getHeight() / 2, 35, 20, 1.0f, 0, 100 * 1000000,10, this.getDamage(),false, getGameWorld()));
        normalAttack.getBladePoint(0).setTeamType(ENEMY_TEAM);
        normalAttack.getBladePoint(1).setTeamType(ENEMY_TEAM);
    }
    

    public void addItemToTheBag(Item item){
        bagItems.add(item);
    }
    
    
    @Override
    public void Update(){
        
        super.Update();
        
        switch(getState()){
            case DEATH -> {
                if(isFallingDown){
                    if(deathForwardAnim.isLastFrame() || deathBackAnim.isLastFrame()){
                        isFallingDown = false;
                        lastTimeDeath = System.nanoTime();
                        droppedDownItems();
                    }
                    else {
                        isFallingDown = true;
                    }

                    if(getDirection() == LEFT_DIR){
                        if(deathBackAnim.getIsChangedFrame()){
                            //System.out.println("back frame : " + deathBackAnim.getCurrentFrame());
                            this.setPosY(posYLanding + (heightDefault / 2) - (deathBackAnim.getCurrentImage().getHeight() / 2 ));
                            this.setHeight(deathBackAnim.getCurrentImage().getHeight());
                        }
                    }
                    else{
                        if(deathForwardAnim.getIsChangedFrame()){
                            //System.out.println("forward frame : " + deathForwardAnim.getCurrentFrame());
                            this.setPosY(posYLanding + (heightDefault / 2) - (deathForwardAnim.getCurrentImage().getHeight() / 2));
                            this.setHeight(deathForwardAnim.getCurrentImage().getHeight());
                        }
                    }
                }
                else{
                    if(!isRiseOfDead){
                        if(System.nanoTime() - lastTimeDeath >= TimeRiseOfDead){
                            if(getDirection() == LEFT_DIR){
                                deathBackAnim.reverseAnimation();
                            }
                            else{
                                deathForwardAnim.reverseAnimation();
                            }
                            isRiseOfDead = true;
                        }
                    }
                    else{

                        if(getDirection() == LEFT_DIR){
                            if(deathBackAnim.getIsChangedFrame()){

                                this.setPosY(posYLanding + (heightDefault / 2) - (deathBackAnim.getCurrentImage().getHeight() / 2 ));
                                //System.out.println("back frame : " + deathBackAnim.getCurrentImage().getHeight() + " y : " + this.getPosY() + " id : " + deathBackAnim.getCurrentFrame());
                                this.setHeight(deathBackAnim.getCurrentImage().getHeight());
                            }
                        }
                        else{
                            if(deathForwardAnim.getIsChangedFrame()){

                                this.setPosY(posYLanding + (heightDefault / 2) - (deathForwardAnim.getCurrentImage().getHeight() / 2));
                                //System.out.println("forward frame h : " + deathForwardAnim.getCurrentImage().getHeight() + " y : " + this.getPosY() + " id : " + deathForwardAnim.getCurrentFrame());
                                this.setHeight(deathForwardAnim.getCurrentImage().getHeight());
                            }
                        }

                        if(deathBackAnim.isLastFrame() || deathForwardAnim.isLastFrame()){
                            if(getDirection() == LEFT_DIR){
                                deathBackAnim.reverseAnimation();
                                deathBackAnim.reset();
                            }
                            else{
                                deathForwardAnim.reverseAnimation();
                                deathForwardAnim.reset();
                            }
                            isRiseOfDead = false;
                            isFallingDown = true;
                            setState(ALIVE);
                            setBlood(bloodDefault);
                            attackForwardAnim.reset();
                            attackBackAnim.reset();

                        }

                    }
                }
                return;
            }

            case NOBEHURT,ALIVE -> {
                if(getIsLanding()){
                    setIsLanding(false);
                    posYLanding = getPosY();
                    walkForwardAnim.reset();
                    walkBackAnim.reset();
                }


                if(isWithinAttackRange()){
                    setSpeedX(0);

                    if(System.nanoTime() - lastTimeAttack > 6000 * 1000000){
                        attack();
                        setIsWaitingAttack(false);
                    }
                    else{
                        if(System.nanoTime() - lastTimeAttack > 800 * 1000000){
                           setIsWaitingAttack(true);
                           attackForwardAnim.reset();
                           attackBackAnim.reset();
                        }
                        else{
                            normalAttack.Update(System.nanoTime());
                        }
                    }
                }
                else {
                    setIsWaitingAttack(true);
                    setIsAttacking(false);
                }
            }
        }

    }

    
    @Override
    public void attack() {

        normalAttack.resetBladePath(System.nanoTime());
        
        if(getDirection() == LEFT_DIR){

            normalAttack.getBladePoint(0).setPosX(getPosX() - getWidth() + 20);
            normalAttack.getBladePoint(0).setPosY(getPosY() - getWidth() / 2);
            
            normalAttack.getBladePoint(1).setPosX(getPosX() - getWidth() + 20);
            normalAttack.getBladePoint(1).setPosY(getPosY() + getWidth() / 2);

        }
        else{

            normalAttack.getBladePoint(0).setPosX(getPosX() + getWidth() - 20);
            normalAttack.getBladePoint(0).setPosY(getPosY() - getWidth() / 2);
            
            normalAttack.getBladePoint(1).setPosX(getPosX() + getWidth() - 20);
            normalAttack.getBladePoint(1).setPosY(getPosY() + getWidth() / 2);

        }
        setIsAttacking(true);
        lastTimeAttack = System.nanoTime();

    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle rect = getBoundForCollisionWithMap();
        rect.x += 10;
        rect.width -= 25;
        
        return rect;
    }
    

    @Override
    public void draw(Graphics2D g2) {
        switch(getState()){
            case ALIVE, NOBEHURT -> {
                if(!isObjectOutOfCameraView()){
                    if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1){
                        // plash...
                    }else{
                        //System.out.println("draw speed : " + getSpeedX());
                        if(getSpeedX() != 0){
                            if(getDirection() == LEFT_DIR){
                                walkBackAnim.Update(System.nanoTime());
                                walkBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                        (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                            }else{
                                walkForwardAnim.Update(System.nanoTime());
                                walkForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                                        (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                            }
                        }
                        else{
                            if(getDirection() == LEFT_DIR){

                                if(!getIsWaitingAttack()){
                                    if(getIsAttacking()){
                                        attackBackAnim.Update(System.nanoTime());
                                        attackBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                                (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                                    }
                                }
                                else{
                                    idleBackAnim.Update(System.nanoTime());
                                    idleBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                                }

                            }else{
                                if(!getIsWaitingAttack()){
                                    if(getIsAttacking()){
                                        attackForwardAnim.Update(System.nanoTime());
                                        attackForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                                (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                                    }
                                }
                                else{

                                    idleForwardAnim.Update(System.nanoTime());
                                    idleForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                                }

                            }
                        }
                    }

                    if(!getIsWaitingAttack()){
                        if(getIsAttacking()){
                            normalAttack.draw(g2);
                        }
                    }
                    
                    drawBoundForCollisionWithMap(g2);
                    drawBoundForCollisionWithEnemy(g2);
                    drawBoundForAttackRange(g2);
                    drawBoundForVisionRange(g2);
                }
            }
            case BEHURT -> {
                if(getDirection() == RIGHT_DIR){
                    beHurtForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }else{
                    beHurtBackAnim.setCurrentFrame(beHurtForwardAnim.getCurrentFrame());
                    beHurtBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
            }
            
            case DEATH -> {
                if(isFallingDown){
                    if(getDirection() == RIGHT_DIR){
                        deathForwardAnim.Update(System.nanoTime());
                        deathForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                    }else{
                        deathBackAnim.Update(System.nanoTime());
                        deathBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                    }
                }
                else {
                    
                    if(!isRiseOfDead){
                        if(getDirection() == RIGHT_DIR){
                            deathForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        }else{
                            deathBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        }
                    }
                    else {
                        if(getDirection() == RIGHT_DIR){
                            deathForwardAnim.Update(System.nanoTime());
                            deathForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        }else{
                            deathBackAnim.Update(System.nanoTime());
                            deathBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        }
                    }
                }
                drawBoundForCollisionWithMap(g2);
                drawBoundForCollisionWithEnemy(g2);
            }
            
            case STUNED -> {
                if (getDirection() == RIGHT_DIR){
                    stunedForwardAnim.Update(System.nanoTime());
                    stunedForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
                else {
                    stunedForwardAnim.Update(System.nanoTime());
                    stunedForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
                drawBoundForCollisionWithMap(g2);
                drawBoundForCollisionWithEnemy(g2);
            }
                
        }
        
    }

    @Override
    public void death() {
        System.out.println("death");
    }
    
    private void droppedDownItems(){
        
        if(this.bagItems.isEmpty())
            return;
        
        for(Item item: this.bagItems){
           item.setPosY(this.posYLanding + (this.heightDefault / 2) - item.getHeight() - 30);
           item.setPosX(this.getPosX());
           this.getGameWorld().particularItemManager.addObject(item);
        }    
        this.bagItems.clear();
        
    }
    
}
