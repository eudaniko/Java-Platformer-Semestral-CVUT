package objects;

import main.Game;

import java.awt.geom.Rectangle2D;

import static utils.Constants.ObjectConstants.*;

public class Projectile {
    private Rectangle2D.Float hitBox;
    private int dir;
    private boolean active = true;
    private int spawnOffsetX, spawnOffaetY = 6;

    public Projectile(int x, int y, int dir) {
        this.hitBox = new Rectangle2D.Float(x, y, CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT);
        this.dir = dir;
        setPos(x, y);
    }


    public void update() {
        updatePos();
    }

    public void updatePos() {
        hitBox.x += dir * CANNON_BALL_SPEED;
    }

    public void setPos(int x, int y) {
        if (dir == 1)
            spawnOffsetX = (int) (30 * Game.SCALE);
        else
            spawnOffsetX = (int) (-10 * Game.SCALE);
        hitBox.x = x + spawnOffsetX;
        hitBox.y = y + spawnOffaetY;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getTileX() {
        return (int) (hitBox.x / Game.TILES_SIZE);
    }

    public int getTileY() {
        return (int) (hitBox.y / Game.TILES_SIZE);
    }
}
