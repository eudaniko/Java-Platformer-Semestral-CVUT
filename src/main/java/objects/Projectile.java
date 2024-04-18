// Daniil Nikonenko
// PJV Semestral

package objects;

import java.awt.geom.Rectangle2D;

import static utils.Constants.GameConstants.SCALE;
import static utils.Constants.GameConstants.TILES_SIZE;
import static utils.Constants.ObjectConstants.*;

public class Projectile {
    private final Rectangle2D.Float hitBox;
    private final int dir;
    private boolean active = true;

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
        int spawnOffsetX;
        if (dir == 1)
            spawnOffsetX = (int) (30 * SCALE);
        else
            spawnOffsetX = (int) (-10 * SCALE);
        hitBox.x = x + spawnOffsetX;
        int spawnOffsetY = 6;
        hitBox.y = y + spawnOffsetY;
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
        return (int) (hitBox.x / TILES_SIZE);
    }

    public int getTileY() {
        return (int) (hitBox.y / TILES_SIZE);
    }
}
