/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User_Inter_Face;

import Game_Objects.DarkKnight;
import Game_Objects.GameWorld;
import Game_Objects.ParticularObject;
import java.awt.event.KeyEvent;

/**
 *
 * @author Admin
 */
public class InputManager {
    
    private GameWorld gameWorld;
    
    public InputManager(GameWorld gameWorld){
        this.gameWorld = gameWorld; 
    }
    
    public void processKeyPressed(int keyCode){
        switch(keyCode){
            case KeyEvent.VK_UP:
                gameWorld.dk.jump();
                break;
            
            case KeyEvent.VK_DOWN:
                gameWorld.dk.dick();
                break;
            
            case KeyEvent.VK_LEFT:
                gameWorld.dk.setDirection(ParticularObject.LEFT_DIR);
                gameWorld.dk.run();

                break;
            
            case KeyEvent.VK_RIGHT:
                gameWorld.dk.setDirection(ParticularObject.RIGHT_DIR);
                gameWorld.dk.run();
                break;
            
            case KeyEvent.VK_A:
                gameWorld.dk.attack();
                break;
            
            case KeyEvent.VK_Q:
                gameWorld.dk.healing();
                System.out.println("healing");
                break;
            
            case KeyEvent.VK_F:
                gameWorld.dk.pickUpItem();
                break;
                
            case KeyEvent.VK_V:
                gameWorld.dk.openTheDoor();
                break;
                
            case KeyEvent.VK_SHIFT:
                gameWorld.dk.slide();
                System.out.println("slide");
                break;
                
            case KeyEvent.VK_SPACE:
                gameWorld.dk.setSpeedY(-3);
                //gamePanel.dk.setPosY(gamePanel.dk.getPosY() - 3);
                break;
            
        }
    }
    
    
    public void processKeyReleased(int keyCode){
        switch(keyCode){
            case KeyEvent.VK_UP:
                break;
            
            case KeyEvent.VK_DOWN:
                gameWorld.dk.standUp();
                break;
            
            case KeyEvent.VK_LEFT:
                gameWorld.dk.stopRun();
                break;
            
            case KeyEvent.VK_RIGHT:                
                gameWorld.dk.stopRun();
                break;
            
            case KeyEvent.VK_A:
                gameWorld.dk.windBladeAttack();
                break;
                
            case KeyEvent.VK_V:
                gameWorld.dk.closeTheDoor();
                break;
            
            
        }
    }
}
