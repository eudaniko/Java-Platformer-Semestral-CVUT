package ui;

import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.*;
import static utils.LoadSave.COINS_COUNTER;
import static utils.LoadSave.NUMBERS_UI;

public class CoinCounter {

    private int globalCoins;
    private int currentCoins;
    private BufferedImage coinsCounterBG;
    private BufferedImage[] sprites;
    private int aniTick, aniIndex;
    private int numbers, tens, hundreds;

    public CoinCounter() {
        coinsCounterBG = LoadSave.GetSpriteAtlas(COINS_COUNTER);
        loadSprites(NUMBERS_UI, 10, 11, 11);
        currentCoins = globalCoins;

    }


    public void update() {
        updateAnimationTick();
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= Constants.GameConstants.ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex > 10)
                aniIndex = 0;
        }
    }

    protected void loadSprites(String entityAtlas, int aniAmount, int widthDefault, int heightDefault) {
        BufferedImage img = LoadSave.GetSpriteAtlas(entityAtlas);
        sprites = new BufferedImage[aniAmount];

        for (int j = 0; j < aniAmount; j++) {
            sprites[j] = img.getSubimage(j * widthDefault, 0, widthDefault, heightDefault);

        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        g.drawImage(coinsCounterBG, (int) (685 * SCALE), (int) (383 * SCALE), (int) (130 * SCALE), (int) (50 * SCALE), null);

        g.drawImage(sprites[hundreds],
                (int) (733 * SCALE), (int) (400 * SCALE),
                (int) (14 * SCALE), (int) (15 * SCALE), null);

        g.drawImage(sprites[tens],
                (int) (748 * SCALE), (int) (400 * SCALE),
                (int) (14 * SCALE), (int) (15 * SCALE), null);
        g.drawImage(sprites[numbers],
                (int) (763 * SCALE), (int) (400 * SCALE),
                (int) (14 * SCALE), (int) (15 * SCALE), null);
    }

    public void changeCurrentCoins(int deltaCoins) {
        this.currentCoins += deltaCoins;
        numbers += deltaCoins;
        if (numbers >= 10) {
            numbers = 0;
            tens++;
            if (tens >= 10)
                hundreds++;

        } else if (numbers <= 0) {
            numbers = 9;
            tens--;
            if (tens <= 0) {
                tens = 0;
                hundreds--;
                if (hundreds <= 0)
                    hundreds = 0;
            }
        }
    }

    public int getCurrentCoins() {
        return currentCoins;
    }

    public void changeGlobalCoins(int deltaCoins) {
        this.globalCoins += deltaCoins;
    }

    public int getGlobalCoins() {
        return globalCoins;
    }

    public void reset() {
        currentCoins = globalCoins;
        hundreds = globalCoins / 100;
        tens = globalCoins / 10;
        numbers = globalCoins * 10;
    }
}

