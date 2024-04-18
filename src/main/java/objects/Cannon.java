package objects;

import main.Game;

public class Cannon extends GameObject {


    public Cannon(int x, int y, int objectType) {
        super(x, y, objectType);
        initHitBox(40, 26);
        hitBox.x -= (int) (4 * Game.SCALE);
        hitBox.y += (int) (6 * Game.SCALE);
    }

    public void update() {
        if (doAnimation)
            updateAnimationTick();
    }

    public int getTileY() {
        return (int) (hitBox.y / Game.TILES_SIZE);
    }
}
