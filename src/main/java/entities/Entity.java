package entities;

import main.Game;
import utils.Constants;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.GameConstants.SCALE;

public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitBox;
    protected int aniTick, aniIndex;
    protected int state;
    protected float airSpeed = 5;
    protected boolean inAir = false;
    protected int maxHealth,  currentHealth;
    protected Rectangle2D.Float attackBox;
    protected float walkSpeed;



    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    //for debugging the hitBox
    protected void drawHitBox(Graphics g, int xLevelOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x - xLevelOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    protected void drawAttackBox(Graphics g, int xLevelOffset) {
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLevelOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    protected void initHitBox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, (int) (width * SCALE), (int) (height * SCALE));
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

}
