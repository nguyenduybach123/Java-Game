/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game_Objects;

/**
 *
 * @author Admin
 */
public class Camera extends GameObject{
    
    private final int Left = 400;
    private final int Right = 200;
    private final int Bottom = 400;
    private final int Top = 250;
    
    private float widthView;
    private float heightView;
    
    private boolean isLocked = false;
    
    public Camera(float x, float y, float widthView, float heightView, GameWorld gameWorld) {
        super(x, y, gameWorld);
        this.widthView = widthView;
        this.heightView = heightView;
    }

    public void lock(){
        isLocked = true;
    }
    
    public void unlock(){
        isLocked = false;
    }
    
    @Override
    public void Update() {
    
        // NOTE: WHEN SEE FINAL BOSS, THE CAMERA WON'T CHANGE THE POSITION,
        // AFTER THE TUTORIAL, CAMERA WILL SET THE NEW POS
        
        if(!isLocked){
        
            DarkKnight mainCharacter = getGameWorld().dk;
            int widthOfMap = getGameWorld().physicalMap.getWidthOfMap();

            if(mainCharacter.getPosX() - getPosX() > Left) setPosX(mainCharacter.getPosX() - Left);
            if(mainCharacter.getPosX() - getPosX() < Right) setPosX(mainCharacter.getPosX() - Right);
            if(getPosX() < 0) setPosX(0);
            if(getPosX() + widthView > widthOfMap) setPosX(widthOfMap - widthView);

            if(mainCharacter.getPosY() - getPosY() > Bottom) setPosY(mainCharacter.getPosY() - Bottom); // bottom
            else if(mainCharacter.getPosY() - getPosY() < Top) setPosY(mainCharacter.getPosY() - Top);// top 
        }
    
    }

    public float getWidthView() {
        return widthView;
    }

    public void setWidthView(float widthView) {
        this.widthView = widthView;
    }

    public float getHeightView() {
        return heightView;
    }

    public void setHeightView(float heightView) {
        this.heightView = heightView;
    }
    
}
