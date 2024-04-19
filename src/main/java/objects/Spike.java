// Daniil Nikonenko
// PJV Semestral

package objects;

import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.ObjectConstants.SPIKE_HEIGHT;
import static utils.Constants.ObjectConstants.SPIKE_WIDTH;

public class Spike extends GameObject {

    private final BufferedImage sprite;

    public Spike(int x, int y, int objectType) {
        super(x, y, objectType);

        initHitBox(32, 16);
        sprite = LoadSave.GetSpriteAtlas(LoadSave.SPIKE_ATLAS);
        xDrawOffset = 0;
        yDrawOffset = (int) (16 * Constants.GameConstants.SCALE);
        hitBox.y += yDrawOffset;
    }


    @Override
    public void draw(Graphics g, int xLevelOffset) {
        g.drawImage(sprite,
                (int) (hitBox.x - xDrawOffset - xLevelOffset),
                (int) (hitBox.y - yDrawOffset),
                SPIKE_WIDTH, SPIKE_HEIGHT, null);
    }

}
