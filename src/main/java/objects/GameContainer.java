package objects;

import main.Game;


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
            xDrawOffset = (int) (7 * Game.SCALE);
            yDrawOffset = (int) (12 * Game.SCALE);
        } else if (objectType == BARREL) {
            initHitBox(23, 15);
            xDrawOffset = (int) (8 * Game.SCALE);
            yDrawOffset = (int) (5 * Game.SCALE);
        }

        hitBox.y += yDrawOffset + (int) (Game.SCALE * 2);
        hitBox.x += xDrawOffset / 2;
    }

    public void update() {
        if (active && doAnimation) {
            updateAnimationTick();
            if (aniIndex + 1 == GetSpriteAmount(objectType))
                setActive(false);
        }
    }

    public void resetAll() {
        active = true;
        aniTick = 0;
        aniIndex = 0;
        doAnimation = false;
    }
}
