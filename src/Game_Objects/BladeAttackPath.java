/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

import java.awt.Graphics2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class BladeAttackPath {
    
    public static final int ALIVE = 1;
    public static final int BLOCKED = 2;
    public static final int DEATH = 3;
    private int state = ALIVE;
    
    protected List<BladeAttack> bladeAttackPoints;
    
    protected List<Boolean> ignoreBladePoints;
    
    private boolean isFirstTime;
    private long beginTime;
    
    private int defaultBladePoint;
    private int currentBladePoint;
    private int nextBladePoint;
    
    private boolean isRepeatable;
    
    public BladeAttackPath(int defaultBladePoint , boolean isRepeatable) {
        this.bladeAttackPoints = new LinkedList<BladeAttack>();
        
        this.ignoreBladePoints = new LinkedList<Boolean>();
        
        this.setDefaultBladePoint(defaultBladePoint);
        this.isRepeatable = isRepeatable;
        
        this.setCurrentBladePoint(defaultBladePoint);
        this.setNextBladePoint(currentBladePoint + 1);
        this.isFirstTime = true;
    }

    /*------------- Getter and Setter ---------------*/
    public void setBladeAttackPoints(List<BladeAttack> bladeAttackPoints) {
        this.bladeAttackPoints = bladeAttackPoints;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDefaultBladePoint() {
        return defaultBladePoint;
    }

    public void setDefaultBladePoint(int value) {
        if(value >= 0 && value < ignoreBladePoints.size())
            defaultBladePoint = value;
        else
            defaultBladePoint = 0;
    }

    public boolean isIsRepeatable() {
        return isRepeatable;
    }

    public void setIsRepeatable(boolean isRepeatable) {
        this.isRepeatable = isRepeatable;
    }
    
    public boolean isIgnoreBladePoint(int id){
        return ignoreBladePoints.get(id);
    }
    
    public void setIgnoreBladePoint(int id){
        if(id >= 0 && id < ignoreBladePoints.size())
            ignoreBladePoints.set(id, true);
    }
    
    public void unIgnoreBladePoint(int id){
        if(id >= 0 && id < ignoreBladePoints.size())
            ignoreBladePoints.set(id, false);
    }
    
    public BladeAttack getCurrentBladePoint(){
        return bladeAttackPoints.get(currentBladePoint);
    }
    
    public BladeAttack getNextBladePoint(){
        return bladeAttackPoints.get(nextBladePoint);
    }
    
    public void setCurrentBladePoint(int value){
        if(value >= 0 && value < ignoreBladePoints.size())
            currentBladePoint = value;
        else
            currentBladePoint = 0;
    }
    
    public void setNextBladePoint(int value){
        if(value >= 0 && value < bladeAttackPoints.size())
            nextBladePoint = value;
        else
            nextBladePoint = 0;
    }

    /*---------- Method ----------*/
    public boolean isLastCurrentBladePoint(){
        if(currentBladePoint == bladeAttackPoints.size() - 1){
            return true;
        }
        return false;
    }
    
    public boolean isLastNextBladePoint(){
        if(nextBladePoint == bladeAttackPoints.size() - 1){
            return true;
        }
        return false;
    }
    
    public void resetBladePath(long currentTime){
        
        this.state = ALIVE;
        this.currentBladePoint = this.defaultBladePoint;
        this.nextBladePoint = 1;
        this.isFirstTime = true;
        this.beginTime = currentTime;
        
    }
    
    public void add(BladeAttack bladeAttack){
        bladeAttackPoints.add(bladeAttack);
        ignoreBladePoints.add(false);
    }
    
    public BladeAttack getBladePoint(int id){
        if(id < 0 || id > bladeAttackPoints.size() - 1)
            return bladeAttackPoints.get(0);
        return bladeAttackPoints.get(id);
    }
    
    //calculate update X and Y
    public float increaseY(){
        if(bladeAttackPoints.size() == 0 || bladeAttackPoints.size() == 1)
            return 0;
        return (bladeAttackPoints.get(nextBladePoint).getPosY() - bladeAttackPoints.get(currentBladePoint).getPosY()) / bladeAttackPoints.get(currentBladePoint).getSpeedAttack();
    }
    
    public float increaseX(){
        if(bladeAttackPoints.size() == 0 || bladeAttackPoints.size() == 1)
            return 0;
        return (bladeAttackPoints.get(nextBladePoint).getPosX() - bladeAttackPoints.get(currentBladePoint).getPosX()) / bladeAttackPoints.get(currentBladePoint).getSpeedAttack();
    }
    
    //calculate update time
    public long timeUpdateBladeAttack(){
        return bladeAttackPoints.get(currentBladePoint).getPathTime() / bladeAttackPoints.get(currentBladePoint).getSpeedAttack();
    }
    
    
    
    private void nextBladePoint(){
        
        if(isLastCurrentBladePoint()){
            if(isRepeatable){ 
                currentBladePoint = this.defaultBladePoint;
                if(nextBladePoint > this.bladeAttackPoints.size() - 1)
                    nextBladePoint = this.defaultBladePoint;
                else
                    nextBladePoint = this.defaultBladePoint + 1;
            }
            else{
                this.state = DEATH;
                return;
            }
            isFirstTime = true;
        }
        else if(isLastNextBladePoint()){
            currentBladePoint = nextBladePoint;
            nextBladePoint = -1;
            isFirstTime = true;
        }
        else {

            currentBladePoint = nextBladePoint;
            nextBladePoint++;
            isFirstTime = true;
        }
        
        if(ignoreBladePoints.get(currentBladePoint)) nextBladePoint();
        
    }

    public void Update(long currentTime) {
        
        switch(state){
            case ALIVE:
                //System.out.println(bladeAttackPoints.get(currentBladePoint).getPosX() + " " + bladeAttackPoints.get(currentBladePoint).getPosY() + " " + bladeAttackPoints.get(currentBladePoint).getSpeedX() + " " + bladeAttackPoints.get(currentBladePoint).getSpeedY());
                
                BladeAttack currentBladeAttackPoint = getCurrentBladePoint();
                
                //System.out.println("size : " + this.bladeAttackPoints.size());
                //System.out.println("node : " + currentBladePoint);

                if(isFirstTime){

                    if(currentTime - beginTime > currentBladeAttackPoint.getDelayTime() && beginTime != 0){
                        if(isLastCurrentBladePoint()){
                            //System.out.println("last node : " + currentBladePoint);
                            currentBladeAttackPoint.setSpeedX(0);
                            currentBladeAttackPoint.setSpeedY(0);
                        }
                        else{
                            //System.out.println("current node : " + currentBladePoint + " | " + increaseX() + " " + increaseY());
                            currentBladeAttackPoint.setSpeedX(increaseX());
                            currentBladeAttackPoint.setSpeedY(increaseY());
                        }
                        
                        isFirstTime = false;
                        beginTime = currentTime;
                    }
                    
                } 
                else{

                    if(isLastCurrentBladePoint()){
                        
                        if(currentTime - beginTime > currentBladeAttackPoint.getPathTime()){

                           nextBladePoint(); 
                        }
                        else {
                            currentBladeAttackPoint.Update();
                        }
                        
                    }
                    else {
                        
                        BladeAttack nextBladeAttackPoint = getNextBladePoint();
                        //System.out.println("X : " + currentBladeAttackPoint.getPosX() + " " + nextBladeAttackPoint.getPosX() );
                        //System.out.println("Y : " + currentBladeAttackPoint.getPosY() + " " + nextBladeAttackPoint.getPosY() );
                        if( currentBladeAttackPoint.getPosX() == nextBladeAttackPoint.getPosX() &&
                            currentBladeAttackPoint.getPosY() == nextBladeAttackPoint.getPosY() || currentTime - beginTime > currentBladeAttackPoint.getPathTime()){

                            nextBladePoint();
                            beginTime = currentTime;
                        }
                        else if(currentTime - beginTime > timeUpdateBladeAttack()){

                            //System.out.println(bladeAttackPoints.get(currentBladePoint).getPosX() + " " + bladeAttackPoints.get(currentBladePoint).getPosY() + " " + bladeAttackPoints.get(currentBladePoint).getSpeedX() + " " + bladeAttackPoints.get(currentBladePoint).getSpeedY());
                            currentBladeAttackPoint.Update();
                        }
                    }

                    
                }
                
                break;
            
            case BLOCKED:
                break;
            
            case DEATH:
                break;
        }

        
    }
    
    public void draw(Graphics2D g2){
        if(!isFirstTime){
            bladeAttackPoints.get(currentBladePoint).draw(g2);
        }
    }
}
