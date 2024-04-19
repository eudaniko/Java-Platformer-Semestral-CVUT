package objects;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.TILES_SIZE;
import static utils.Constants.ObjectConstants.*;

public class Tree extends GameObject {

    private BufferedImage[] sprites;
    public Tree(int x, int y, int objectType) {
        super(x, y, objectType);
        yDrawOffset = 50;
        loadSprites();
    }

    @Override
    public void draw(Graphics g, int xLevelOffset) {
        g.drawImage(sprites[getAniIndex()],
                x * TILES_SIZE - xLevelOffset, y * TILES_SIZE - yDrawOffset,
                S_TREE_WIDTH, S_TREE_HEIGHT, null);
    }

    protected void loadSprites(){
        sprites = new BufferedImage[4];
        BufferedImage treeAtlas = LoadSave.GetSpriteAtlas(LoadSave.STRAIGHT_TREE_ATLAS);

        for (int i = 0; i < sprites.length; i++)
            sprites[i] = treeAtlas.getSubimage(i * S_TREE_WIDTH_DEFAULT, 0, S_TREE_WIDTH_DEFAULT, S_TREE_HEIGHT_DEFAULT);
    }
}
