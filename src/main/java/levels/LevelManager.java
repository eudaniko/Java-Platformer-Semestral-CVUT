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

import static utils.Constants.GameConstants.TILES_SIZE;


public class LevelManager {

    private final Game game;
    private BufferedImage[] levelSprite;
    private final ArrayList<Level> levels;
    private int levelIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    public void loadNextLevel(){
        levelIndex++;
        if (levelIndex >= levels.size()){
            levelIndex =0;
            System.out.println("No more levels, The Game completed!");
            GameState.state = GameState.MENU;
        }

        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLevelData(newLevel.getLevelData());
        game.getPlaying().setMaxLevelOffsetX(newLevel.getMaxLevelOffset());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
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

    public void draw(Graphics g, int levelOffset) {
        for (int j = 0; j < Constants.GameConstants.TILES_IN_HEIGHT; j++)
            for (int i = 0; i < levels.get(levelIndex).getLevelData()[0].length; i++) {
                int index = levels.get(levelIndex).getTileSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILES_SIZE * i - levelOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
            }
    }





    public void update() {

    }

    public Level getCurrentLevel(){
        return levels.get(levelIndex);
    }

    public int getAmountOfLevels(){
        return levels.size();
    }
}
