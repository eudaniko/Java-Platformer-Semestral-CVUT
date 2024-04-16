package objects;

import main.Game;

import java.awt.geom.Rectangle2D;

import static utils.Constants.ANI_SPEED;
import static utils.Constants.ObjectConstants.*;

public abstract class GameObject {

    protected int x, y, objectType;
    protected Rectangle2D.Float hitBox;
    protected boolean doAnimation, active = true;
    protected int aniTick, aniIndex;
    protected int state;
    protected int xDrawOffset, yDrawOffset;

    public GameObject(int x, int y, int objectType) {
        this.x = x;
        this.y = y;
        this.objectType = objectType;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(state)) {
                aniIndex = 0;
            }
        }
    }

    protected void initHitBox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }


    protected void resetAll(){
        active = true;
        aniTick = 0;
        aniIndex = 0;
        doAnimation = true;
//        state = IDLE;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public int getEntityState() {
        return state;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getObjectType(){
        return objectType;
    }

    public boolean isActive(){
        return active;
    }

    public int getXDrawOffset(){
        return xDrawOffset;
    }

    public int getYDrawOffset(){
        return yDrawOffset;
    }
}
