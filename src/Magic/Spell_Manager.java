/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Magic;

import Game_Objects.GameWorld;
import Game_Objects.ParticularObject;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Spell_Manager {
    protected List<Spell> listSpells;
    
    private GameWorld gameWorld;

    public Spell_Manager(GameWorld gameWorld) {
        listSpells = Collections.synchronizedList(new LinkedList<Spell>());
        this.gameWorld = gameWorld;
    }
    
    public void addSpell(Spell magicSpell){
        synchronized(listSpells){
            listSpells.add(magicSpell);
        }
        
    }
    
    public void RemoveObject(Spell magicSpell){
        synchronized(listSpells){
            for(int id = 0; id < listSpells.size(); id++){
                Spell spell = listSpells.get(id);
                if(spell == magicSpell)
                    listSpells.remove(id);
            }
        }
    }
    
    public void UpdateObjects(){
        synchronized(listSpells){
            for(int id = 0; id < listSpells.size(); id++){
                
                Spell spellCaster = listSpells.get(id);
                
                spellCaster.Update();
                
                if(spellCaster.getState() == Spell.END){
                    listSpells.remove(id);
                }
            }
        }
    }

    public void draw(Graphics2D g2){
        synchronized(listSpells){
            for(Spell spell: listSpells)
                spell.draw(g2);
        }
    }
    
}
