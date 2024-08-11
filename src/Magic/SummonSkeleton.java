/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Magic;

import Effect.Animation;
import Game_Objects.GameWorld;
import Game_Objects.ParticularObject;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Admin
 */
public class SummonSkeleton extends Spell {
    
    private Animation summonEffectAnim;
    
    public SummonSkeleton(float posX, float posY, ParticularObject casterSpell, GameWorld gameWorld) {
        super(posX, posY, gameWorld);
        
        setTypeSpell(SUMMON_SPELL);
        
        setCasterSpell(casterSpell);
        
        getCasterSpell().setMana(getCasterSpell().getMana() - getManaCost());
    }
    
    @Override
    public void Update(){
        switch(getState()){
            case EXIST -> {
                
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
