package objects;

import utils.Constants;

public class Spike extends GameObject {
    public Spike(int x, int y, int objectType) {
        super(x, y, objectType);

        initHitBox(32, 16);
        xDrawOffset = 0;
        yDrawOffset = (int) (16 * Constants.GameConstants.SCALE);
        hitBox.y += yDrawOffset;
    }
}
