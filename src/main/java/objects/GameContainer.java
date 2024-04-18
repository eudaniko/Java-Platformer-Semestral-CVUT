package objects;

import main.Game;
import utils.Constants;


import static utils.Constants.GameConstants.SCALE;
import static utils.Constants.ObjectConstants.*;

public class GameContainer extends GameObject {


    public GameContainer(int x, int y, int objectType) {
        super(x, y, objectType);
        doAnimation = false;
        createHitBox();
    }

    private void createHitBox() {
        if (objectType == BOX) {
            initHitBox(25, 18);
            xDrawOffset = (int) (7 * SCALE);
            yDrawOffset = (int) (12 * SCALE);
        } else if (objectType == BARREL) {
            initHitBox(23, 15);
            xDrawOffset = (int) (8 * SCALE);
            yDrawOffset = (int) (5 * SCALE);
        }

        hitBox.y += yDrawOffset + (int) (SCALE * 2);
        hitBox.x += (float) xDrawOffset / 2;
    }

    public void update() {
        if (active && doAnimation) {
            updateAnimationTick();
            if (aniIndex + 1 == GetSpriteAmount(objectType))
                setActive(false);
        }
    }

    public boolean doAnimation() {
        return doAnimation;
    }
}
