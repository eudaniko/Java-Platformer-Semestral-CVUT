// Daniil Nikonenko
// PJV Semestral

package objects;

import gamestates.Playing;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.SCALE;
import static utils.Constants.ObjectConstants.*;

public class GameContainer extends GameObject {

    private BufferedImage[][] sprites;

    public GameContainer(int x, int y, int objectType) {
        super(x, y, objectType);
        doAnimation = false;
        createHitBox();
        loadSprites();
    }

    @Override
    public void update(Playing playing) {
        if (active && doAnimation) {
            updateAnimationTick();
            if (aniIndex + 1 == GetSpriteAmount(objectType))
                setActive(false);
        }

    }

    @Override
    public void draw(Graphics g, int xLevelOffset) {
        int typeIndex;

        if (getObjectType() == BARREL) {
            typeIndex = 1;
        } else {
            typeIndex = 0;
        }
        g.drawImage(sprites[typeIndex][getAniIndex()],
                (int) (hitBox.x - xDrawOffset - xLevelOffset),
                (int) (hitBox.y - yDrawOffset),
                CONTAINER_WIDTH,
                CONTAINER_HEIGHT,
                null);
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

    protected void loadSprites() {
        BufferedImage containerAtlas = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        sprites = new BufferedImage[2][GetSpriteAmount(BOX)];

        for (int j = 0; j < sprites.length; j++)
            for (int i = 0; i < sprites[j].length; i++)
                sprites[j][i] = containerAtlas.getSubimage(
                        i * CONTAINER_WIDTH_DEFAULT, j * CONTAINER_HEIGHT_DEFAULT,
                        CONTAINER_WIDTH_DEFAULT, CONTAINER_HEIGHT_DEFAULT);
    }

    public boolean doAnimation() {
        return doAnimation;
    }
}
