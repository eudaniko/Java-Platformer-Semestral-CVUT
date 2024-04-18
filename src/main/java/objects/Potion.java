// Daniil Nikonenko
// PJV Semestral

package objects;

import static utils.Constants.GameConstants.SCALE;

public class Potion extends GameObject {

    private float hoverOffset;
    private final int maxHoverOffset;
    private int hoverDir = -1;


    public Potion(int x, int y, int objectType) {
        super(x, y, objectType);
        doAnimation = true;
        initHitBox(7, 14);
        this.xDrawOffset = (int) (3 * SCALE);
        this.yDrawOffset = (int) (2 * SCALE);
        maxHoverOffset = (int) (3 * SCALE);

    }

    public void update() {
        updateAnimationTick();
        updateHover();
    }

    private void updateHover() {
        hoverOffset += (0.04f * SCALE * hoverDir);
        if (hoverOffset >= maxHoverOffset)
            hoverDir = -1;
        else if (hoverOffset < 0)
            hoverDir = 1;
        this.hitBox.y = y + hoverOffset;
    }


    public void reset() {
        active = true;
        aniTick = 0;
        aniIndex = 0;
        doAnimation = true;
    }
}


