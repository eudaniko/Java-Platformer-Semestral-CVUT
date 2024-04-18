package levels;

import entities.Player;
import gamestates.Playing;
import objects.*;
import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import static utils.Constants.ObjectConstants.*;
import static utils.HelpMethods.CanCannonSeePlayer;
import static utils.HelpMethods.IsTileSolid;

public class ObjectManager {

    private final Playing playing;
    private BufferedImage[][] potionImages, containerImages;
    private BufferedImage spikeImage, ballImage;
    private BufferedImage[] cannonImages;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> gameContainers;
    private ArrayList<Spike> spikes;
    private ArrayList<Cannon> cannons;
    private final ArrayList<Projectile> projectiles;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImages();
        potions = new ArrayList<>();
        gameContainers = new ArrayList<>();
        cannons = new ArrayList<>();
        projectiles = new ArrayList<>();
    }

    public void loadObjects(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        gameContainers = new ArrayList<>(newLevel.getGameContainers());
        spikes = new ArrayList<>(newLevel.getSpikes());
        cannons = new ArrayList<>(newLevel.getCannons());
    }

    private void loadImages() {
        BufferedImage potionAtlas = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
        potionImages = new BufferedImage[2][GetSpriteAmount(BLUE_POTION)];

        for (int j = 0; j < potionImages.length; j++)
            for (int i = 0; i < potionImages[j].length; i++)
                potionImages[j][i] = potionAtlas.getSubimage(
                        i * POTION_WIDTH_DEFAULT, j * POTION_HEIGHT_DEFAULT,
                        POTION_WIDTH_DEFAULT, POTION_HEIGHT_DEFAULT);


        BufferedImage containerAtlas = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containerImages = new BufferedImage[2][GetSpriteAmount(BOX)];

        for (int j = 0; j < containerImages.length; j++)
            for (int i = 0; i < containerImages[j].length; i++)
                containerImages[j][i] = containerAtlas.getSubimage(
                        i * CONTAINER_WIDTH_DEFAULT, j * CONTAINER_HEIGHT_DEFAULT,
                        CONTAINER_WIDTH_DEFAULT, CONTAINER_HEIGHT_DEFAULT);


        spikeImage = LoadSave.GetSpriteAtlas(LoadSave.SPIKE_ATLAS);

        cannonImages = new BufferedImage[GetSpriteAmount(CANNON_LEFT)];
        BufferedImage cannonAtlas = LoadSave.GetSpriteAtlas(LoadSave.CANNON_ATLAS);

        for (int i = 0; i < cannonImages.length; i++)
            cannonImages[i] = cannonAtlas.getSubimage(i * CANNON_WIDTH_DEFAULT, 0, CANNON_WIDTH_DEFAULT, CANNON_HEIGHT_DEFAULT);

        ballImage = LoadSave.GetSpriteAtlas(LoadSave.BALL);
    }

    public void update() {
        for (Potion p : potions)
            if (p.isActive())
                p.update();

        for (GameContainer gc : gameContainers)
            if (gc.isActive())
                gc.update();

        updateCannons(playing.getLevelManager().getCurrentLevel().getLevelData(), playing.getPlayer());

        for (Projectile p : projectiles)
            if (p.isActive())
                p.update();
        checkProjectilesTouched();
    }

    private void updateCannons(int[][] levelData, Player player) {
        for (Cannon c : cannons) {
            if (!c.doAnimations())
                if (c.getTileY() == player.getTileY())
                    if (isPlayerInRange(c, player))
                        if (isPlayerInFrontOfCannon(c, player))
                            if (CanCannonSeePlayer(player, c, c.getTileY(), levelData))
                                c.setAnimation(true);

            c.update();
            if (c.getAniIndex() == 4 && c.getAniTick() == 0)
                shootCannon(c);
        }
    }

    private void shootCannon(Cannon c) {
        int dir = 1;
        if (c.getObjectType() == CANNON_LEFT)
            dir = -1;

        projectiles.add(new Projectile((int) c.getHitBox().x, (int) c.getHitBox().y, dir));

    }


    private boolean isPlayerInRange(Cannon c, Player player) {
        int absRange = (int) Math.abs(player.getHitBox().x - c.getHitBox().x);
        return absRange <= Constants.GameConstants.TILES_SIZE * 5;
    }

    private boolean isPlayerInFrontOfCannon(Cannon c, Player player) {
        if (c.getObjectType() == CANNON_LEFT) {
            return c.getHitBox().x > player.getHitBox().x;

        } else return c.getHitBox().x < player.getHitBox().x;
    }


    public void draw(Graphics g, int xLevelOffset) {
        drawPotions(g, xLevelOffset);
        drawContainers(g, xLevelOffset);
        drawSpikes(g, xLevelOffset);
        drawCannons(g, xLevelOffset);
        drawProjectiles(g, xLevelOffset);

    }

    private void drawProjectiles(Graphics g, int xLevelOffset) {
        for (Projectile p : projectiles)
            if (p.isActive())
                g.drawImage(ballImage,
                        (int) (p.getHitBox().x - xLevelOffset),
                        (int) (p.getHitBox().y),
                        CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);
    }

    private void drawCannons(Graphics g, int xLevelOffset) {
        for (Cannon c : cannons) {
            int x = (int) (c.getHitBox().x - xLevelOffset);
            int width = CANNON_WIDTH;

            if (c.getObjectType() == CANNON_RIGHT) {
                x += width;
                width *= -1;
            }
            g.drawImage(cannonImages[c.getAniIndex()], x, (int) (c.getHitBox().y), width, CANNON_HEIGHT, null);
        }
    }

    private void drawSpikes(Graphics g, int xLevelOffset) {
        for (Spike s : spikes)
            g.drawImage(spikeImage, (int) (s.getHitBox().x - s.getXDrawOffset() - xLevelOffset), (int) (s.getHitBox().y - s.getYDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
    }

    public void checkPotionsTouched() {
        for (Potion p : potions)
            if (p.isActive())
                if (playing.getPlayer().getHitBox().intersects(p.getHitBox())) {
                    p.setActive(false);
                    applyEffectsToPlayer(p);
                }
    }

    public void checkSpikesTouched() {
        for (Spike s : spikes)
            if (playing.getPlayer().getHitBox().intersects(s.getHitBox()))
                playing.getPlayer().changeHealth(-9999);
    }

    public void checkProjectilesTouched() {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile p = iterator.next();
            if (p.getHitBox().intersects(playing.getPlayer().getHitBox())) {
                playing.getPlayer().changeHealth(-30);
                p.setActive(false);
                iterator.remove();
            } else if (IsTileSolid(p.getTileX(), p.getTileY(), playing.getLevelManager().getCurrentLevel().getLevelData())) {
                p.setActive(false);
                iterator.remove();
            }
        }
    }


    public void applyEffectsToPlayer(Potion p) {
        switch (p.getObjectType()) {
            case RED_POTION:
                playing.getPlayer().changeHealth(RED_POTION_VALUE);
                break;
            case BLUE_POTION:
                playing.getPlayer().changePower(BLUE_POTION);
                break;
        }
    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        for (GameContainer gc : gameContainers)
            if (gc.isActive() && !gc.doAnimation())
                if (gc.getHitBox().intersects(attackBox)) {
                    gc.setAnimation(true);
                    int type = RED_POTION;
                    if (gc.getObjectType() == BARREL)
                        type = BLUE_POTION;
                    potions.add(new Potion((int) (gc.getHitBox().x + gc.getHitBox().width / 2), (int) (gc.getHitBox().y - gc.getHitBox().height / 2), type));
                    return;
                }
    }

    private void drawPotions(Graphics g, int xLevelOffset) {
        for (Potion p : potions)
            if (p.isActive())
                g.drawImage(potionImages[p.getObjectType()][p.getAniIndex()],
                        (int) (p.getHitBox().x - p.getXDrawOffset() - xLevelOffset),
                        (int) (p.getHitBox().y - p.getYDrawOffset()),
                        POTION_WIDTH,
                        POTION_HEIGHT,
                        null);
    }

    private void drawContainers(Graphics g, int xLevelOffset) {
        for (GameContainer c : gameContainers)
            if (c.isActive()) {
                int typeIndex;

                if (c.getObjectType() == BARREL) {
                    typeIndex = 1;
                } else {
                    typeIndex = 0;
                }
                g.drawImage(containerImages[typeIndex][c.getAniIndex()],
                        (int) (c.getHitBox().x - c.getXDrawOffset() - xLevelOffset),
                        (int) (c.getHitBox().y - c.getYDrawOffset()),
                        CONTAINER_WIDTH,
                        CONTAINER_HEIGHT,
                        null);
            }
    }

    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrentLevel());
        for (Potion p : potions)
            p.reset();

        for (GameContainer gc : gameContainers)
            gc.reset();

        for (Cannon c : cannons)
            c.reset();
    }

}
