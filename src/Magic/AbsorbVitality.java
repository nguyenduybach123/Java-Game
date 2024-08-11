/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Magic;

import Effect.Animation;
import Effect.CacheDataLoader;
import Game_Objects.GameWorld;
import Game_Objects.ParticularObject;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class AbsorbVitality extends Spell{
    
    private final long timeForAbsorb = 1000000000;
    private final int limitTimeToVitality = 5;
    
    
    private Animation absorbVitalityForwardAnim, absorbVitalityBackAnim;
    private Animation bloodyEffectForwardAnim, bloodyEffectBackAnim;
    
    private List<ParticularObject> listParticularVitality;

    private int numberOfAbsorb;
    
    private long lastTimeForAbsorb;
    
    private boolean isTakingVitality;
    
    public AbsorbVitality(ParticularObject caster , GameWorld gameWorld) {
        
        super(caster.getPosX(), caster.getPosY(), gameWorld);
        
        listParticularVitality = new LinkedList<ParticularObject>();
        
        setCasterSpell(caster);
        
        getCasterSpell().setIsCastingSpell(true);
        
        setTypeSpell(BUFF_SPELL);
        
        numberOfAbsorb = 0;
        
        isTakingVitality = false;

        absorbVitalityForwardAnim = CacheDataLoader.getInstance().getAnimation("absorb_vitality");
        absorbVitalityBackAnim = CacheDataLoader.getInstance().getAnimation("absorb_vitality");
        absorbVitalityBackAnim.flipAllImage();
        
        absorbVitalityForwardAnim.setIgnoreFrame(13);
        absorbVitalityForwardAnim.setIgnoreFrame(14);
        
        absorbVitalityBackAnim.setIgnoreFrame(13);
        absorbVitalityBackAnim.setIgnoreFrame(14);
        
        bloodyEffectForwardAnim = CacheDataLoader.getInstance().getAnimation("bloody_effect");
        bloodyEffectBackAnim = CacheDataLoader.getInstance().getAnimation("bloody_effect");
        bloodyEffectBackAnim.flipAllImage();
        
    }

    public List<ParticularObject> getListParticularVitality() {
        return listParticularVitality;
    }

    public void setListParticularVitality(List<ParticularObject> listParticularVitality) {
        this.listParticularVitality = listParticularVitality;
    }
    
    @Override
    public void Update(){
        
        super.Update();
        
        
        switch(getState()){
            
            case EXIST -> {
                
                if(getCasterSpell().getState() == ParticularObject.DEATH){
                    setState(FEY);
                    return;
                }
                
                if(getCasterSpell().getSpeedY() > 0 || getCasterSpell().getSpeedX() > 0){
                    setState(FEY);
                    return;
                }

                if(absorbVitalityForwardAnim.isLastFrame() || absorbVitalityBackAnim.isLastFrame()){
                    setState(FEY);
                    return;
                }

                if(System.nanoTime() - lastTimeForAbsorb > timeForAbsorb) {
                    
                    if(!getListParticularVitality().isEmpty()){
                        
                        if(!isTakingVitality){
                            for(ParticularObject objectVitality : listParticularVitality){
                                if(objectVitality.getTeamType() == getCasterSpell().getTeamType()){
                                    objectVitality.beStuned(1000000000);
                                    objectVitality.setBlood(objectVitality.getBlood() - 10);
                                }
                                else{
                                    objectVitality.setBlood(objectVitality.getBlood() - 10);
                                }
                            }
                            isTakingVitality = true;
                        }
                        
                        if(absorbVitalityBackAnim.getCurrentFrame() == 12 || absorbVitalityForwardAnim.getCurrentFrame() == 12){
                            
                            if(numberOfAbsorb == limitTimeToVitality - 1){
                                absorbVitalityForwardAnim.unIgnoreFrame(13);
                                absorbVitalityForwardAnim.unIgnoreFrame(14);
                                
                                absorbVitalityBackAnim.unIgnoreFrame(13);
                                absorbVitalityBackAnim.unIgnoreFrame(14);
                            }
                            
                            lastTimeForAbsorb = System.nanoTime();
                            isTakingVitality = false;
                            numberOfAbsorb++;
                        }
                        
                        
                    }
                    else {
                        setState(FEY);
                    }
                }
            }
        }
    }
    
    @Override
    public void draw(Graphics2D g2) {
        
        if(!getListParticularVitality().isEmpty()){
            
            for(ParticularObject objectVitalitied : listParticularVitality){
                if(getCasterSpell().getDirection() == ParticularObject.LEFT_DIR){
                    bloodyEffectBackAnim.Update(System.nanoTime());
                    bloodyEffectBackAnim.draw((int)(objectVitalitied.getPosX() - getGameWorld().camera.getPosX()), (int)(objectVitalitied.getPosY() - getGameWorld().camera.getPosY()), g2);
                }
                else{
                    bloodyEffectForwardAnim.Update(System.nanoTime());
                    bloodyEffectForwardAnim.draw((int)(objectVitalitied.getPosX() - getGameWorld().camera.getPosX()), (int)(objectVitalitied.getPosY() - getGameWorld().camera.getPosY()), g2);
                }
            }
            
            int y = (int) ((getCasterSpell().getPosY() + getCasterSpell().getHeight() / 2) - absorbVitalityForwardAnim.getCurrentImage().getHeight() / 2);
            if(getCasterSpell().getDirection() == ParticularObject.LEFT_DIR){
                absorbVitalityBackAnim.Update(System.nanoTime());
                absorbVitalityBackAnim.draw((int) (getCasterSpell().getPosX() - getGameWorld().camera.getPosX()), (int) (y - getGameWorld().camera.getPosY()), g2);
            }
            else{
                absorbVitalityForwardAnim.Update(System.nanoTime());
                absorbVitalityForwardAnim.draw((int) (getCasterSpell().getPosX() - getGameWorld().camera.getPosX()), (int) (y - getGameWorld().camera.getPosY()), g2);
            }
            
            
            

        }
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
