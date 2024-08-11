/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

import Effect.Animation;
import Effect.CacheDataLoader;
import Magic.AbsorbVitality;
import Magic.ThunderColumn;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class Dark_Necromancer extends Enemy {
    
    public static final long TIME_TO_SUMMON = 5000000000L;
    public static final long TIME_TO_THUNDER = 1000000000;
    public static final int BASE_ABSORB_BLOOD = 30;
    public static final int BASE_ABSORB_MANA = 20;
    
    
    private ArrayList<Item> bagItems;
    private ArrayList<ParticularObject> summonedMonters;
    
    
    private Animation idleForwardAnim, idleBackAnim;
    private Animation walkForwardAnim, walkBackAnim;
    private Animation stunedForwardAnim, stunedBackAnim;
    private Animation deathForwardAnim, deathBackAnim;
    private Animation summonForwardAnim, summonBackAnim;
    private Animation absorbForwardAnim, absorbBackAnim;
    private Animation summonThunderForwardAnim, summonThunderBackAnim;
    private Animation summonEffectForwardAnim, summonEffectBackAnim; 
    
    private boolean isSummoningMonter;
    private boolean isSummonMonter;
    private boolean isCastedUltimate;
    private boolean isFinishUltimate;
    private boolean isAbsorbVitality;
    private boolean isThunderHitting;
    private boolean isFirstTime = false;
    
    private long lastTimeSummonMonter;
    private long lastTimeThunderHitting; 
    
    private int amountImmolateMonters;
    private float monterSummonPosX;
    private float monterSummonDir;
    private float posImageSummonX;
    private float posImageSummonY;
    
    
    public Dark_Necromancer(float x, float y, GameWorld gameWorld) {
        super(x, y, 90, 127,1.0f,100,500, gameWorld);
        
        bagItems = new ArrayList<Item>();
        
        lastTimeThunderHitting = 0;
        
        isCastedUltimate = false;
        
        isAbsorbVitality = false;
        
        isSummoningMonter = false;
        
        isThunderHitting = false;
        
        monterSummonPosX = 0;
        
        monterSummonDir = LEFT_DIR;
        
        summonedMonters = new ArrayList<ParticularObject>(3);
        
        setTimeForNoBeHurt(0);
        
        setDamage(50);
        
        setSpeedX(1);
        
        setVisionRangeX(250);
        setVisionRangeY(20);
        
        setAttackRangeX(100);
        setAttackRangeY(20);
        
        setIsLocationKeeper(true);
        
        idleForwardAnim = CacheDataLoader.getInstance().getAnimation("necromancer_idle");
        idleBackAnim = CacheDataLoader.getInstance().getAnimation("necromancer_idle");
        idleBackAnim.flipAllImage();
        
        walkForwardAnim = CacheDataLoader.getInstance().getAnimation("necromancer_walk");
        walkBackAnim = CacheDataLoader.getInstance().getAnimation("necromancer_walk");
        walkBackAnim.flipAllImage();
        
        beHurtForwardAnim = CacheDataLoader.getInstance().getAnimation("necromancer_take_hit");
        beHurtBackAnim = CacheDataLoader.getInstance().getAnimation("necromancer_take_hit");
        beHurtBackAnim.flipAllImage();
        
        stunedForwardAnim = CacheDataLoader.getInstance().getAnimation("necromancer_stuned");
        stunedBackAnim = CacheDataLoader.getInstance().getAnimation("necromancer_stuned");
        stunedBackAnim.flipAllImage();
        
        deathForwardAnim = CacheDataLoader.getInstance().getAnimation("necromancer_death");
        deathBackAnim = CacheDataLoader.getInstance().getAnimation("necromancer_death");
        deathBackAnim.flipAllImage();
        
        summonForwardAnim = CacheDataLoader.getInstance().getAnimation("necromancer_attack");
        summonBackAnim = CacheDataLoader.getInstance().getAnimation("necromancer_attack");
        summonBackAnim.flipAllImage();
        
        absorbForwardAnim = CacheDataLoader.getInstance().getAnimation("necromancer_absorb");
        absorbBackAnim = CacheDataLoader.getInstance().getAnimation("necromancer_absorb");
        absorbBackAnim.flipAllImage();
        
        summonThunderForwardAnim = CacheDataLoader.getInstance().getAnimation("necromancer_summon_thunder");
        summonThunderBackAnim = CacheDataLoader.getInstance().getAnimation("necromancer_summon_thunder");
        summonThunderBackAnim.flipAllImage();
        
        summonEffectForwardAnim = CacheDataLoader.getInstance().getAnimation("dark_soul_effect");
        summonEffectBackAnim = CacheDataLoader.getInstance().getAnimation("dark_soul_effect");
        summonEffectBackAnim.flipAllImage();
        
    }
    
    public void addItemToTheBag(Item item){
        bagItems.add(item);
    }
    
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle rect = getBoundForCollisionWithMap();
        
        rect.x += 10;
        rect.y += 10;
        
        rect.width -= 20;
        rect.height -= 20;
        
        return rect;
    }
    
    @Override
    public void Update(){
        
        super.Update();

        switch(getState()){
            
            case ALIVE:
            case NOBEHURT:
                if(getIsAttacking()){
                    if(isSummoningMonter){

                        if(summonForwardAnim.isLastFrame() || summonBackAnim.isLastFrame()){
                            System.out.println("is summon monter");
                            if(getDirection() == LEFT_DIR){
                                monterSummonPosX = getPosX() - getAttackRangeX();
                                monterSummonDir = LEFT_DIR;
                            }
                            else{
                                monterSummonPosX = getPosX() + getWidth() + getAttackRangeX();
                                monterSummonDir = RIGHT_DIR;
                            }

                            isSummonMonter = true;
                            isSummoningMonter = false;
                            setIsAttacking(false);
                            summonEffectForwardAnim.reset();
                            summonEffectBackAnim.reset();
                            summonBackAnim.reset();
                            summonForwardAnim.reset();
                        }
                        
                    }
                    else if(isCastedUltimate){
                        
                        if(!isFinishUltimate){
                            if(isThunderHitting){
                                if(amountImmolateMonters != 0){
                                    if(System.nanoTime() - lastTimeThunderHitting >  TIME_TO_THUNDER){
                                        lastTimeThunderHitting = System.nanoTime();
                                        ThunderColumn thunderSpell = new ThunderColumn((int)getGameWorld().dk.getPosX(),getGameWorld());
                                        getGameWorld().spellManager.addSpell(thunderSpell);
                                        amountImmolateMonters--;
                                    }
                                }
                                else {
                                    isFinishUltimate = true;
                                }
                            }
                            else{
                                if(!isAbsorbVitality){
                                    AbsorbVitality absorbVitalitySpell = new AbsorbVitality(this,getGameWorld());
                                    absorbVitalitySpell.setListParticularVitality(summonedMonters);
                                    getGameWorld().spellManager.addSpell(absorbVitalitySpell);
                                    amountImmolateMonters = summonedMonters.size();
                                    isAbsorbVitality = true;
                                }
                                else {
                                    if(!isIsCastingSpell()){
                                        isAbsorbVitality = false;
                                        isThunderHitting = true;
                                    }
                                }
                            }
                        }
                        else{
                            isThunderHitting = false;
                            isCastedUltimate = false;
                            setIsAttacking(false);
                        }
   
                    }
                }
                
                if(isSummonMonter){
                    if(summonEffectForwardAnim.getCurrentFrame() == 9 || summonEffectBackAnim.getCurrentFrame() == 9){
                        SkeletonWarrior skeletonWarrior = new SkeletonWarrior(350,getPosY(),getGameWorld());
                        if(getDirection() == LEFT_DIR){
                            skeletonWarrior.setDirection(ParticularObject.LEFT_DIR);
                        }
                        else {
                            skeletonWarrior.setDirection(ParticularObject.RIGHT_DIR);
                        }
                        skeletonWarrior.setPosX(monterSummonPosX);

                        skeletonWarrior.setIsEnemySpotted(true);
                        skeletonWarrior.setTeamType(ParticularObject.ENEMY_TEAM);
                        summonedMonters.add(skeletonWarrior);
                        getGameWorld().particularObjectManager.addObject(skeletonWarrior);
                        isSummonMonter = false;
                    }
                }
                
                
                if(isWithinAttackRange()){
                    setSpeedX(0);
                    attack();
                }

                if(getIsLanding()){
                    setIsLanding(false);
                    walkForwardAnim.reset();
                    walkBackAnim.reset();
                    posImageSummonY = (getPosY() + getHeight() / 2) - summonForwardAnim.getCurrentImage().getHeight() / 2;
                }
                break;
            
            case DEATH:
                if(deathForwardAnim.isLastFrame() || deathBackAnim.isLastFrame()){
                    setState(DELETE);
                }
                break;
        }
        
    }
    
    @Override 
    public void draw(Graphics2D g2){
        switch(getState()){
            
            case ALIVE:
                super.Update();
                
                if(getSpeedX() != 0){
                    if(getDirection() == LEFT_DIR){
                        walkBackAnim.Update(System.nanoTime());
                        walkBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                    }
                    else{
                        walkForwardAnim.Update(System.nanoTime());
                        walkForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                    }
                }
                else{
                    if(!getIsAttacking()){
                        if(getDirection() == LEFT_DIR){
                            idleBackAnim.Update(System.nanoTime());
                            idleBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        }
                        else{
                            idleForwardAnim.Update(System.nanoTime());
                            idleForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        }
                    }
                    else{
                        if(!isCastedUltimate){
                            if(getDirection() == LEFT_DIR){
                                posImageSummonX = (getPosX() - summonBackAnim.getCurrentImage().getWidth() / 2) + 20;
                                summonBackAnim.Update(System.nanoTime());
                                summonBackAnim.draw((int) (posImageSummonX - getGameWorld().camera.getPosX()), (int) posImageSummonY - (int) getGameWorld().camera.getPosY(), g2);
                            }
                            else{
                                posImageSummonX = getPosX() + summonBackAnim.getCurrentImage().getWidth() / 2;
                                summonForwardAnim.Update(System.nanoTime());
                                summonForwardAnim.draw((int) (posImageSummonX - getGameWorld().camera.getPosX()), (int) posImageSummonY - (int) getGameWorld().camera.getPosY(), g2);
                            }
                        }
                        else{
                            if(isAbsorbVitality){
                                if(getDirection() == LEFT_DIR){
                                    absorbBackAnim.Update(System.nanoTime());
                                    absorbBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                                else{
                                    absorbForwardAnim.Update(System.nanoTime());
                                    absorbForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                            }
                            else if(isThunderHitting){
                                if(getDirection() == LEFT_DIR){
                                    summonThunderBackAnim.Update(System.nanoTime());
                                    summonThunderBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                                else{
                                    summonThunderForwardAnim.Update(System.nanoTime());
                                    summonThunderForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                            }
                        }
                        
                    }
                }
                
                if(isSummonMonter){
                    float monterPosY = (getPosY() + getHeight() / 2) - summonEffectBackAnim.getCurrentImage().getHeight() / 2;
                    if(monterSummonDir == LEFT_DIR){
                        summonEffectBackAnim.Update(System.nanoTime());
                        summonEffectBackAnim.draw((int)(monterSummonPosX - getGameWorld().camera.getPosX()) , (int) (monterPosY - (int) getGameWorld().camera.getPosY()), g2);
                    }
                    else{
                        summonEffectForwardAnim.Update(System.nanoTime());
                        summonEffectForwardAnim.draw((int)(monterSummonPosX - getGameWorld().camera.getPosX()) , (int) (monterPosY - (int) getGameWorld().camera.getPosY()), g2);
                    }
                }
                
                drawBoundForCollisionWithMap(g2);
                drawBoundForCollisionWithEnemy(g2);
                drawBoundForAttackRange(g2);
                drawBoundForVisionRange(g2);
                
                break;
            
            case BEHURT:
                if(getDirection() == LEFT_DIR){
                    beHurtBackAnim.Update(System.nanoTime());
                    beHurtBackAnim.draw((int)(getPosX() - getGameWorld().camera.getPosX()) , (int) (getPosY() - (int) getGameWorld().camera.getPosY()), g2);
                }
                else{
                    beHurtForwardAnim.Update(System.nanoTime());
                    beHurtForwardAnim.draw((int)(getPosX() - getGameWorld().camera.getPosX()) , (int) (getPosY() - (int) getGameWorld().camera.getPosY()), g2);
                }
                break;
                
                
            case DEATH:
                if(getDirection() == LEFT_DIR){
                    deathBackAnim.Update(System.nanoTime());
                    deathBackAnim.draw((int)(getPosX() - getGameWorld().camera.getPosX()) , (int) (getPosY() - (int) getGameWorld().camera.getPosY()), g2);
                }
                else{
                    deathForwardAnim.Update(System.nanoTime());
                    deathForwardAnim.draw((int)(getPosX() - getGameWorld().camera.getPosX()) , (int) (getPosY() - (int) getGameWorld().camera.getPosY()), g2);
                }
                break;
        }
    }
    
    @Override
    public void death() {
        System.out.println("death");
        
    }
    
    @Override
    public void attack() {
        
        if(!getIsAttacking()){
            if(!isCastedUltimate && !isSummoningMonter && !isSummonMonter){
                if(summonedMonters.size() <= 1){
                    if(System.nanoTime() - lastTimeSummonMonter > TIME_TO_SUMMON){
                        isSummoningMonter = true;
                        lastTimeSummonMonter = System.nanoTime();
                        setIsAttacking(true);
                    }
                }
                else if(!isFirstTime){
                    absorbLifeUltimate();
                    isFirstTime = true;
                }
            }
            
        }
        
    }
    
    private void absorbLifeUltimate(){        
        if(summonedMonters.size() >= 1){
            System.out.println("ultimate yeah");
            amountImmolateMonters = summonedMonters.size();
            isCastedUltimate = true;
            isFinishUltimate = false;
            setIsAttacking(true);
        }
    }
}
