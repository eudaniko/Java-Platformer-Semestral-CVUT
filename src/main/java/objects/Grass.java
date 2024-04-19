package objects;


import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.TILES_SIZE;
import static utils.Constants.ObjectConstants.GRASS_WIDTH_HEIGHT_DEFAULT;

public class Grass extends GameObject {

    private BufferedImage[] sprites;

    public Grass(int x, int y, int objectType) {
        super(x, y, objectType);
        loadSprites();
    }

    public void draw(Graphics g, int xLevelOffset) {
        g.drawImage(sprites[objectType],
                x * TILES_SIZE - xLevelOffset,
                y * TILES_SIZE - TILES_SIZE,
                TILES_SIZE, TILES_SIZE, null);
    }


    protected void loadSprites() {
        sprites = new BufferedImage[2];
        BufferedImage grassAtlas = LoadSave.GetSpriteAtlas(LoadSave.GRASS_ATLAS);
        for (int i = 0; i < sprites.length; i++)
            sprites[i] = grassAtlas.getSubimage(i * GRASS_WIDTH_HEIGHT_DEFAULT, 0, GRASS_WIDTH_HEIGHT_DEFAULT, GRASS_WIDTH_HEIGHT_DEFAULT);

    }

}
