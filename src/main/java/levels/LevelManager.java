// Daniil Nikonenko
// PJV Semestral

package levels;

import gamestates.GameState;
import main.Game;
import utils.Constants;
import utils.HelpMethods;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.GameConstants.TILES_IN_HEIGHT;
import static utils.Constants.GameConstants.TILES_SIZE;


public class LevelManager {

    private final Game game;
    private BufferedImage[] levelSprite;
    private BufferedImage[] waterSprite;
    private final ArrayList<Level> levels;
    private int lvlIndex = 0, aniTick, aniIndex;
    private int levelIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        createWater();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    public void loadNextLevel() {
        levelIndex++;
        if (levelIndex >= levels.size()) {
            levelIndex = 0;
        }

        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLevelData(newLevel.getLevelData());
        game.getPlaying().setMaxLevelOffsetX(newLevel.getMaxLevelOffset());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
        game.getAudioPlayer().stopEffect();
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = HelpMethods.GetAllLevels();
        for (BufferedImage level : allLevels)
            levels.add(new Level(level));
    }

    private void importOutsideSprites() {
        BufferedImage levelAtlas = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = levelAtlas.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    private void createWater() {
        waterSprite = new BufferedImage[5];
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.TOP_WATER);
        for (int i = 0; i < 4; i++) {
            waterSprite[i] = img.getSubimage(i * 32, 0, 32, 32);
            waterSprite[4] = LoadSave.GetSpriteAtlas(LoadSave.BOTTOM_WATER);
        }
    }

    public void draw(Graphics g, int lvlOffset) {
        for (int j = 0; j < TILES_IN_HEIGHT; j++)
            for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                int x = TILES_SIZE * i - lvlOffset;
                int y = TILES_SIZE * j;
                if (index != 48 && index != 49)
                    g.drawImage(levelSprite[index], x, y, TILES_SIZE, TILES_SIZE, null);
            }
    }

    public void drawWater(Graphics g, int lvlOffset) {
        for (int j = 0; j < TILES_IN_HEIGHT; j++)
            for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                int x = TILES_SIZE * i - lvlOffset;
                int y = TILES_SIZE * j;
                if (index == 48)
                    g.drawImage(waterSprite[aniIndex], x, y, TILES_SIZE, TILES_SIZE, null);
                else if (index == 49)
                    g.drawImage(waterSprite[4], x, y, TILES_SIZE, TILES_SIZE, null);
            }

    }

    private void updateWaterAnimation() {
        aniTick++;
        if (aniTick >= 40) {
            aniTick = 0;
            aniIndex++;

            if (aniIndex >= 4)
                aniIndex = 0;
        }
    }


    public void update() {
        updateWaterAnimation();
    }

    public Level getCurrentLevel() {
        return levels.get(levelIndex);
    }

    public int getCurrentLevelIndex() {
        return levelIndex;
    }

    public int getAmountOfLevels() {
        return levels.size();
    }
}
