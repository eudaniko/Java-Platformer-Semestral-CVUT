package levels;

import entities.Crabby;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.HelpMethods.*;

public class Level {
    private BufferedImage levelImage;
    private int[][] levelData;
    private ArrayList<Crabby> crabbies;
    private int levelTilesWide;
    private int maxTilesOffset;
    private int maxLevelOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage levelImage) {
        this.levelImage = levelImage;
        createLevelData();
        createEnemies();
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

    public Point getPlayerSpawn(){
        return playerSpawn;
    }
}
