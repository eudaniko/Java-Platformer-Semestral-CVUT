package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    //for debugging the hitBox
    protected void drawHitBox(Graphics g) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    protected void initHitBox(float x, float y, int width, int height){
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

//    public void updateHitBox() {
//        hitBox.x = (int) x;
//        hitBox.y = (int) y;
//    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
}
