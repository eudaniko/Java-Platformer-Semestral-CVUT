package levels;

import gamestates.Playing;
import objects.GameContainer;
import objects.Potion;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.ObjectConstants.*;

public class ObjectManager {

    private Playing playing;
    private BufferedImage[][] potionImages, containerImages;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> gameContainers;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImages();
        potions = new ArrayList<>();
        gameContainers = new ArrayList<>();

        potions.add(new Potion(300, 300, BLUE_POTION));
        potions.add(new Potion(400, 300, RED_POTION));
        gameContainers.add(new GameContainer(500, 300, BOX));
        gameContainers.add(new GameContainer(600, 300, BARREL));
    }

    public void loadObjects(Level newLevel) {
        potions = newLevel.getPotions();
        gameContainers = newLevel.getGameContainers();
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


    }

    public void update() {
        for (Potion p : potions)
            if (p.isActive())
                p.update();

        for (GameContainer c : gameContainers)
            if (c.isActive())
                c.update();
        checkObjectTouched(playing.getPlayer().getHitBox());
    }

    public void draw(Graphics g, int xLevelOffset) {
        drawPotions(g, xLevelOffset);
        drawContainers(g, xLevelOffset);

    }

    public void checkObjectTouched(Rectangle2D.Float hitBox) {
        for (Potion p : potions)
            if (p.isActive())
                if (hitBox.intersects(p.getHitBox())) {
                    p.setActive(false);
                    applyEffectsToPlayer(p);
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
            if (gc.isActive())
                if (gc.getHitBox().intersects(attackBox)) {
                    gc.setAnimation(true);
                    int type = RED_POTION;
                    if (gc.getObjectType() == BARREL)
                        type = BLUE_POTION;
                    potions.add(new Potion((int) (gc.getHitBox().x + gc.getHitBox().width / 2), (int) (gc.getHitBox().y + gc.getHitBox().height / 4), type));
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

                switch (c.getObjectType()) {
                    case BARREL:
                        typeIndex = 1;
                        break;
                    case BOX:
                    default:
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
         for (Potion p : potions)
             p.resetAll();

         for (GameContainer gc : gameContainers)
             gc.resetAll();
    }
}
