package objects;

import main.Game;
import utils.Constants;

import java.awt.geom.Rectangle2D;

import static utils.Constants.ANI_SPEED;
import static utils.Constants.GameConstants.SCALE;
import static utils.Constants.ObjectConstants.*;

public abstract class GameObject {

    protected int x, y, objectType;
    protected Rectangle2D.Float hitBox;
    protected boolean doAnimation, active = true;
    protected int aniTick, aniIndex;
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
            if (aniIndex >= GetSpriteAmount(objectType)) {
                aniIndex = 0;
                doAnimation = false;
            }
        }
    }

    protected void initHitBox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, (int) (width * SCALE), (int) (height * SCALE));
    }

    public void reset() {
        active = true;
        aniTick = 0;
        aniIndex = 0;
        doAnimation = objectType == RED_POTION || objectType == BLUE_POTION;
    }
    public int getAniTick(){
        return aniTick;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getObjectType() {
        return objectType;
    }

    public boolean isActive() {
        return active;
    }

    public int getXDrawOffset() {
        return xDrawOffset;
    }

    public int getYDrawOffset() {
        return yDrawOffset;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean doAnimations(){return  doAnimation;}

    public void setAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }

}
