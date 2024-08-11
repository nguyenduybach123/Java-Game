/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

import Effect.CacheDataLoader;
import User_Inter_Face.GameFrame;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Admin
 */
public class BackgroundMap extends GameObject {
    public int[][] map;
    private final int tileSize = 30;
    
    public BackgroundMap(float x, float y, GameWorld gameWorld) {
        super(x, y, gameWorld);
        map = CacheDataLoader.getInstance().getBackgroundMap();
    }

    @Override
    public void Update() {}
    
    public void draw(Graphics2D g2){
        
        Camera camera = getGameWorld().camera;
        
        g2.setColor(Color.RED);
        for(int i = 0;i< map.length;i++)
            for(int j = 0;j<map[0].length;j++)
                if(map[i][j]!=0 && j*tileSize - camera.getPosX() > -30 && j*tileSize - camera.getPosX() < GameFrame.SCREEN_WIDTH
                        && i*tileSize - camera.getPosY() > -30 && i*tileSize - camera.getPosY() < GameFrame.SCREEN_HEIGHT){ 
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage("tiled"+map[i][j]).getImage(), (int) getPosX() + j*tileSize - (int) camera.getPosX(), 
                        (int) getPosY() + i*tileSize - (int) camera.getPosY(), null);
                }
        
    }
}
