// Daniil Nikonenko
// PJV Semestral

package entities;

import gamestates.Playing;
import levels.Level;
import levels.LevelManager;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.*;
import static utils.Constants.PlayerConstants.PLAYER_DAMAGE;
import static utils.Constants.PlayerConstants.POWER_ATTACK_DAMAGE;

public class EnemyManager {

    private final Playing playing;
    private final LevelManager levelManager;
    private BufferedImage[][] crabbyArray;
    private BufferedImage[][] pinkstarArray;
    private ArrayList<Crabby> crabbies = new ArrayList<>();
    private ArrayList<Pinkstar> pinkstars = new ArrayList<>();
    private ArrayList<Shark> sharks = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        this.levelManager = playing.getLevelManager();
        loadEnemyImages();

    }

    public void loadEnemies(Level level) {
        crabbies = level.getCrabbies();
        pinkstars = level.getPinkStars();
        sharks = level.getSharks();
    }

    public void update(int[][] levelData) {
        boolean isCrabbiesActive = updateActiveEnemies(levelData, crabbies);
        boolean isPinkStartsActive = updateActiveEnemies(levelData, pinkstars);
        boolean isSharksActive = updateActiveEnemies(levelData, sharks);

        if (!isCrabbiesActive && !isPinkStartsActive && !isSharksActive) {
            if (levelManager.getCurrentLevelIndex() >= levelManager.getAmountOfLevels() - 1)
                playing.setGameComplete(true);
            else
                playing.setLevelComplete(true);
        }

    }

    private boolean updateActiveEnemies(int[][] levelData, ArrayList<? extends Enemy> enemies) {
        boolean isAnyActive = false;
        for (Enemy enemy : enemies)
            if (enemy.isActive()) {
                enemy.update(levelData, playing);
                isAnyActive = true;
            }
        return isAnyActive;
    }

    public void draw(Graphics g, int xLevelOffset) {
        drawEntities(g, xLevelOffset, crabbies);
        drawEntities(g, xLevelOffset, pinkstars);
        drawEntities(g, xLevelOffset, sharks);

    }

    private void drawEntities(Graphics g, int xLevelOffset, ArrayList<? extends Entity> list) {
        for (Entity entity : list)
            entity.draw(g, xLevelOffset);
    }


    public void HitNearEnemies(Rectangle2D.Float attackBox, boolean powerAttackActive) {
        checkEnemiesHit(attackBox, powerAttackActive, crabbies);
        checkEnemiesHit(attackBox, powerAttackActive, pinkstars);
        checkEnemiesHit(attackBox, powerAttackActive, sharks);
    }

    private void checkEnemiesHit(Rectangle2D.Float attackBox, boolean powerAttackActive, ArrayList<? extends Enemy> list) {
        for (Enemy enemy : list)
            if (enemy.isActive())
                if (attackBox.intersects(enemy.getHitBox()) && enemy.getEntityState() != DEAD) {
                    if (powerAttackActive)
                        enemy.changeHealth(-POWER_ATTACK_DAMAGE, playing.getPlayer());
                    else
                        enemy.changeHealth(-PLAYER_DAMAGE, playing.getPlayer());
                    return;
                }

    }

    private void loadEnemyImages() {
        crabbyArray = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_ATLAS);
        for (int j = 0; j < crabbyArray.length; j++)
            for (int i = 0; i < crabbyArray[j].length; i++)
                crabbyArray[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);

        pinkstarArray = new BufferedImage[5][8];
        temp = LoadSave.GetSpriteAtlas(LoadSave.PINKSTAR_ATLAS);
        for (int j = 0; j < pinkstarArray.length; j++)
            for (int i = 0; i < pinkstarArray[j].length; i++)
                pinkstarArray[j][i] = temp.getSubimage(i * PINKSTAR_WIDTH_DEFAULT, j * PINKSTAR_HEIGHT_DEFAULT, PINKSTAR_WIDTH_DEFAULT, PINKSTAR_HEIGHT_DEFAULT);
    }

    public void resetAllEnemies() {
        for (Crabby c : crabbies)
            c.resetEnemy();
        for (Pinkstar p : pinkstars)
            p.resetEnemy();
        for (Shark s : sharks)
            s.resetEnemy();
    }
}
