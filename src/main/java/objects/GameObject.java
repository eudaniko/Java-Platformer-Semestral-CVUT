// Daniil Nikonenko
// PJV Semestral

package objects;

import gamestates.Playing;
import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.*;
import static utils.Constants.ObjectConstants.*;

public abstract class GameObject {

    protected int x, y, objectType;
    protected Rectangle2D.Float hitBox;
    protected BufferedImage[] sprites;
    protected boolean doAnimation, active = true;
    protected int aniTick, aniIndex;
    protected int xDrawOffset, yDrawOffset;

    private float hoverOffset;
    protected int hoverDir = -1;
    protected boolean hoverEffect = false;
    protected float hoverSpeed = 0.04f;
    protected int maxHoverOffset = 3;

    public GameObject(int x, int y, int objectType) {
        this.x = x;
        this.y = y;
        this.objectType = objectType;
        loadSprites();
    }

    public void update(Playing playing) {
        updateAnimationTick();
        if (hoverEffect)
            updateHover(hoverSpeed, maxHoverOffset);
         updatePos();
    }

    protected void draw(Graphics g, int xLevelOffset) {
        if (DRAW_HIT_BOX)
            drawHitBox(g, xLevelOffset);
    }

    protected void drawHitBox(Graphics g, int xLevelOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x - xLevelOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }


    protected void loadSprites() {
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= Constants.GameConstants.ANI_SPEED) {
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


    protected void updateHover(float hoverSpeed, int maxHoverOffset) {
        hoverOffset += (hoverSpeed * SCALE * hoverDir);
        if (hoverOffset >= (int) (maxHoverOffset * SCALE))
            hoverDir = -1;
        else if (hoverOffset < 0)
            hoverDir = 1;
        this.hitBox.y = y + hoverOffset;
    }

    protected void updatePos(){};


    public void reset() {
        active = true;
        doAnimation = objectType == RED_POTION || objectType == BLUE_POTION;
        aniTick = 0;
        aniIndex = 0;

    }

    public int getAniTick() {
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

    public boolean doAnimations() {
        return doAnimation;
    }

    public void setAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }

}
