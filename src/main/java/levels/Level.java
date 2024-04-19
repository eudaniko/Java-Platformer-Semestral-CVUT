// Daniil Nikonenko
// PJV Semestral

package levels;

import entities.Crabby;
import objects.*;
import utils.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.HelpMethods.*;

public class Level {
    private final BufferedImage levelImage;
    private int[][] levelData;
    private ArrayList<Crabby> crabbies;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> gameContainers;
    private ArrayList<Spike> spikes;
    private ArrayList<Cannon> cannons;
    private ArrayList<Grass> grasses;
    private ArrayList<Tree> trees;
    private int maxLevelOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage levelImage) {
        this.levelImage = levelImage;
        createLevelData();
        createEnemies();
        createGameObjects();
        calculateLevelOffsets();
        calculatePlayerSpawn(levelImage);
    }

    private void calculatePlayerSpawn(BufferedImage levelImage) {
        playerSpawn = GetPlayerSpawn(levelImage);
    }

    private void calculateLevelOffsets() {
        int levelTilesWide = levelImage.getWidth();
        int maxTilesOffset = levelTilesWide - Constants.GameConstants.TILES_IN_WIDTH;
        maxLevelOffsetX = Constants.GameConstants.TILES_SIZE * maxTilesOffset;
    }

    private void createGameObjects() {
        potions = GetPotions(levelImage);
        gameContainers = GetGameContainers(levelImage);
        spikes = GetSpikes(levelImage);
        cannons = GetCannons(levelImage);
        grasses = GetGrasses(levelData, this);
        trees = GetTrees(levelImage);
    }

    private void createEnemies() {
        crabbies = GetCrabs(levelImage);
    }

    private void createLevelData() {
        levelData = GetLevelData(levelImage);
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
        return grasses;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }
}
