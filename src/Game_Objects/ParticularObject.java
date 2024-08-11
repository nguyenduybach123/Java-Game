/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

import Effect.Animation;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Admin
 */
public abstract class ParticularObject extends GameObject {
    public static final int LEAGUE_TEAM = 1;
    public static final int ENEMY_TEAM = 2;
    public static final int NEUTRAL_TEAM = 3;
    
    public static final int LEFT_DIR = 0;
    public static final int RIGHT_DIR = 1;
    
    public static final int ALIVE = 0;
    public static final int BEHURT = 1;
    public static final int FEY = 2;
    public static final int DEATH = 3;
    public static final int NOBEHURT = 4;
    public static final int DELETE = 5;
    public static final int STUNED = 6;
    
    
    private int state = ALIVE;
    
    private float width;
    private float height;
    private float mass;
    private float speedX;
    private float speedY;
    private int blood;
    private int stamina;
    private int mana;
    private int regenStamina;
    private int regenMana;
    
    private int damage;
    
    private int direction;
    
    protected Animation beHurtForwardAnim, beHurtBackAnim;
    
    private int teamType;
    
    private long startTimeNoBeHurt;
    private long startTimeStuned;
    private long timeForNoBeHurt;
    private long timeForStuned;
    private long timeRegenStamina;
    
    
    private boolean isStuned;
    private boolean isCastingSpell;

    public ParticularObject(float x, float y, float width, float height, float mass, int blood, GameWorld gameWorld){
        super(x,y,gameWorld);
        setWidth(width);
        setHeight(height);
        setMass(mass);
        setBlood(blood);
        setStamina(100);
        setTimeRegenStamina(100000);
        direction = RIGHT_DIR;
    }

    public int getState() {        
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        if(stamina < 0)
            this.stamina = 0;
        else
            this.stamina = stamina;
    }

    public int getRegenStamina() {
        return regenStamina;
    }

    public void setRegenStamina(int regenStamina) {
        if(regenStamina <= 0)
            this.regenStamina = 0;
        else
            this.regenStamina = regenStamina;
    }

    public long getTimeRegenStamina() {
        return timeRegenStamina;
    }

    public void setTimeRegenStamina(long timeRegenStamina) {
            this.timeRegenStamina = timeRegenStamina;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getRegenMana() {
        return regenMana;
    }

    public void setRegenMana(int regenMana) {
        this.regenMana = regenMana;
    }
    
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Animation getBeHurtForwardAnim() {
        return beHurtForwardAnim;
    }

    public void setBeHurtForwardAnim(Animation beHurtForwardAnim) {
        this.beHurtForwardAnim = beHurtForwardAnim;
    }

    public Animation getBeHurtBackAnim() {
        return beHurtBackAnim;
    }

    public void setBeHurtBackAnim(Animation beHurtBackAnim) {
        this.beHurtBackAnim = beHurtBackAnim;
    }

    public int getTeamType() {
        return teamType;
    }

    public void setTeamType(int teamType) {
        this.teamType = teamType;
    }

    public long getStartTimeNoBeHurt() {
        return startTimeNoBeHurt;
    }

    public void setStartTimeNoBeHurt(long startTimeNoBeHurt) {
        this.startTimeNoBeHurt = startTimeNoBeHurt;
    }

    public long getTimeForNoBeHurt() {
        return timeForNoBeHurt;
    }

    public void setTimeForNoBeHurt(long timeForNoBeHurt) {
        this.timeForNoBeHurt = timeForNoBeHurt;
    }

    public boolean isIsCastingSpell() {
        return isCastingSpell;
    }

    public void setIsCastingSpell(boolean isCastingSpell) {
        this.isCastingSpell = isCastingSpell;
    }

    public boolean isObjectOutOfCameraView(){
        if(getPosX() - getGameWorld().camera.getPosX() > getGameWorld().camera.getWidthView() ||
                getPosX() - getGameWorld().camera.getPosX() < -50
            ||getPosY() - getGameWorld().camera.getPosY() > getGameWorld().camera.getHeightView()
                    ||getPosY() - getGameWorld().camera.getPosY() < -50)
            return true;
        else return false;
    }
    
    public Rectangle getBoundForCollisionWithMap(){
        Rectangle bound = new Rectangle();
        bound.x = (int) (getPosX() - (getWidth() / 2));
        bound.y = (int) (getPosY() - (getHeight() / 2));
        bound.width = (int) getWidth();
        bound.height = (int) getHeight();
        
        return bound;
    }
    
    public void beHurt(int damageEat){
        System.out.println("eat damage " + damageEat);
        setBlood(getBlood() - damageEat);
        hurtingCallBack();
        
        if(getState() == STUNED && damageEat < 100){
            return;
        }
        
        state = BEHURT;
    }
    
    public void beStuned(long stunedTime){
        isStuned = true;
        startTimeStuned = System.nanoTime();
        timeForStuned = stunedTime;
        setState(STUNED);
    }
    

    @Override
    public void Update() {
        switch(state){
            case ALIVE:   
                // note: SET DAMAGE FOR OBJECT NO DAMAGE
                ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
                if(object!=null){
                    
                    
                    if(object.getDamage() > 0){

                        // switch state to fey if object die

                        System.out.println("eat damage.... from collision with enemy........ " + object.getDamage());
                        beHurt(object.getDamage());
                    }
                    
                }
                break;
                
            case BEHURT:
                //System.out.println("be hurt");
                if(beHurtBackAnim == null){
                    //System.out.println("i so hurt");
                    state = NOBEHURT;
                    startTimeNoBeHurt = System.nanoTime();
                    if(getBlood() <= 0)
                            state = FEY;
                    
                } else {
                    //System.out.println("i so hurt for");
                    beHurtForwardAnim.Update(System.nanoTime());
                    if(beHurtForwardAnim.isLastFrame()){
                        beHurtForwardAnim.reset();
                        state = NOBEHURT;
                        if(getBlood() <= 0)
                            state = FEY;
                        startTimeNoBeHurt = System.nanoTime();
                    }
                }
                
                break;
                
            case FEY:
                state = DEATH;
                
                break;
            
            case DEATH:
                //death();
                break;
                
            case NOBEHURT:
                //System.out.println("state = nobehurt");
                if(System.nanoTime() - startTimeNoBeHurt > timeForNoBeHurt)
                    state = ALIVE;
                break;
                
            case STUNED:
                if(isStuned){
                    if(System.nanoTime() - startTimeStuned > timeForStuned){
                        setState(ALIVE);
                        isStuned = false;
                    }

                    if(getBlood() <= 0){
                        setState(FEY);
                    }
            
                }
                else {
                    isStuned = false;
                    setState(ALIVE);
                }
        }
    }
    
    public void drawBoundForCollisionWithMap(Graphics2D g2){
        Rectangle rect = getBoundForCollisionWithMap();
        g2.setColor(Color.BLUE);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    }
    
    public void drawBoundForCollisionWithEnemy(Graphics2D g2){
        Rectangle rect = getBoundForCollisionWithEnemy();
        g2.setColor(Color.RED);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    }
    
    public abstract void attack();
    
    public abstract void death();
    
    public abstract Rectangle getBoundForCollisionWithEnemy();
    
    public abstract void draw(Graphics2D g2);
    
    //public abstract void addItemToTheBag(Item item);
    
    public void hurtingCallBack(){}
    
}
