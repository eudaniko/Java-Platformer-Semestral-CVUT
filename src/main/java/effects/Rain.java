package effects;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.Game;
import utils.LoadSave;

import static utils.Constants.GameConstants.GAME_HEIGHT;
import static utils.Constants.GameConstants.GAME_WIDTH;

public class Rain {

    private Point2D.Float[] drops;
    private Random rand;
    private float rainSpeed = 1f;
    private BufferedImage rainParticle;

    // Worth knowing, adding particles this way can cost a lot in
    // computer power. Disable it if the game lags.
    public Rain() {
        rand = new Random();
        drops = new Point2D.Float[200];
        rainParticle = LoadSave.GetSpriteAtlas(LoadSave.RAIN_PARTICLE);
        initDrops();
    }

    private void initDrops() {
        for (int i = 0; i < drops.length; i++)
            drops[i] = getRndPos();
    }

    private Point2D.Float getRndPos() {
        return new Point2D.Float((int) getNewX(0), rand.nextInt(GAME_HEIGHT));
    }

    public void update(int xLvlOffset) {
        for (Point2D.Float p : drops) {
            p.y += rainSpeed;
            if (p.y >= GAME_HEIGHT) {
                p.y = -20;
                p.x = getNewX(xLvlOffset);
            }
        }
    }

    private float getNewX(int xLvlOffset) {
        float value = (-GAME_WIDTH) + rand.nextInt((int) (GAME_WIDTH * 3f)) + xLvlOffset;
        return value;
    }

    public void draw(Graphics g, int xLvlOffset) {
        for (Point2D.Float p : drops)
            g.drawImage(rainParticle, (int) p.getX() - xLvlOffset, (int) p.getY(), 3, 12, null);
    }

}
