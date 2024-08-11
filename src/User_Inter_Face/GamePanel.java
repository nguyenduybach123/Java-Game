/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User_Inter_Face;

import Effect.Animation;
import Game_Objects.GameWorld;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
    
    private Thread panelThread;
    
    private InputManager inputManager;
    
    private boolean isRunning;
    
    private BufferedImage bufImage;
    private Graphics2D bufG2D;
    
    private GameWorld gameWorld;
    
    private Animation idleForwardAnim;
    
    public GamePanel() {
        
        gameWorld = new GameWorld();
        
        inputManager = new InputManager(gameWorld);
        
        bufImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        

    }

    @Override
    public void paint(Graphics g){
        g.drawImage(bufImage, 0, 0, this);
    }
    
    public void UpdateGame(){
       gameWorld.Update();
    }
    
    
    public void RenderGame(){
        
        if(bufImage == null){
            bufImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        }
        if(bufImage != null){
            bufG2D = (Graphics2D) bufImage.getGraphics();
        }
        
        if(bufG2D != null){
            bufG2D.setColor(Color.BLACK);
            bufG2D.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
            
            // draw here
            gameWorld.Render(bufG2D);
            
        }
    
    }   
    
    public void startGame(){
        if(panelThread == null){
            isRunning = true;
            panelThread = new Thread(this);
            panelThread.start();
        }
    }
    
    @Override
    public void run(){
        
        int FPS = 80;
        long period = 1000*1000000 / FPS;
        long beginTime;
        long sleepTime;
        
        beginTime = System.nanoTime();
        
        while(isRunning){
            // Update and render game
            UpdateGame();
            RenderGame();
            repaint();
            
            long deltaTime = System.nanoTime() - beginTime;
            sleepTime = period - deltaTime;
            
            try {
                if(sleepTime > 0)
                    Thread.sleep(sleepTime / 1000000);
                else
                    Thread.sleep(period / 2000000);
            } catch (InterruptedException ex) {
            
            }
            
            beginTime = System.nanoTime();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        inputManager.processKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        inputManager.processKeyReleased(e.getKeyCode());
    }
}
