package objects;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.SCALE;
import static utils.Constants.ObjectConstants.*;

public class Tree extends GameObject {

    private BufferedImage[] sprites;
    private int treeWidth, treeHeight;

    public Tree(int x, int y, int objectType) {
        super(x, y, objectType);
        yDrawOffset = 50;
        loadSprites(objectType);
    }

    public void update() {
        updateAnimationTick();
    }

    @Override
    public void draw(Graphics g, int xLevelOffset) {
        g.drawImage(sprites[getAniIndex()],
                x - xLevelOffset, y - yDrawOffset,
                (int) (treeWidth * SCALE), (int) (treeHeight * SCALE), null);
    }

    protected void loadSprites(int objectType) {
        sprites = new BufferedImage[4];
        BufferedImage treeAtlas;

        if (objectType == TREE_THREE) {
            treeAtlas = LoadSave.GetSpriteAtlas(LoadSave.TREE_THREE_ATLAS);
            treeWidth = TREE_THREE_WIDTH_DEFAULT;
            treeHeight = TREE_THREE_HEIGHT_DEFAULT;
        } else {
            treeAtlas = LoadSave.GetSpriteAtlas(LoadSave.TREE_ONE_ATLAS);
            treeWidth = TREE_ONE_WIDTH_DEFAULT;
            treeHeight = TREE_ONE_HEIGHT_DEFAULT;

        }

        for (int i = 0; i < sprites.length; i++)
            sprites[i] = treeAtlas.getSubimage(i * treeWidth, 0, treeWidth, treeHeight);
    }
}
