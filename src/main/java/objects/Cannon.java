// Daniil Nikonenko
// PJV Semestral

package objects;

import utils.Constants;

import static utils.Constants.GameConstants.SCALE;

public class Cannon extends GameObject {


    public Cannon(int x, int y, int objectType) {
        super(x, y, objectType);
        initHitBox(40, 26);
        hitBox.x -= (int) (4 * SCALE);
        hitBox.y += (int) (6 * SCALE);
    }

    public void update() {
        if (doAnimation)
            updateAnimationTick();
    }

    public int getTileY() {
        return (int) (hitBox.y / Constants.GameConstants.TILES_SIZE);
    }
}
