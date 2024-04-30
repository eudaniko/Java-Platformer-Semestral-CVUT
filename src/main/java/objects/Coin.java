package objects;

import gamestates.Playing;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.SCALE;
import static utils.Constants.ObjectConstants.*;

public class Coin extends GameObject {
    private BufferedImage[] sprites;

    public Coin(int x, int y, int objectType) {
        super(x, y, objectType);
        initHitBox(COIN_SIZE, COIN_SIZE);
    }

    public void update(Playing playing) {
        super.update(playing);
        updateHover(0.04f, 10);
        if (hitBox.intersects(playing.getPlayer().getHitBox()))
            if (isActive()) {
                playing.getCoinCounter().changeCurrentCoins(+1);
                setActive(false);

            }
    }

    public void draw(Graphics g, int xLevelOffset) {
        g.drawImage(sprites[aniIndex], x - xLevelOffset, y, COIN_SIZE, COIN_SIZE, null);
    }


    protected void loadSprites() {
        sprites = new BufferedImage[4];
        BufferedImage grassAtlas = LoadSave.GetSpriteAtlas(LoadSave.GOLD_COIN);
        for (int i = 0; i < sprites.length; i++)
            sprites[i] = grassAtlas.getSubimage(i * COIN_SIZE_DEFAULT, 0, COIN_SIZE_DEFAULT, COIN_SIZE_DEFAULT);

    }
}
