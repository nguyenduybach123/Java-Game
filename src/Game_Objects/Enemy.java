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
public abstract class Enemy extends ParticularObject {
    
    public static final long TIME_RESET = 5000 * 1000000;
    public static final long TIME_TO_STOP = 2000 * 1000000;
    public static final long TIME_OUT_RANGE = 2000 * 1000000;
    
    private float defaultLocationX;
    private int operatingRange;

    private int attackRangeX;
    private int attackRangeY;
    private int visionRangeX;
    private int visionRangeY;

    private boolean isJumping;
    private boolean isDicking;
    private boolean isLanding;
    
    private boolean isAttacking;
    private boolean isLocationKeeper;
    private boolean isReturningLocationDefault;
    private boolean isControlRange;
    private boolean isOutControlRange;
    private boolean isWaitingAttack;
    private boolean isPatrolAround;
    private boolean isEnemySpotted;
    private boolean isTimeStop;
    private boolean isStuned;
    
    private long lastTimeStuned;
    private long timeStuned;
    private long timeOnRange;
    private long timeStopMove = 0;
    private long lastTimeInControlRange;
    
    

    public Enemy(float x, float y, float width, float height, float mass, int blood, int operateRange, GameWorld gameWorld) {
        
        super(x, y, width, height, 1.0f, blood, gameWorld);
        
        operatingRange = operateRange;
        
        defaultLocationX = x;
        
        isReturningLocationDefault = false;
        
        isOutControlRange = false;
        
        isControlRange = true;
        
        isPatrolAround = true;
        
        isLocationKeeper = true;

    }
    
    public boolean getIsPatrolAround() {
        return isPatrolAround;
    }

    public void setIsPatrolAround(boolean isPatrolAround) {
        this.isPatrolAround = isPatrolAround;
    }
    
    public boolean isIsJumping() {
        return isJumping;
    }

    public void setIsJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    public boolean isIsDicking() {
        return isDicking;
    }

    public void setIsDicking(boolean isDicking) {
        this.isDicking = isDicking;
    }

    public boolean getIsLanding() {
        return isLanding;
    }

    public void setIsLanding(boolean isLanding) {
        this.isLanding = isLanding;
    }

    public int getOperatingRange() {
        return operatingRange;
    }

    public void setOperatingRange(int operatingRange) {
        this.operatingRange = operatingRange;
    }
 
    public int getAttackRangeX() {
        return attackRangeX;
    }

    public void setAttackRangeX(int attackRangeX) {
        this.attackRangeX = attackRangeX;
    }

    public int getAttackRangeY() {
        return attackRangeY;
    }
    

    public void setAttackRangeY(int attackRangeY) {
        this.attackRangeY = attackRangeY;
    }

    public int getVisionRangeX() {
        return visionRangeX;
    }

    public void setVisionRangeX(int visionRangeX) {
        this.visionRangeX = visionRangeX;
    }

    public int getVisionRangeY() {
        return visionRangeY;
    }

    public void setVisionRangeY(int visionRangeY) {
        this.visionRangeY = visionRangeY;
    }

    public boolean getIsAttacking() {
        return isAttacking;
    }

    public void setIsAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
    }

    public boolean getIsEnemySpotted() {
        return isEnemySpotted;
    }

    public void setIsEnemySpotted(boolean isEnemySpotted) {
        this.isEnemySpotted = isEnemySpotted;
    }
    
    public boolean getIsWaitingAttack() {
        return isWaitingAttack;
    }

    public void setIsWaitingAttack(boolean isWaitingAttack) {
        this.isWaitingAttack = isWaitingAttack;
    }

    public boolean isIsControlRange() {
        return isControlRange;
    }

    public void setIsControlRange(boolean isControlRange) {
        this.isControlRange = isControlRange;
    }

    public boolean getIsOutControlRange() {
        return isOutControlRange;
    }

    public void setIsOutControlRange(boolean isOutControlRange) {
        this.isOutControlRange = isOutControlRange;
    }

    public boolean getIsLocationKeeper() {
        return isLocationKeeper;
    }

    public void setIsLocationKeeper(boolean isLocationKeeper) {
        this.isLocationKeeper = isLocationKeeper;
    }

    public long getTimeStuned() {
        return timeStuned;
    }

    public void setTimeStuned(long timeStuned) {
        this.timeStuned = timeStuned;
    }

    @Override
    public void Update(){
        
        super.Update();

        if(getState() == ALIVE || getState() == NOBEHURT){

            if(!isLanding){
                
                if(getIsAttacking()){
                    return;
                }
                
                if(!isReturningLocationDefault){
                
                    intrusionDetecion();

                    if(getIsEnemySpotted()){

                        //if main charater out range sight of object , the heath and isEnemySpotted will reset
                        if(isOutVisionRange()){
                            if(System.nanoTime() - timeOnRange > TIME_RESET){
                                isEnemySpotted = false;
                            }
                        }
                        else{
                            //Change direction object
                            changeDirection();
                        }

                    }
                    else if(getIsPatrolAround()){

                        //else the emeny will patrol around 
                        patrolAround();

                        if(isTimeStop){
                            if(System.nanoTime() - timeStopMove > TIME_TO_STOP){

                                if(getDirection() == LEFT_DIR){
                                    setSpeedX(1);
                                    setDirection(ParticularObject.RIGHT_DIR);
                                }
                                else{
                                    setSpeedX(-1);
                                    setDirection(ParticularObject.LEFT_DIR);
                                }
                                isTimeStop = false;
                            }
                        }   
                    }

                    if(getIsLocationKeeper()){
                        if(!getIsOutControlRange()){
                            if(getPosX() > defaultLocationX + operatingRange || getPosX() < defaultLocationX - operatingRange){
                                isOutControlRange = true;
                                lastTimeInControlRange = System.nanoTime();
                            }
                        }
                        else{
                            if(System.nanoTime() - lastTimeInControlRange > TIME_OUT_RANGE){
                                isReturningLocationDefault = true;
                                isEnemySpotted = false;
                                isTimeStop = true;
                                setSpeedX(0);
                                timeStopMove = System.nanoTime();

                            }
                        }
                    }
                }
                else{
                    if(this.getPosX() == defaultLocationX){
                        isReturningLocationDefault = false;
                        isOutControlRange = false;
                    }
                    else{
                        if(isTimeStop){
                            if(System.nanoTime() - timeStopMove > TIME_TO_STOP){
                                if(getDirection() == LEFT_DIR){
                                    setSpeedX(1);
                                    setDirection(ParticularObject.RIGHT_DIR);
                                }
                                else{
                                    setSpeedX(-1);
                                    setDirection(ParticularObject.LEFT_DIR);
                                }
                                isTimeStop = false;
                            }
                            else {
                                return;
                            }
                        }
                        else{
                            intrusionDetecion();
                            if(getIsEnemySpotted()){
                                isReturningLocationDefault = false;
                                isOutControlRange = false;
                            }
                        }
                    }
                }

                float lastPosX = getPosX();
                
                setPosX(getPosX() + getSpeedX());
                
                if(getDirection() == LEFT_DIR &&
                        getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this) != null){
                    
                    ParticularObject rectObjectCollision = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
                    setPosX(lastPosX);
                }
                
                if(getDirection() == RIGHT_DIR && 
                        getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this) != null){
                    
                    ParticularObject rectObjectCollision = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
                    setPosX(lastPosX);
                }
                
                
                if(getDirection() == LEFT_DIR &&
                        getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap()) != null){
                    
                    Rectangle rectLeftWall = getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap());
                    setPosX(rectLeftWall.x + rectLeftWall.width + getWidth() / 2);

                }
                
                if(getDirection() == RIGHT_DIR && 
                        getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap()) != null){
                    Rectangle rectRightWall = getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap());
                    setPosX(rectRightWall.x - getWidth() / 2);
                }
                
                
                
                Rectangle boundForCollisionWithMapFuture = getBoundForCollisionWithMap();
                boundForCollisionWithMapFuture.y += (getSpeedY() != 0 ? getSpeedY() : 2);
                
                Rectangle rectLand = getGameWorld().physicalMap.haveCollisionWithLand(boundForCollisionWithMapFuture);
                Rectangle rectTop = getGameWorld().physicalMap.haveCollisionWithTopWall(boundForCollisionWithMapFuture);
                
                if(rectTop != null){
                    setSpeedY(0);
                    setPosY(rectTop.y + rectTop.height + getHeight() / 2);
                }
                else if(rectLand != null){
                    setIsJumping(false);
                    if(getSpeedY() > 0)
                        setIsLanding(true);
                    setSpeedY(0);
                    setPosY(rectLand.y - getHeight() / 2);
                }
                else {
                    setIsJumping(true);
                    setSpeedY(getSpeedY() + getMass());
                    setPosY(getPosY() + getSpeedY());
                }
            }   
            
        }
        else if(getState() == BEHURT){
            isEnemySpotted = true;
            isReturningLocationDefault = false;
            isOutControlRange = false;
        }
        
    }
    
    
    private void patrolAround(){
        if(!isTimeStop){
            Rectangle futureRect = getBoundForCollisionWithMap();
            futureRect.x += getSpeedX(); 

            if(getGameWorld().physicalMap.haveCollisionWithLeftWall(futureRect) != null || getGameWorld().physicalMap.haveCollisionWithRightWall(futureRect) != null || isOutControlRange){
                timeStopMove = System.nanoTime();
                setSpeedX(0);
                isTimeStop = true;
            }
        }
        /*if(getDirection() == LEFT_DIR && getGameWorld().physicalMap.haveCollisionWithLeftWall(futureRect) != null ){
            System.out.println("Left");
            setDirection(ParticularObject.RIGHT_DIR);
            setSpeedX(getSpeedX() * -1);
        }
                
        if(getDirection() == RIGHT_DIR && getGameWorld().physicalMap.haveCollisionWithRightWall(futureRect) != null){
            System.out.println("Right");
            setDirection(ParticularObject.LEFT_DIR);
            setSpeedX(getSpeedX() * -1);
        }*/

    }
    
    public boolean isOutVisionRange(){
        
        if(!getBoundForVisionRange().intersects(getGameWorld().dk.getBoundForCollisionWithMap())){
            return true;
        }

        return false;
    }

    public boolean isWithinAttackRange(){

        if(getIsEnemySpotted()){
            if(getBoundForAttackRange().intersects(getGameWorld().dk.getBoundForCollisionWithEnemy())){
                if(getGameWorld().dk.getPosX() < this.getPosX() && this.getDirection() == LEFT_DIR ||
                   getGameWorld().dk.getPosX() > this.getPosX() && this.getDirection() == RIGHT_DIR){
                    return true;
                }
            }
        }
        return false;
    }
    
    public Rectangle getBoundForVisionRange(){
        Rectangle rect = getBoundForCollisionWithMap();
        Rectangle rectVision = new Rectangle();
        rectVision.x = rect.x - getVisionRangeX();
        rectVision.y = rect.y - getVisionRangeY();
        rectVision.width = ((rect.x + rect.width) + getVisionRangeX()) - rectVision.x; 
        rectVision.height = (rect.y + rect.height) - rectVision.y;
        return rectVision;
    }
    
    public Rectangle getBoundForAttackRange(){
        Rectangle rect = getBoundForCollisionWithMap();
        Rectangle rectAttack = new Rectangle();
        rectAttack.x = rect.x - getAttackRangeX();
        rectAttack.y = rect.y - getAttackRangeY();
        rectAttack.width = ((rect.x + rect.width) + getAttackRangeX()) - rectAttack.x; 
        rectAttack.height = (rect.y + rect.height) - rectAttack.y;
        return rectAttack;
    }
    
    public void drawBoundForVisionRange(Graphics2D g2){
        Rectangle rect = getBoundForVisionRange();
        g2.setColor(Color.MAGENTA);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    } 
    
    public void drawBoundForAttackRange(Graphics2D g2){
        Rectangle rect = getBoundForAttackRange();
        g2.setColor(Color.ORANGE);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    }
    
    private void intrusionDetecion(){
        if(!isEnemySpotted){
            if(getBoundForVisionRange().intersects(getGameWorld().dk.getBoundForCollisionWithMap())){
                if(getGameWorld().dk.getPosX() < this.getPosX() && this.getDirection() == LEFT_DIR ||
                   getGameWorld().dk.getPosX() > this.getPosX() && this.getDirection() == RIGHT_DIR){
                    this.isEnemySpotted = true;
                }
            }    
        }
        else {
            timeOnRange = System.nanoTime();
        }
        
    }
    
    private void changeDirection(){
        Rectangle objectCollisionMap = getBoundForCollisionWithMap();
        if((getGameWorld().dk.getPosX() >= objectCollisionMap.x  - getVisionRangeX() && getGameWorld().dk.getPosX() <= objectCollisionMap.x) ||
            (getGameWorld().dk.getPosX() + getGameWorld().dk.getWidth() >= objectCollisionMap.x - getVisionRangeX()  && getGameWorld().dk.getPosX() + getGameWorld().dk.getWidth() <= objectCollisionMap.x)){
            setSpeedX(-1);
            setDirection(LEFT_DIR);
        }
        else if((getGameWorld().dk.getPosX() >= objectCollisionMap.x && getGameWorld().dk.getPosX() <= (objectCollisionMap.x + objectCollisionMap.width) + visionRangeX) ||
                (getGameWorld().dk.getPosX() + getGameWorld().dk.getWidth() >= objectCollisionMap.x && getGameWorld().dk.getPosX() + getGameWorld().dk.getWidth() <= (objectCollisionMap.x + objectCollisionMap.width) + visionRangeX)){
            setSpeedX(1);
            setDirection(RIGHT_DIR);
        }
    }
    
}
