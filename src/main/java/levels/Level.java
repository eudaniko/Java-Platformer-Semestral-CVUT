// Daniil Nikonenko
// PJV Semestral

package levels;

import entities.Crabby;
import objects.*;
import utils.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import static utils.Constants.EnemyConstants.CRABBY;
import static utils.Constants.GameConstants.TILES_SIZE;
import static utils.Constants.ObjectConstants.*;
import static utils.Constants.PlayerConstants.PLAYER_GREEN_INDEX;

public class Level {
    private final BufferedImage levelImage;
    private final int[][] levelData;
    private final ArrayList<Crabby> crabbies = new ArrayList<>();
    private ArrayList<Potion> potions = new ArrayList<>();
    private ArrayList<GameContainer> gameContainers = new ArrayList<>();
    private ArrayList<Spike> spikes = new ArrayList<>();
    private ArrayList<Cannon> cannons = new ArrayList<>();
    private ArrayList<Grass> grass = new ArrayList<>();
    private ArrayList<Tree> trees = new ArrayList<>();
    private int maxLevelOffsetX;
    private Point playerSpawn;
    private Random random = new Random();

    public Level(BufferedImage levelImage) {
        this.levelImage = levelImage;
        levelData = new int[levelImage.getHeight()][levelImage.getWidth()];
        loadLevel();
        calculateLevelOffsets();
    }

    private void loadLevel() {

        // Looping through the image colors just once. Instead of one per
        // object/enemy/etc..
        // Removed many methods in HelpMethods class.

        for (int y = 0; y < levelImage.getHeight(); y++)
            for (int x = 0; x < levelImage.getWidth(); x++) {
                Color c = new Color(levelImage.getRGB(x, y));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                loadLevelData(red, x, y);
                loadEntities(green, x, y);
                loadObjects(blue, x, y);
            }
    }


    private void loadLevelData(int redValue, int x, int y) {
        if (redValue >= 50)
            levelData[y][x] = 0;
        else
            levelData[y][x] = redValue;
        switch (redValue) {
            case 0, 1, 2, 3, 30, 31, 33, 34, 35, 36, 37, 38, 39 -> {
                int grassType = (random.nextInt(-(GRASS_RARELY), 2));
                if (grassType >= 0)
                    grass.add(new Grass((x * TILES_SIZE), (y * TILES_SIZE) - TILES_SIZE, grassType));
            }
        }
    }

    private void loadEntities(int greenValue, int x, int y) {
        switch (greenValue) {
            case CRABBY -> crabbies.add(new Crabby(x * TILES_SIZE, y * TILES_SIZE));
            case PLAYER_GREEN_INDEX -> playerSpawn = new Point(x * TILES_SIZE, y * TILES_SIZE);
        }
    }

    private void loadObjects(int blueValue, int x, int y) {
        switch (blueValue) {
            case RED_POTION, BLUE_POTION -> potions.add(new Potion(x * TILES_SIZE, y * TILES_SIZE, blueValue));
            case BOX, BARREL -> gameContainers.add(new GameContainer(x * TILES_SIZE, y * TILES_SIZE, blueValue));
            case SPIKE -> spikes.add(new Spike(x * TILES_SIZE, y * TILES_SIZE, SPIKE));
            case CANNON_LEFT, CANNON_RIGHT -> cannons.add(new Cannon(x * TILES_SIZE, y * TILES_SIZE, blueValue));
            case TREE_ONE, TREE_THREE -> trees.add(new Tree(x * TILES_SIZE, y * TILES_SIZE, blueValue));
        }
    }


    private void calculateLevelOffsets() {
        int levelTilesWide = levelImage.getWidth();
        int maxTilesOffset = levelTilesWide - Constants.GameConstants.TILES_IN_WIDTH;
        maxLevelOffsetX = Constants.GameConstants.TILES_SIZE * maxTilesOffset;
    }

    public int getTileSpriteIndex(int x, int y) {
        return levelData[y][x];
    }

    public int[][] getLevelData() {
        return levelData;
    }

    public int getMaxLevelOffset() {
        return maxLevelOffsetX;
    }

    public ArrayList<Crabby> getCrabbies() {
        return crabbies;
    }

    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public ArrayList<GameContainer> getGameContainers() {
        return gameContainers;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }

    public ArrayList<Cannon> getCannons() {
        return cannons;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Grass> getGrasses() {
        return grass;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public int getSpriteIndex(int x, int y) {
        return levelData[y][x];
    }
}
