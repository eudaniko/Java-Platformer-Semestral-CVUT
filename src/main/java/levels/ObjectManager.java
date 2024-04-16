package levels;

import gamestates.Playing;
import objects.Container;
import objects.Potion;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.ObjectConstants.*;

public class ObjectManager {

    private Playing playing;
    private BufferedImage[][] potionImages, containerImages;
    private ArrayList<Potion> potions;
    private ArrayList<Container> containers;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        loadImages();
        potions = new ArrayList<>();
        containers = new ArrayList<>();

        potions.add(new Potion(300, 300, BLUE_POTION));
        potions.add(new Potion(400, 300, RED_POTION));
        containers.add(new Container(500, 300, BOX));
        containers.add(new Container(600, 300, BARREL));
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

        for (Container c : containers)
            if (c.isActive())
                c.update();

    }

    public void draw(Graphics g, int xLevelOffset) {
        drawPotions(g, xLevelOffset);
        drawContainers(g, xLevelOffset);

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
        for (Container c : containers)
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
}
