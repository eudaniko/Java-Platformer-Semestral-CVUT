package levels;

import entities.Crabby;
import main.Game;
import objects.GameContainer;
import objects.Potion;
import objects.Spike;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.HelpMethods.*;

public class Level {
    private BufferedImage levelImage;
    private int[][] levelData;
    private ArrayList<Crabby> crabbies;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> gameContainers;
    private ArrayList<Spike> spikes;
    private int levelTilesWide;
    private int maxTilesOffset;
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
        levelTilesWide = levelImage.getWidth();
        maxTilesOffset = levelTilesWide - Game.TILES_IN_WIDTH;
        maxLevelOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createGameObjects(){
        potions = GetPotions(levelImage);
        gameContainers = GetGameContainers(levelImage);
        spikes = GetSpikes(levelImage);
    }

    private void createEnemies() {
        crabbies = GetCrabs(levelImage);
    }

    private void createLevelData() {
        levelData = GetLevelData(levelImage);
    }

    public int getSpriteIndex(int x, int y) {
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
    public ArrayList<GameContainer> getGameContainers(){
        return gameContainers;
    }
    public ArrayList<Spike> getSpikes(){
        return spikes;
    }

    public Point getPlayerSpawn(){
        return playerSpawn;
    }
}
