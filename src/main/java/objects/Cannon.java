// Daniil Nikonenko
// PJV Semestral

package objects;

import entities.Player;
import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.GameConstants.SCALE;
import static utils.Constants.GameConstants.TILES_SIZE;
import static utils.Constants.ObjectConstants.*;
import static utils.HelpMethods.CanCannonSeePlayer;

public class Cannon extends GameObject {
    private BufferedImage[] sprites;
    protected int tileX, tileY;


    public Cannon(int x, int y, int objectType) {
        super(x, y, objectType);
        initHitBox(40, 26);
        hitBox.x -= (int) (4 * SCALE);
        hitBox.y += (int) (6 * SCALE);
        tileY = (int) (hitBox.y / Constants.GameConstants.TILES_SIZE);
        tileX = (int) (hitBox.x / Constants.GameConstants.TILES_SIZE);
        loadSpites();
    }

    public void update(Player player, int[][] levelData, ArrayList<Projectile> projectiles) {
        if (doAnimation)
            updateAnimationTick();
        if (tileY == player.getTileY())
            if (isPlayerInRange(this, player))
                if (isPlayerInFrontOfCannon(player))
                    if (CanCannonSeePlayer(player, this, tileY, levelData))
                        setAnimation(true);

        if (aniIndex == 4 && aniTick == 0)
            shootCannon(this, projectiles);
    }

    private boolean isPlayerInRange(Cannon c, Player player) {
        int absRange = (int) Math.abs(player.getHitBox().x - c.getHitBox().x);
        return absRange <= TILES_SIZE * 5;
    }

    private boolean isPlayerInFrontOfCannon(Player player) {
        if (objectType == CANNON_LEFT) {
            return hitBox.x > player.getHitBox().x;

        } else return hitBox.x < player.getHitBox().x;
    }

    private void shootCannon(Cannon c, ArrayList<Projectile> projectiles) {
        int dir = 1;
        if (c.getObjectType() == CANNON_LEFT)
            dir = -1;
        projectiles.add(new Projectile((int) c.getHitBox().x, (int) c.getHitBox().y, dir));
    }

    @Override
     public void draw(Graphics g, int xLevelOffset) {
        int x = (int) (hitBox.x - xLevelOffset);
        int width = CANNON_WIDTH;

        if (objectType == CANNON_RIGHT) {
            x += width;
            width *= -1;
        }
        g.drawImage(sprites[aniIndex], x, (int) (hitBox.y), width, CANNON_HEIGHT, null);
    }

    protected void loadSpites() {
        sprites = new BufferedImage[GetSpriteAmount(5)];
        BufferedImage cannonAtlas = LoadSave.GetSpriteAtlas(LoadSave.CANNON_ATLAS);

        for (int i = 0; i < sprites.length; i++)
            sprites[i] = cannonAtlas.getSubimage(i * CANNON_WIDTH_DEFAULT, 0, CANNON_WIDTH_DEFAULT, CANNON_HEIGHT_DEFAULT);
    }

    public int getTileY() {
        return tileY;
    }

    public int getTileX() {
        return tileX;
    }
}
