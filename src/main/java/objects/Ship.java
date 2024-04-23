package objects;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.SCALE;
import static utils.Constants.ObjectConstants.*;

public class Ship extends GameObject {

    protected BufferedImage[] sprites;
    protected float hoverOffset;
    protected int hoverDir = -1;

    public Ship(int x, int y, int objectType) {
        super(x, y - 14, objectType);
        yDrawOffset = (int) (50 * SCALE);
        initHitBox(SHIP_WIDTH_DEFAULT - 10, 30);

    }


    public void update() {
        super.update();
        updateHover(5, 0.02f);
    }

    public void draw(Graphics g, int xLevelOffset) {
        super.draw(g, xLevelOffset);
        g.drawImage(sprites[getAniIndex()],
                (int) (hitBox.x - xLevelOffset), (int) (hitBox.y - yDrawOffset),
                (int) (SHIP_WIDTH_DEFAULT * SCALE), (int) (SHIP_HEIGHT_DEFAULT * SCALE), null);

    }

    protected void loadSprites() {
        sprites = new BufferedImage[4];
        BufferedImage grassAtlas = LoadSave.GetSpriteAtlas(LoadSave.SHIP_ATLAS);
        for (int i = 0; i < sprites.length; i++)
            sprites[i] = grassAtlas.getSubimage(i * SHIP_WIDTH_DEFAULT, 0, SHIP_WIDTH_DEFAULT, SHIP_HEIGHT_DEFAULT);

    }

    private void updateHover(int maxHoverOffset, float aniSpeed) {
        hoverOffset += (aniSpeed * SCALE * hoverDir);
        if (hoverOffset >= maxHoverOffset)
            hoverDir = -1;
        else if (hoverOffset < 0)
            hoverDir = 1;
        this.hitBox.y = y + hoverOffset;
    }

}
