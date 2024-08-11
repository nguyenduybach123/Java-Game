/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

import Effect.Animation;
import Effect.CacheDataLoader;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class DarkKnight extends Human {
    
    public static final int RUNSPEED = 3;
    public static final long TIMEREPRESSED = 500 * 1000000;
    
    private Animation runForwardAnim, runBackAnim, runShootingForwarAnim, runShootingBackAnim;
    private Animation idleForwardAnim, idleBackAnim, idleShootingForwardAnim, idleShootingBackAnim;
    private Animation dickForwardAnim, dickBackAnim;
    private Animation flyForwardAnim, flyBackAnim, flyShootingForwardAnim, flyShootingBackAnim;
    private Animation landingForwardAnim, landingBackAnim;
    private Animation idleBladeWeakAttackAnim, idleBladeWeakAttackBackAnim, idleBladeStrongAttackAnim, idleBladeStrongAttackBackAnim;
    private Animation idleStabAttackAnim, idleStabAttackBackAnim, idleWindBladeAttackAnim, idleWindBladeAttackBackAnim;
    private Animation idleForwardRepressedAnim, idleBackRepressedAnim;
    private Animation idleHealingAnim, idleHealingBackAnim;
    private Animation slideForwardAnim, slideBackAnim;
    private Animation flyingAttackAnim;
    private Animation climWallForward, climWallBack;
    
    private ArrayList<Item> bagItems;
    
    private int comboAttackCurrent = 1;
    private int comboAttackNext = 1;
    private int healingPotions = 3;
    private int maximumHealth;
    private int maximumStamina;
    
    private MeleeAttack bladeAttack;
    private BladeAttackPath normalAttack_1;
    private BladeAttackPath normalAttack_2;   
    private BladeAttackPath normalAttack_3;
    private BladeAttackPath bladeWindAttack;
    private BladeAttackPath flyAttack;
    

    private boolean isAttacking = false;
    private boolean isRepressedAttack = false;
    private boolean isCountTimeRepressed = true;
    private boolean isFlyingAttack = false;
    private boolean isFlyingAttackLanding = false;
    private boolean isWindyAttack = false;
    private boolean isSlide = false;
    private boolean isHealing = false;
    private boolean isOpenDoor = false;
    private boolean isPickingUpItem = false;
    
    private long lastTimeAttack;
    private long lastTimeRepressedAttack;
    private long lastTimeFlyingAttack;
    private long timeIsFlyingAttackLanding;
    private long isLastTimeSlide;
    private long isLastTimeHealing;
    private long isLastTimeStaminaling;
    private long lastTimePickingUpItem;

    private AudioClip hurtingSound;
    private AudioClip shooting1;
    
    public DarkKnight(float x, float y, GameWorld gameWorld) {
        
        super(x, y, 50, 84, 0.1f, 240, gameWorld);

        
        bladeAttack = new BladeAttack(getPosX(),getPosY(),50,82,1.0f,0,0,10,100,false,getGameWorld());
        
        bagItems = new ArrayList<Item>();
        
        setTeamType(LEAGUE_TEAM);

        setTimeForNoBeHurt(2000*1000000);
        
        setStamina(248);
        
        setRegenStamina(5);
        
        setTimeRegenStamina(500 * 1000000);
        
        maximumHealth = getBlood();
        
        maximumStamina = getStamina();

        shooting1 = CacheDataLoader.getInstance().getSound("bluefireshooting");
        hurtingSound = CacheDataLoader.getInstance().getSound("megamanhurt");
        
        runForwardAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAnim.flipAllImage();  
        
        idleForwardAnim = CacheDataLoader.getInstance().getAnimation("idle");
        idleBackAnim = CacheDataLoader.getInstance().getAnimation("idle");
        idleBackAnim.flipAllImage();
        
        dickForwardAnim = CacheDataLoader.getInstance().getAnimation("crouch");
        dickBackAnim = CacheDataLoader.getInstance().getAnimation("crouch");
        dickBackAnim.flipAllImage();
        
        flyForwardAnim = CacheDataLoader.getInstance().getAnimation("jump");
        flyForwardAnim.setIsRepeated(false);
        flyBackAnim = CacheDataLoader.getInstance().getAnimation("jump");
        flyBackAnim.setIsRepeated(false);
        flyBackAnim.flipAllImage();
        
        landingForwardAnim = CacheDataLoader.getInstance().getAnimation("landing");
        landingBackAnim = CacheDataLoader.getInstance().getAnimation("landing");
        landingBackAnim.flipAllImage();
        
        idleBladeWeakAttackAnim = CacheDataLoader.getInstance().getAnimation("blade_weak_attack");
        idleBladeWeakAttackBackAnim = CacheDataLoader.getInstance().getAnimation("blade_weak_attack");
        idleBladeWeakAttackBackAnim.flipAllImage();
        
        idleBladeStrongAttackAnim = CacheDataLoader.getInstance().getAnimation("blade_strong_attack");
        idleBladeStrongAttackBackAnim = CacheDataLoader.getInstance().getAnimation("blade_strong_attack");
        idleBladeStrongAttackBackAnim.flipAllImage();
        
        idleStabAttackAnim = CacheDataLoader.getInstance().getAnimation("stab_attack");
        idleStabAttackBackAnim = CacheDataLoader.getInstance().getAnimation("stab_attack");
        idleStabAttackBackAnim.flipAllImage();
        
        idleWindBladeAttackAnim = CacheDataLoader.getInstance().getAnimation("wind_blade_attack");
        idleWindBladeAttackBackAnim = CacheDataLoader.getInstance().getAnimation("wind_blade_attack");
        idleWindBladeAttackBackAnim.flipAllImage();
        
        idleForwardRepressedAnim = CacheDataLoader.getInstance().getAnimation("repressed");
        idleBackRepressedAnim = CacheDataLoader.getInstance().getAnimation("repressed");
        idleBackRepressedAnim.flipAllImage();
        
        idleHealingAnim = CacheDataLoader.getInstance().getAnimation("healing");
        idleHealingBackAnim = CacheDataLoader.getInstance().getAnimation("healing");
        idleHealingBackAnim.flipAllImage();
        
        slideForwardAnim = CacheDataLoader.getInstance().getAnimation("slide");
        slideBackAnim = CacheDataLoader.getInstance().getAnimation("slide");
        slideBackAnim.flipAllImage();
        
        flyingAttackAnim = CacheDataLoader.getInstance().getAnimation("air_attack");
        
        flyingAttackAnim.setIgnoreFrame(3);
        flyingAttackAnim.setIgnoreFrame(4);
        flyingAttackAnim.setIgnoreFrame(5);
        flyingAttackAnim.setIgnoreFrame(6);
        
        beHurtForwardAnim = CacheDataLoader.getInstance().getAnimation("hurt");
        beHurtBackAnim = CacheDataLoader.getInstance().getAnimation("hurt");
        beHurtBackAnim.flipAllImage();
        
        normalAttack_1 = new BladeAttackPath(0,false);
        normalAttack_1.add(new BladeAttack(getPosX() + getWidth() / 2, getPosY() - getHeight() / 2, 20, 20, 1.0f, 170 * 1000000, 100 * 1000000,10, 30,false, getGameWorld()));
        normalAttack_1.add(new BladeAttack(getPosX() + getWidth() / 2, getPosY() + getHeight() / 2, 20, 20, 1.0f, 0, 100 * 1000000,10, 40,false, getGameWorld()));
        normalAttack_1.getBladePoint(0).setTeamType(LEAGUE_TEAM);
        normalAttack_1.getBladePoint(1).setTeamType(LEAGUE_TEAM);
        
        normalAttack_2 = new BladeAttackPath(0,false);
        normalAttack_2.add(new BladeAttack(getPosX() + getWidth() / 2, getPosY() - getHeight() / 2, 30, 20, 1.0f, 70 * 1000000, 140 * 1000000,10, 40,false, getGameWorld()));
        normalAttack_2.add(new BladeAttack(getPosX() + getWidth() / 2, getPosY() + getHeight() / 2, 30, 20, 1.0f, 0, 70 * 1000000,10, 50,false, getGameWorld()));
        normalAttack_2.getBladePoint(0).setTeamType(LEAGUE_TEAM);
        normalAttack_2.getBladePoint(1).setTeamType(LEAGUE_TEAM);
        
        
        normalAttack_3 = new BladeAttackPath(0,false);
        normalAttack_3.add(new BladeAttack(getPosX() + getWidth() / 2, getPosY() - getHeight() / 2, 20, 20, 1.0f, 50 * 1000000, 130 * 1000000,10, 40,false, getGameWorld()));
        normalAttack_3.add(new BladeAttack(getPosX() + getWidth() / 2, getPosY() + getHeight() / 2, 20, 20, 1.0f, 0, 100 * 1000000,10, 80,false, getGameWorld()));
        normalAttack_3.getBladePoint(0).setTeamType(LEAGUE_TEAM);
        normalAttack_3.getBladePoint(1).setTeamType(LEAGUE_TEAM);
        
        
        bladeWindAttack = new BladeAttackPath(0,false);
        bladeWindAttack.add(new BladeAttack(getPosX() + getWidth() / 2, getPosY() - getHeight() / 2, 30, getHeight(), 1.0f, 200 * 1000000, 270 * 1000000,10, 70,false, getGameWorld()));
        bladeWindAttack.add(new BladeAttack(getPosX() + getWidth() / 2, getPosY() + getHeight() / 2, 30, getHeight(), 1.0f, 0, 170 * 1000000,15, 150,false, getGameWorld()));
        bladeWindAttack.getBladePoint(0).setTeamType(LEAGUE_TEAM);
        bladeWindAttack.getBladePoint(1).setTeamType(LEAGUE_TEAM);
        
        
        flyAttack = new BladeAttackPath(0,false);
        flyAttack.add(new BladeAttack(getPosX() , getPosY() + getHeight() / 2, 60, 30, 1.0f, 100 * 1000000, 480 * 1000000,10, 50,false, getGameWorld()));
        flyAttack.getBladePoint(1).setTeamType(LEAGUE_TEAM);
        
        /*climWallBack = CacheDataLoader.getInstance().getAnimation("clim_wall");
        climWallForward = CacheDataLoader.getInstance().getAnimation("clim_wall");
        climWallForward.flipAllImage();
        
        beHurtForwardAnim = CacheDataLoader.getInstance().getAnimation("hurting");
        beHurtBackAnim = CacheDataLoader.getInstance().getAnimation("hurting");
        beHurtBackAnim.flipAllImage();
        
        idleShootingForwardAnim = CacheDataLoader.getInstance().getAnimation("idleshoot");
        idleShootingBackAnim = CacheDataLoader.getInstance().getAnimation("idleshoot");
        idleShootingBackAnim.flipAllImage();
        
        runShootingForwarAnim = CacheDataLoader.getInstance().getAnimation("runshoot");
        runShootingBackAnim = CacheDataLoader.getInstance().getAnimation("runshoot");
        runShootingBackAnim.flipAllImage();
        
        flyShootingForwardAnim = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
        flyShootingBackAnim = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
        flyShootingBackAnim.flipAllImage();*/
        
    }

    public boolean getIsOpenDoor() {
        return isOpenDoor;
    }
    

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        // TODO Auto-generated method stub
        Rectangle rect = getBoundForCollisionWithMap();
        
        if(getIsDicking()){
            
            if(getDirection() == LEFT_DIR) {
                rect.x = (int) getPosX() - 32;
                rect.y = (int) getPosY() - 22;
                rect.width = 44;
                rect.height = 65;
            }
            else {
                rect.x = (int) getPosX() - 13;
                rect.y = (int) getPosY() - 22;
                rect.width = 44;
                rect.height = 65;
            }
            
        }
        else if(isSlide){
            if(getDirection() == LEFT_DIR) {
                rect.x = (int) getPosX() - slideBackAnim.getCurrentImage().getWidth() / 2;
                rect.y = (int) getPosY();
                rect.width = slideBackAnim.getCurrentImage().getWidth();
                rect.height = slideBackAnim.getCurrentImage().getHeight();
            }
            else {
                rect.x = (int) getPosX() - slideForwardAnim.getCurrentImage().getWidth() / 2;
                rect.y = (int) getPosY();
                rect.width = slideForwardAnim.getCurrentImage().getWidth();
                rect.height = slideForwardAnim.getCurrentImage().getHeight();
            }
        }
        else{
            if(getDirection() == LEFT_DIR) {
                rect.x = (int) getPosX() - 24;
                rect.y = (int) getPosY() - 40;
                rect.width = 40;
                rect.height = 82;
            }
            else {
                rect.x = (int) getPosX() - 17;
                rect.y = (int) getPosY() - 40;
                rect.width = 42;
                rect.height = 82;
            }
        }
        
        return rect;
    }

    @Override
    public void Update() {

        super.Update();
        
        
        
        if(isAttacking){
            
            if(isWindyAttack){
                
                if(System.nanoTime() - lastTimeAttack >= 600 * 1000000){
                    isWindyAttack = false;
                    isAttacking = false;
                }
                else{
                    bladeWindAttack.Update(System.nanoTime());
                }
            }
            else{
                switch(comboAttackCurrent){
                    case 1:
                        if(System.nanoTime() - lastTimeAttack >= 500 * 1000000){
                            isAttacking = false;
                        }
                        else{
                            normalAttack_1.Update(System.nanoTime());
                        }
                        break;

                    case 2:
                        if(System.nanoTime() - lastTimeAttack >= 500 * 1000000){
                            isAttacking = false;
                        }
                        else{
                            normalAttack_2.Update(System.nanoTime());
                        }
                        break;

                    case 3:
                        if(System.nanoTime() - lastTimeAttack >= 500 * 1000000){
                            isAttacking = false;
                        }
                        else{
                            normalAttack_3.Update(System.nanoTime());
                        }
                        break;

                }
            }
        }
        
        if(isFlyingAttack){
            if(getSpeedY() == 0){
                isFlyingAttackLanding = true;
                isFlyingAttack = false;
                flyingAttackAnim.reset();
                
                flyingAttackAnim.unIgnoreFrame(3);
                flyingAttackAnim.unIgnoreFrame(4);
                flyingAttackAnim.unIgnoreFrame(5);
                flyingAttackAnim.unIgnoreFrame(6);
            }
            else{
                /*if(System.nanoTime() - lastTimeFlyingAttack > timeDrop){
                    setSpeedY(getSpeedY() + (int)(timeDrop / 100000000));
                    timeDrop += 100 * 1000000; 
                }*/
                if(getSpeedY() < 30 ){
                    setSpeedY(getSpeedY() + 1);
                }
                
                if(System.nanoTime() - lastTimeFlyingAttack > 100 * 1000000){
                    if(flyAttack.getBladePoint(0).getWidth() < 150){
                        flyAttack.getBladePoint(0).setWidth(flyAttack.getBladePoint(0).getWidth() + 20);
                        flyAttack.getBladePoint(0).setHeight(flyAttack.getBladePoint(0).getHeight() + 10);
                        lastTimeFlyingAttack = System.nanoTime();
                    }
                }
                
                flyAttack.getBladePoint(0).setPosX(getPosX());
                flyAttack.getBladePoint(0).setPosY(getPosY() + getHeight() / 2);
                flyAttack.Update(System.nanoTime());
            }
        }
        
        
        if(isHealing){
            if(System.nanoTime() - isLastTimeHealing > 800 * 1000000){
                System.out.println("healingTime");
                isHealing = false;
            }
        }

        // Regen stamina
        if(System.nanoTime() - isLastTimeStaminaling >= this.getTimeRegenStamina() && getStamina() < maximumStamina){
            if(isLastTimeStaminaling == 0){
                isLastTimeStaminaling = System.nanoTime();
            }
            else{
                setStamina(getStamina() + getRegenStamina());
                isLastTimeStaminaling = System.nanoTime();
            }
        }
        
        
        if(isSlide){
            if(System.nanoTime() - isLastTimeSlide > 720 * 1000000){
                isSlide = false;
                setSpeedX(0);
                slideForwardAnim.reset();
                slideBackAnim.reset();
                runForwardAnim.reset();
                runBackAnim.reset();
            }
        }
        
        
        if(getIsLanding()){
            landingBackAnim.Update(System.nanoTime());
            timeIsFlyingAttackLanding = System.nanoTime();
            if(landingBackAnim.isLastFrame()) {
                setIsLanding(false);
                landingBackAnim.reset();
                runForwardAnim.reset();
                runBackAnim.reset();
            }
        }
        
        if(isFlyingAttackLanding){
            if(System.nanoTime() - timeIsFlyingAttackLanding > 280 * 1000000){
                isFlyingAttackLanding = false;
            }
        }
        
        if(isPickingUpItem){
            if(System.nanoTime() - lastTimePickingUpItem >= 200 * 1000000){
                setIsDicking(false);
                isPickingUpItem = false;
            }
        }

        
    }


    @Override
    public void draw(Graphics2D g2) {

        switch(getState()){
        
            case ALIVE:
            case NOBEHURT:
                if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1)
                {
                    //System.out.println("Plash...");
                }else{
                    if(getIsLanding()){
                        if(isFlyingAttackLanding){
                            if(flyingAttackAnim.getCurrentFrame() >= 0 && flyingAttackAnim.getCurrentFrame() <= 2) flyingAttackAnim.setCurrentFrame(3);
                            flyingAttackAnim.Update(System.nanoTime());
                            flyingAttackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        }
                        else{
                            if(getDirection() == RIGHT_DIR){
                                landingForwardAnim.setCurrentFrame(landingBackAnim.getCurrentFrame());
                                landingForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height/2 - landingForwardAnim.getCurrentImage().getHeight()/2),
                                    g2);
                            }
                            else{
                                landingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                                        (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height/2 - landingBackAnim.getCurrentImage().getHeight()/2),
                                        g2);
                            }
                        }
                        
                    }else if(getIsJumping()){

                        if(getDirection() == RIGHT_DIR){
                            flyForwardAnim.Update(System.nanoTime());
                            if(isFlyingAttack){
                                System.out.println(flyingAttackAnim.getCurrentFrame());
                                flyingAttackAnim.Update(System.nanoTime());
                                flyingAttackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else
                                flyForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        }else{
                            flyBackAnim.Update(System.nanoTime());
                            if(isFlyingAttack){
                                System.out.println(flyingAttackAnim.getCurrentFrame());
                                flyingAttackAnim.Update(System.nanoTime());
                                flyingAttackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else
                            flyBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        }

                    }else if(getIsDicking()){

                        if(getDirection() == RIGHT_DIR){

                            dickForwardAnim.Update(System.nanoTime());
                            dickForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height/2 - dickForwardAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }else{
                            dickBackAnim.Update(System.nanoTime());
                            dickBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height/2 - dickBackAnim.getCurrentImage().getHeight()/2),
                                    g2);
                        }

                    }else{
                        if(getSpeedX() > 0){
                            runForwardAnim.Update(System.nanoTime());
                            if(isSlide){
                                if(slideBackAnim.getCurrentFrame() == 1 || slideBackAnim.getCurrentFrame() == 2 || slideBackAnim.getCurrentFrame() == 9){
                                    
                                    slideForwardAnim.Update(System.nanoTime());
                                    slideForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height/2 - dickBackAnim.getCurrentImage().getHeight()/2),
                                                g2);
                                }
                                else {
                                    slideForwardAnim.Update(System.nanoTime());
                                    slideForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height - dickForwardAnim.getCurrentImage().getHeight() + 5)
                                            , g2);
                                }
                            }else
                                runForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            if(runForwardAnim.getCurrentFrame() == 1) runForwardAnim.setIgnoreFrame(0);
                        }else if(getSpeedX() < 0){
                            runBackAnim.Update(System.nanoTime());
                            if(isSlide){
                                if(slideBackAnim.getCurrentFrame() == 1 || slideBackAnim.getCurrentFrame() == 2 || slideBackAnim.getCurrentFrame() == 9){
                                    
                                    slideBackAnim.Update(System.nanoTime());
                                    slideBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height/2 - dickBackAnim.getCurrentImage().getHeight()/2),
                                                g2);
                                }
                                else {
                                    slideBackAnim.Update(System.nanoTime());
                                    slideBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getBoundForCollisionWithMap().height - dickForwardAnim.getCurrentImage().getHeight() + 5)
                                            , g2);
                                }
                            }else
                                runBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            if(runBackAnim.getCurrentFrame() == 1) runBackAnim.setIgnoreFrame(0);
                        }else{
                            if(getDirection() == RIGHT_DIR){
                                if(isAttacking){
                                    
                                    if(isWindyAttack){
                                        idleWindBladeAttackAnim.Update(System.nanoTime());
                                        idleWindBladeAttackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                    }
                                    else{
                                        switch(comboAttackCurrent){
                                            case 1:
                                                idleBladeWeakAttackAnim.Update(System.nanoTime());
                                                idleBladeWeakAttackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                                break;
                                            case 2:
                                                idleBladeStrongAttackAnim.Update(System.nanoTime());
                                                idleBladeStrongAttackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                                break;
                                            case 3:
                                                idleStabAttackAnim.Update(System.nanoTime());
                                                idleStabAttackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                                break;
                                        }
                                    }
                                }
                                else if(isHealing){
                                    idleHealingAnim.Update(System.nanoTime());
                                    idleHealingAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                                else if(isRepressedAttack){
                                    idleForwardRepressedAnim.Update(System.nanoTime());
                                    idleForwardRepressedAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                                else{
                                    idleForwardAnim.Update(System.nanoTime());
                                    idleForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                            }else{
                                if(isAttacking){
                                    
                                    if(isWindyAttack){
                                        idleWindBladeAttackBackAnim.Update(System.nanoTime());
                                        idleWindBladeAttackBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                    }
                                    else{
                                        switch(comboAttackCurrent){
                                            case 1:
                                                idleBladeWeakAttackBackAnim.Update(System.nanoTime());
                                                idleBladeWeakAttackBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                                break;
                                            case 2:
                                                idleBladeStrongAttackBackAnim.Update(System.nanoTime());
                                                idleBladeStrongAttackBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                                break;
                                            case 3:
                                                idleStabAttackBackAnim.Update(System.nanoTime());
                                                idleStabAttackBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                                break; 
                                        }
                                    }
                                }
                                else if(isHealing){
                                    idleHealingBackAnim.Update(System.nanoTime());
                                    idleHealingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                                else if(isRepressedAttack){
                                    idleBackRepressedAnim.Update(System.nanoTime());
                                    idleBackRepressedAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);                          
                                }
                                else{
                                    idleBackAnim.Update(System.nanoTime());
                                    idleBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                            }
                        }            
                    }
                }
                
                break;
            
            case BEHURT:
                if(getDirection() == RIGHT_DIR){
                    beHurtForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }else{
                    beHurtBackAnim.setCurrentFrame(beHurtForwardAnim.getCurrentFrame());
                    beHurtBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
                break;
             
            case FEY:
                
                break;

        }
        
        g2.setColor(Color.RED);
        g2.fillRect((int)getPosX() - (int) getGameWorld().camera.getPosX(), (int)getPosY() - (int) getGameWorld().camera.getPosY(), 2, 2);
        
        if(isAttacking || isFlyingAttack){
            
            if(isFlyingAttack){
                flyAttack.draw(g2);
            }
            else if(isWindyAttack){
                
                bladeWindAttack.draw(g2);
            }
            else{
                switch(comboAttackCurrent){
                    case 1:
                        normalAttack_1.draw(g2);
                        break;
                    case 2:
                        normalAttack_2.draw(g2);
                        break;
                    case 3:
                        normalAttack_3.draw(g2);
                        break;
                }
            }
        }
        
        drawBoundForCollisionWithMap(g2);
        drawBoundForCollisionWithEnemy(g2);
    }

    @Override
    public void run() {
        
        if(!getIsDicking() && !isSlide && !isAttacking){
            if(getDirection() == LEFT_DIR)
                setSpeedX(-3);
            else setSpeedX(3);
        }
        
    }

    @Override
    public void jump() {

        if(!getIsJumping() && !isSlide){
            setIsJumping(true);
            setSpeedY(-5.0f);           
            flyBackAnim.reset();
            flyForwardAnim.reset();
        }
        // for clim wall
        else{
            Rectangle rectRightWall = getBoundForCollisionWithMap();
            rectRightWall.x += 1;
            Rectangle rectLeftWall = getBoundForCollisionWithMap();
            rectLeftWall.x -= 1;
            
            if(getGameWorld().physicalMap.haveCollisionWithRightWall(rectRightWall)!=null && getSpeedX() > 0){
                setSpeedY(-5.0f);
                //setSpeedX(-1);
                flyBackAnim.reset();
                flyForwardAnim.reset();
                //setDirection(LEFT_DIR);
            }else if(getGameWorld().physicalMap.haveCollisionWithLeftWall(rectLeftWall)!=null && getSpeedX() < 0){
                setSpeedY(-5.0f);
                //setSpeedX(1);
                flyBackAnim.reset();
                flyForwardAnim.reset();
                //setDirection(RIGHT_DIR);
            }
                
        }
    }

    @Override
    public void dick() {
        if(!getIsJumping() && !isSlide && getSpeedX() == 0)
            setIsDicking(true);
    }

    @Override
    public void standUp() {
        setIsDicking(false);
        idleForwardAnim.reset();
        idleBackAnim.reset();
        dickForwardAnim.reset();
        dickBackAnim.reset();
    }

    @Override
    public void stopRun() {
        setSpeedX(0);
        runForwardAnim.reset();
        runBackAnim.reset();
        runForwardAnim.unIgnoreFrame(0);
        runBackAnim.unIgnoreFrame(0);
    }
    
    public void windBladeAttack(){
        
        if(isRepressedAttack){
            isRepressedAttack = false;
            isCountTimeRepressed = true;
            isWindyAttack = true;
            isAttacking = true;
            comboAttackCurrent = 1;
            comboAttackNext = 2;
            lastTimeAttack = System.nanoTime();
            idleWindBladeAttackAnim.reset();
            idleWindBladeAttackBackAnim.reset();
            idleForwardRepressedAnim.reset();
            idleForwardRepressedAnim.reset();
            
            bladeWindAttack.resetBladePath(System.nanoTime());
            Bullet boltBullet = new BoltBullet(getPosX(),getPosY(),getGameWorld());
            
            if(getDirection() == LEFT_DIR){
                System.out.println("here left");
                bladeWindAttack.getBladePoint(0).setPosX(getPosX() - (getWidth() / 2));
                bladeWindAttack.getBladePoint(0).setPosY(getPosY());

                bladeWindAttack.getBladePoint(1).setPosX(getPosX() - getWidth() - 50);
                bladeWindAttack.getBladePoint(1).setPosY(getPosY());
                
                boltBullet.setPosX(getPosX() - 70);
                boltBullet.setSpeedX(-3);
            }
            else{
                System.out.println("here right");
                bladeWindAttack.getBladePoint(0).setPosX(getPosX() + (getWidth() / 2));
                bladeWindAttack.getBladePoint(0).setPosY(getPosY());

                bladeWindAttack.getBladePoint(1).setPosX(getPosX() + getWidth() + 50);
                bladeWindAttack.getBladePoint(1).setPosY(getPosY());
                
                boltBullet.setPosX(getPosX() + 70);
                boltBullet.setSpeedX(3);
            }
            
            boltBullet.setTeamType(getTeamType());
            getGameWorld().particularBulletManager.addObject(boltBullet);
        }
        else{
            isCountTimeRepressed = true;
        }
                
    }
    
    @Override
    public void attack() {

        if(!isAttacking && !getIsDicking() && !getIsJumping() && getSpeedX() == 0 && !isHealing && !isSlide && getStamina() >= 30 && !isRepressedAttack){
            
            if(isCountTimeRepressed){
                this.lastTimeRepressedAttack = System.nanoTime();
                isCountTimeRepressed = false;
            }
            
            if(System.nanoTime() - lastTimeRepressedAttack >= TIMEREPRESSED){
                this.isRepressedAttack = true;
                return;
            }
            
            setStamina(getStamina() - 30);
            
            if(System.nanoTime() - lastTimeAttack <= 1035 * 1000000){
                
                comboAttackCurrent = comboAttackNext;
                
                switch(comboAttackCurrent){
                    case 1:
                        normalAttack_1.resetBladePath(System.nanoTime());
                        if(getDirection() == LEFT_DIR) {
                            
                            normalAttack_1.getBladePoint(0).setPosX(getPosX() - (getWidth() / 2) - 20);
                            normalAttack_1.getBladePoint(0).setPosY(getPosY() + getWidth() / 2);
                            
                            normalAttack_1.getBladePoint(1).setPosX(getPosX() - (getWidth() / 2) - 20);
                            normalAttack_1.getBladePoint(1).setPosY(getPosY() - getWidth() / 2);
                            
                        }
                        else {
                            normalAttack_1.getBladePoint(0).setPosX(getPosX() + (getWidth() / 2) + 20);
                            normalAttack_1.getBladePoint(0).setPosY(getPosY() + getWidth() / 2);
                            
                            normalAttack_1.getBladePoint(1).setPosX(getPosX() + (getWidth() / 2) + 20);
                            normalAttack_1.getBladePoint(1).setPosY(getPosY() - getWidth() / 2);
                        }
                        comboAttackNext = 2;
                        break;
                        
                    case 2:
                        normalAttack_2.resetBladePath(System.nanoTime());
                        if(getDirection() == LEFT_DIR) {
                            normalAttack_2.getBladePoint(0).setPosX(getPosX() - getWidth() / 2);
                            normalAttack_2.getBladePoint(0).setPosY(getPosY() - getHeight() / 2);
                            
                            normalAttack_2.getBladePoint(1).setPosX(getPosX() - getWidth() / 2);
                            normalAttack_2.getBladePoint(1).setPosY(getPosY() + getHeight() / 2);
                        }
                        else {
                            normalAttack_2.getBladePoint(0).setPosX(getPosX() + getWidth() / 2);
                            normalAttack_2.getBladePoint(0).setPosY(getPosY() - getHeight() / 2);
                            
                            normalAttack_2.getBladePoint(1).setPosX(getPosX() + getWidth() / 2);
                            normalAttack_2.getBladePoint(1).setPosY(getPosY() + getHeight() / 2);
                        }
                        comboAttackNext = 3;
                        break;
                        
                    case 3:
                        normalAttack_3.resetBladePath(System.nanoTime());
                        if(getDirection() == LEFT_DIR) {
                            normalAttack_3.getBladePoint(0).setPosX(getPosX() - getWidth() / 2);
                            normalAttack_3.getBladePoint(0).setPosY(getPosY() - 5);
                            
                            normalAttack_3.getBladePoint(1).setPosX((getPosX() - getWidth() / 2) - 35);
                            normalAttack_3.getBladePoint(1).setPosY(getPosY() - 5);
                        }
                        else {
                            normalAttack_3.getBladePoint(0).setPosX(getPosX() + getWidth() / 2);
                            normalAttack_3.getBladePoint(0).setPosY(getPosY() - 5);
                            
                            normalAttack_3.getBladePoint(1).setPosX((getPosX() + getWidth() / 2) + 35);
                            normalAttack_3.getBladePoint(1).setPosY(getPosY() - 5);
                        }
                        comboAttackNext = 1;                        
                        break;
                }

            }
            else {
                normalAttack_1.resetBladePath(System.nanoTime());
                if(getDirection() == LEFT_DIR) {
                    normalAttack_1.getBladePoint(0).setPosX(getPosX() - (getWidth() / 2) - 20);
                    normalAttack_1.getBladePoint(0).setPosY(getPosY() + (getWidth() / 2));
                            
                    normalAttack_1.getBladePoint(1).setPosX(getPosX() - (getWidth() / 2) - 20);
                    normalAttack_1.getBladePoint(1).setPosY(getPosY() - getWidth() / 2);
                }
                else {
                    normalAttack_1.getBladePoint(0).setPosX(getPosX() + (getWidth() / 2) + 20);
                    normalAttack_1.getBladePoint(0).setPosY(getPosY() + getWidth() / 2);
                            
                    normalAttack_1.getBladePoint(1).setPosX(getPosX() + (getWidth() / 2) + 20);
                    normalAttack_1.getBladePoint(1).setPosY(getPosY() - getWidth() / 2);
                }
                comboAttackCurrent = 1;
                comboAttackNext = 2;
            }

            lastTimeAttack = System.nanoTime();
            isAttacking = true;
        }
        else if(!isAttacking && getIsJumping() && getStamina() >= 40){
            
            setSpeedX(0);
            setSpeedY(7);
            
            setStamina(getStamina() - 40);
            
            flyAttack.resetBladePath(System.nanoTime());
            
            flyAttack.getBladePoint(0).setPosX(getPosX());
            flyAttack.getBladePoint(0).setPosY(getPosY() + getHeight() / 2);
            flyAttack.getBladePoint(0).setWidth(60);
            flyAttack.getBladePoint(0).setHeight(30);
            flyAttack.getBladePoint(0).setDamage(flyAttack.getBladePoint(0).getDamage() + 10);
            
            lastTimeFlyingAttack = System.nanoTime();
            isFlyingAttack = true;
            flyingAttackAnim.setIgnoreFrame(3);
            flyingAttackAnim.setIgnoreFrame(4);
            flyingAttackAnim.setIgnoreFrame(5);
            flyingAttackAnim.setIgnoreFrame(6);
        }
            
    }
    
    
    public void healing(){
        if(!isHealing && !isAttacking && !getIsJumping() && !getIsDicking() && getSpeedX() == 0 && healingPotions != 0 && !isSlide){
            setBlood(getBlood() + 65);
            if(getBlood() >= maximumHealth)
                setBlood(maximumHealth);
            isHealing = true;
            isLastTimeHealing = System.nanoTime();
        }
    }
    
    
    public void slide(){
        if(!isHealing && !isAttacking && !getIsJumping() && !getIsDicking() && getSpeedX() != 0 && getStamina() >= 30 ){

            if(System.nanoTime() - isLastTimeSlide >= 5000 * 1000000){
                
                setStamina(getStamina() - 30);
            
                if(getDirection() == LEFT_DIR) {
                    setSpeedX(-5);
                }
                else {
                    setSpeedX(5);
                }

                isSlide = true;
                isLastTimeSlide = System.nanoTime();
            }
        }
    }
    
    public Item getItemInTheBag(String code){
        for(int i = 0; i < this.bagItems.size(); i++){
            if(this.bagItems.get(i).getItemCode() == null ? code == null : this.bagItems.get(i).getItemCode().equals(code)){
                return this.bagItems.get(i);
            }
        }
        return null;
    }
    
    
    public void openTheDoor(){
        isOpenDoor = true;
    }
    
    public void closeTheDoor(){
        isOpenDoor = false;
    }

    public void pickUpItem(){
        if(!isHealing && !isAttacking && !getIsJumping() && !getIsDicking() && !isSlide && getSpeedX() == 0){
            System.out.println("pick up : " + this.bagItems.size());
            ParticularObject objectItem = getGameWorld().particularItemManager.getCollisionWidthObject(this);

            if(objectItem == null)
                return;
            
            this.setIsDicking(true);
            isPickingUpItem = true;
            lastTimePickingUpItem = System.nanoTime();
            
            getGameWorld().particularItemManager.RemoveObject(objectItem);
            bagItems.add((Item) objectItem);
        }
    }
    
    
    /*@Override
    public void hurtingCallback(){
        System.out.println("Call back hurting");
        hurtingSound.play();
    }*/

    @Override
    public void death() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    
}
