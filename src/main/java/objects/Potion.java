// Daniil Nikonenko
// PJV Semestral

package objects;

import entities.Player;
import gamestates.Playing;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.SCALE;
import static utils.Constants.ObjectConstants.*;
import static utils.Constants.ObjectConstants.POTION_HEIGHT_DEFAULT;

public class Potion extends GameObject {

    private BufferedImage[][] sprites;


    public Potion(int x, int y, int objectType) {
        super(x, y, objectType);
        doAnimation = true;
        initHitBox(7, 14);
        this.xDrawOffset = (int) (3 * SCALE);
        this.yDrawOffset = (int) (2 * SCALE);
        loadSprites();

    }

    public void update() {
        updateAnimationTick();
        updateHover(0.04f, 3);

    }

    @Override
    public void draw(Graphics g, int xLevelOffset) {
        g.drawImage(sprites[getObjectType()][getAniIndex()],
                (int) (getHitBox().x - getXDrawOffset() - xLevelOffset),
                (int) (getHitBox().y - getYDrawOffset()),
                POTION_WIDTH, POTION_HEIGHT,
                null);
    }

    protected void loadSprites() {
        BufferedImage potionAtlas = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
        sprites = new BufferedImage[2][GetSpriteAmount(BLUE_POTION)];

        for (int j = 0; j < sprites.length; j++)
            for (int i = 0; i < sprites[j].length; i++)
                sprites[j][i] = potionAtlas.getSubimage(
                        i * POTION_WIDTH_DEFAULT, j * POTION_HEIGHT_DEFAULT,
                        POTION_WIDTH_DEFAULT, POTION_HEIGHT_DEFAULT);
    }


}


