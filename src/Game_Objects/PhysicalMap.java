 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

import Effect.CacheDataLoader;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Admin
 */
public class PhysicalMap extends GameObject {
    
    public int[][] phys_map;
    private int tileSize;
    private int widthOfMap;
    
    public PhysicalMap(float x, float y, GameWorld gameWorld){
        super(x,y,gameWorld);
        this.tileSize = 30;
        this.phys_map = CacheDataLoader.getInstance().getPhysicalMap();
        widthOfMap = phys_map[0].length * tileSize;
    }
    
    public int getTileSize() {
        return tileSize;
    }

    public int getWidthOfMap() {
        return widthOfMap;
    }
    

    public void draw(Graphics2D g2){
        
        Camera camera = getGameWorld().camera;
        
        g2.setColor(Color.GRAY);
        for(int i = 0; i< phys_map.length; i++){
            for(int j = 0; j < phys_map[0].length; j++){
                if(phys_map[i][j] != 0)
                    g2.fillRect((int)getPosX() + j*tileSize - (int) camera.getPosX(), (int)getPosY() + i*tileSize - (int) camera.getPosY(), tileSize, tileSize);
            }
        }
    }
    
    public Rectangle haveCollisionWithTopWall(Rectangle rect){
        int posX1 = rect.x / tileSize;
        posX1 -= 2;
        int posX2 = (rect.x + rect.width) / tileSize;
        posX2 += 2;
        
        int posY1 = rect.y / tileSize;
        
        if(posX1 < 0) 
            posX1 = 0;
        
        if(posX2 >= phys_map[0].length)
            posX2 = phys_map[0].length - 1;

        
        
        for(int y = posY1; y >= 0; y--){
            for(int x = posX1; x <= posX2; x++){
                if(phys_map[y][x] == 1){
                    Rectangle r = new Rectangle((int) getPosX() + x * tileSize, (int) getPosY() + y * tileSize, tileSize, tileSize);
                    if(rect.intersects(r))
                        return r;
                }
            }
        }
        
        return null;   
    }
    
    public Rectangle haveCollisionWithLand(Rectangle rect){
        int posX1 = rect.x / tileSize;
        posX1 -= 2;
        int posX2 = (rect.x + rect.width) / tileSize;
        posX2 += 2;
        
        int posY1 = (rect.y + rect.height) / tileSize;
        
        if(posX1 < 0) 
            posX1 = 0;
        
        if(posX2 >= phys_map[0].length)
            posX2 = phys_map[0].length - 1;
        
        for(int y = posY1; y < phys_map.length; y++){
            for(int x = posX1; x <= posX2; x++){
                if(phys_map[y][x] == 1){
                    Rectangle r = new Rectangle((int) getPosX() + x * tileSize, (int) getPosY() + y * tileSize, tileSize, tileSize);
                    if(rect.intersects(r))
                        return r;
                }
            }
        }
        
        return null;
    }
    
    public Rectangle haveCollisionWithLeftWall(Rectangle rect){
        
        int posY1 = rect.y/tileSize;
        posY1-=2;
        int posY2 = (rect.y + rect.height)/tileSize;
        posY2+=2;
        
        int posX1 = (rect.x + rect.width)/tileSize;
        int posX2 = posX1 - 3;
        if(posX2 < 0) posX2 = 0;
        
        if(posY1 < 0) posY1 = 0;
        if(posY2 >= phys_map.length) posY2 = phys_map.length - 1;
        
        
        for(int x = posX1; x >= posX2; x--){
            for(int y = posY1; y <= posY2;y++){
                if(phys_map[y][x] == 1){
                    Rectangle r = new Rectangle((int) getPosX() + x * tileSize, (int) getPosY() + y * tileSize, tileSize, tileSize);
                    if(r.y < rect.y + rect.height - 1 && rect.intersects(r))
                        return r;
                }
            }
        }
        return null;
        
    }
    
    public Rectangle haveCollisionWithRightWall(Rectangle rect){
        
        int posY1 = rect.y/tileSize;
        posY1-=2;
        int posY2 = (rect.y + rect.height)/tileSize;
        posY2+=2;
        
        int posX1 = (rect.x + rect.width)/tileSize;
        int posX2 = posX1 + 3;
        if(posX2 >= phys_map[0].length) posX2 = phys_map[0].length - 1;
        
        if(posY1 < 0) posY1 = 0;
        if(posY2 >= phys_map.length) posY2 = phys_map.length - 1;
        
        
        for(int x = posX1; x <= posX2; x++){
            for(int y = posY1; y <= posY2;y++){
                if(phys_map[y][x] == 1){
                    Rectangle r = new Rectangle((int) getPosX() + x * tileSize, (int) getPosY() + y * tileSize, tileSize, tileSize);
                    if(r.y < rect.y + rect.height - 1 && rect.intersects(r))
                        return r;
                }
            }
        }
        return null;
        
    }

    @Override
    public void Update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
