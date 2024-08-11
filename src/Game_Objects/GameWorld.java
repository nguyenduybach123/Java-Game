/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

import Magic.Spell_Manager;
import User_Inter_Face.GameFrame;
import java.awt.Graphics2D;

/**
 *
 * @author Admin
 */
public class GameWorld {
    
    public HealthBar healthBar;
    
    public StaminaBar staminaBar;
    
    public DarkKnight dk;
    
    public PhysicalMap physicalMap;
    
    public BackgroundMap backgroundMap;
    
    public Camera camera;
    
    public ParticularObjectManager particularObjectManager;
    
    ParticularObjectManager particularBulletManager;
    
    public ParticularObjectManager particularItemManager;
    
    Spell_Manager spellManager;

    public GameWorld() {
        physicalMap = new PhysicalMap(0,0,this);
        
        backgroundMap = new BackgroundMap(0, 0, this);
        
        particularObjectManager = new ParticularObjectManager(this);
        
        particularBulletManager = new ParticularObjectManager(this);
        
        particularItemManager = new ParticularObjectManager(this);
        
        spellManager = new Spell_Manager(this);
        
        dk = new DarkKnight(600,370,this);
        
        healthBar = new HealthBar(600,500,100,dk,this);
        
        staminaBar = new StaminaBar(600,550,dk,this);

        camera = new Camera(0, 50, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, this);

        InitEnemies_Map_1();
      
        particularObjectManager.addObject(dk);
    }   
    
    private void InitEnemies_Map_1(){
        
        //Item keyItem = new KeyItem(0,0,this);
        
        ParticularObject door = new Door_1(2900,421,this);
        particularObjectManager.addObject(door);
        
        SkeletonWarrior skeletonWarrior = new SkeletonWarrior(350,310,this);
        skeletonWarrior.setDirection(ParticularObject.LEFT_DIR);
        skeletonWarrior.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(skeletonWarrior);
        
        Dark_Necromancer darkNecromancer = new Dark_Necromancer(2000,310,this);
        darkNecromancer.setDirection(ParticularObject.RIGHT_DIR);
        darkNecromancer.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(darkNecromancer);
        
        
        
    }
    
    public void Update(){

        camera.Update();
        
        particularObjectManager.UpdateObjects();
        
        particularBulletManager.UpdateObjects();
        
        particularItemManager.UpdateObjects();
        
        spellManager.UpdateObjects();
        
        healthBar.Update(System.nanoTime());
        
        staminaBar.Update(System.nanoTime());
    }
    
    public void Render(Graphics2D g2){
        
        backgroundMap.draw(g2);
        
        
        
        particularBulletManager.draw(g2);
        
        particularItemManager.draw(g2);
        
        spellManager.draw(g2);
        
        particularObjectManager.draw(g2);
        
        healthBar.draw(g2);
        
        staminaBar.draw(g2);
    }
    
    
}
