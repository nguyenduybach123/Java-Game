/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User_Inter_Face;

import Effect.CacheDataLoader;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author Admin
 */
public class GameFrame extends JFrame {

    GamePanel gamePanel;

    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 600;

    public GameFrame() {
        Toolkit toolKit = this.getToolkit();
        Dimension dimension = toolKit.getScreenSize();
        this.setBounds((dimension.width - SCREEN_WIDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2, SCREEN_WIDTH, SCREEN_HEIGHT);

        try {
            CacheDataLoader.getInstance().LoadData();
        } catch (IOException ex) {
            ex.printStackTrace(); 
        }

        this.setTitle("That is the game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new GamePanel();
        this.add(gamePanel);
        this.addKeyListener(gamePanel);
        this.setVisible(true);
    }

    public void startGame() {
        gamePanel.startGame();
    }

    public static void main(String[] args) {
        GameFrame game = new GameFrame();
        game.startGame();
    }
}
