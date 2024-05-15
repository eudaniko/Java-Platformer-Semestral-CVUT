package objects;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.*;
import static utils.Constants.ObjectConstants.*;

public class Rum extends GameObject {

    private BufferedImage[] sprites;


    public Rum(int x, int y, int objectType) {
        super(x, y, objectType);
        doAnimation = true;
        hoverEffect = true;
        hoverSpeed = 0.02f;
        maxHoverOffset = 5;
        initHitBox(7, 14);
        this.xDrawOffset = (int) (3 * SCALE);
        this.yDrawOffset = (int) (2 * SCALE);
        loadSprites();
    }

    @Override
    public void draw(Graphics g, int xLevelOffset) {
        g.drawImage(sprites[getAniIndex()],
                (int) (getHitBox().x - getXDrawOffset() - xLevelOffset),
                (int) (getHitBox().y - getYDrawOffset()),
                TILES_SIZE, TILES_SIZE,
                null);
    }

    @Override
    protected void loadSprites() {
        BufferedImage potionAtlas = LoadSave.GetSpriteAtlas(LoadSave.RUM_ATLAS);
        sprites = new BufferedImage[GetSpriteAmount(RUM)];

        for (int j = 0; j < sprites.length; j++)
            sprites[j] = potionAtlas.getSubimage(
                    j * TILES_DEFAULT_SIZE, 0,
                    TILES_DEFAULT_SIZE, TILES_DEFAULT_SIZE);
    }
}
