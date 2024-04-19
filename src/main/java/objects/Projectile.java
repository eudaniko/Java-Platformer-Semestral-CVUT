// Daniil Nikonenko
// PJV Semestral

package objects;

import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.SCALE;
import static utils.Constants.GameConstants.TILES_SIZE;
import static utils.Constants.ObjectConstants.*;

public class Projectile extends GameObject {
    private final Rectangle2D.Float hitBox;
    private final int dir;
    private boolean active = true;
    private final BufferedImage sprite;

    public Projectile(int x, int y, int dir) {
        super(x, y, dir);
        this.hitBox = new Rectangle2D.Float(x, y, CANNON_BALL_WIDTH_HEIGHT, CANNON_BALL_WIDTH_HEIGHT);
        this.dir = dir;
        setPos(x, y);
        sprite = LoadSave.GetSpriteAtlas(LoadSave.BALL);

    }


    public void update() {
        updatePos();
    }

    @Override
    public void draw(Graphics g, int xLevelOffset) {
        g.drawImage(sprite,
                (int) (hitBox.x - xLevelOffset), (int) hitBox.y,
                CANNON_BALL_WIDTH_HEIGHT, CANNON_BALL_WIDTH_HEIGHT, null);
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
