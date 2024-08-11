/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

/**
 *
 * @author Admin
 */
public abstract class MeleeAttack extends ParticularObject{
    
    public MeleeAttack(float x, float y, float width, float height, float mass, int damage, GameWorld gameWorld) {
            super(x, y, width, height, mass, 1, gameWorld);
            setDamage(damage);
    }
    
    public void Update(){
        super.Update();
    }
}
