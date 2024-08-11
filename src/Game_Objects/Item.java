/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

import java.awt.Rectangle;

/**
 *
 * @author Admin
 */
public abstract class Item extends ParticularObject{

    private String itemCode;
    
    private boolean isLanding;
    private boolean isInTheBag;
    
    
    public Item(float x, float y, float width, float height, float mass, int blood, String code, GameWorld gameWorld) {
        super(x, y, width, height, mass, blood, gameWorld);
        
        setTeamType(NEUTRAL_TEAM);
        
        setItemCode(code);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
    
    public boolean getIsLanding() {
        return isLanding;
    }

    public void setIsLanding(boolean isLanding) {
        this.isLanding = isLanding;
    }

    public boolean getIsInTheBag() {
        return isInTheBag;
    }

    public void setIsInTheBag(boolean isInTheBag) {
        this.isInTheBag = isInTheBag;
    }
    

    @Override
    public void Update(){
        
        super.Update();
        
        if(getState() == ALIVE || getState() == NOBEHURT){
            System.out.println(isLanding);
            if(!isLanding){

                if(getDirection() == LEFT_DIR && 
                        getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap())!=null){

                    Rectangle rectLeftWall = getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap());
                    setPosX(rectLeftWall.x + rectLeftWall.width + getWidth()/2);

                }
                if(getDirection() == RIGHT_DIR && 
                        getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap())!=null){

                    Rectangle rectRightWall = getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap());
                    setPosX(rectRightWall.x - getWidth()/2);

                }


                Rectangle boundForCollisionWithMapFuture = getBoundForCollisionWithMap();
                boundForCollisionWithMapFuture.y += (getSpeedY()!=0?getSpeedY(): 2);
                
                Rectangle rectLand = getGameWorld().physicalMap.haveCollisionWithLand(boundForCollisionWithMapFuture);
                
                Rectangle rectTop = getGameWorld().physicalMap.haveCollisionWithTopWall(boundForCollisionWithMapFuture);
                
                if(rectTop !=null){
                    setSpeedY(0);
                    setPosY(rectTop.y + getGameWorld().physicalMap.getTileSize() + getHeight()/2);   
                }else if(rectLand != null){
                    if(getSpeedY() > 0) setIsLanding(true);
                    setSpeedY(0);
                    setPosY(rectLand.y - getHeight()/2 - 1);
                }else{
                    setSpeedY(getSpeedY() + getMass());
                    setPosY(getPosY() + getSpeedY());
                }
            }
        }
        
    }
    

    @Override
    public void attack() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void death() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle rect = getBoundForCollisionWithMap();
        
        rect.x = (int) getPosX() - (int)(this.getWidth() / 2);
        rect.y = (int) getPosY() - (int)(this.getHeight() / 2);
        rect.width = (int) this.getWidth();
        rect.height = (int) this.getHeight();
        
        return rect;
    }
    
//    @Override
//    public void addItemToTheBag(Item item){}
    
}
