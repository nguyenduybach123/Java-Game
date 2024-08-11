/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Magic;

import Game_Objects.GameObject;
import Game_Objects.GameWorld;
import Game_Objects.ParticularObject;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Admin
 */
public abstract class Spell extends GameObject {
    
    public static final int EXIST = 1;
    public static final int NOBEHURT = 2;
    public static final int FEY = 3;
    public static final int END = 4;
    
    public static final int ATTACK_SPELL = 1;
    public static final int BUFF_SPELL = 2;
    public static final int SUMMON_SPELL = 3;
    
    private ParticularObject casterSpell;
    
    private int state;
    private int typeSpell;
    private int width,height;
    private int damage;
    private int manaCost;
    
    public Spell(float posX, float posY, GameWorld gameWorld) {
        super(posX, posY, gameWorld);
        
        setState(EXIST);
    }
    
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTypeSpell() {
        return typeSpell;
    }

    public void setTypeSpell(int typeSpell) {
        this.typeSpell = typeSpell;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
    
    public ParticularObject getCasterSpell() {
        return casterSpell;
    }

    public void setCasterSpell(ParticularObject casterSpell) {
        this.casterSpell = casterSpell;
    }
    
    
    
    @Override
    public void Update() {
        switch(state){
            
            case NOBEHURT -> {
                
            }
            
            case EXIST -> {
                /*if(getTypeSpell() == ATTACK_SPELL){
                    ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthMagicSpell(this);
                    System.out.println("eat damage spell");
                    if(object != null){
                        object.beHurt(damage);
                    }
                }*/
            }
            
            case FEY -> {
                getCasterSpell().setIsCastingSpell(false);
                setState(END);
            }

            case END -> {

            }
        }
    }
    
    public void drawBoundForCollisionWithEnemy(Graphics2D g2){
        Rectangle rect = getBoundForCollisionWithEnemy();
        g2.setColor(Color.RED);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    }
    
    public abstract void draw(Graphics2D g2);
    
    public abstract Rectangle getBoundForCollisionWithEnemy();
    
}
