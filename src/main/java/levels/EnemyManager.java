package levels;

import entities.Crabby;
import entities.Player;
import gamestates.Playing;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.*;
import static utils.Constants.Player.PLAYER_DAMAGE;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] crabbyArray;
    private ArrayList<Crabby> crabbies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImages();
        addEnemies();

    }

    private void addEnemies() {
        crabbies = LoadSave.GetCrabs();
        System.out.println("size of crabs: " + crabbies.size());
    }

    public void update(int[][] levelData, Player player) {
        for (Crabby c : crabbies)
            if (c.isActive())
                c.update(levelData, player);

    }

    public void draw(Graphics g, int xLevelOffset) {
        drawCrabs(g, xLevelOffset);
        for (Crabby c : crabbies)
            c.draw(g, xLevelOffset);


    }

    private void drawCrabs(Graphics g, int xLevelOffset) {
        for (Crabby c : crabbies)
            if (c.isActive()) {
                g.drawImage(crabbyArray[c.getEnemyState()][c.getAniIndex()],
                        (int) c.getHitbox().x - xLevelOffset - CRABBY_DRAW_OFFSET_X + c.flipX(),
                        (int) c.getHitbox().y - CRABBY_DRAW_OFFSET_Y,
                        CRABBY_WIDTH * c.flipW(),
                        CRABBY_HEIGHT, null);
//                c.drawAttackBox(g, xLevelOffset);
            }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Crabby c : crabbies)
            if (c.isActive())
                if (attackBox.intersects(c.getHitbox())) {
                    c.hurt(PLAYER_DAMAGE);
                    System.out.println("HIT CRAB");
                    return;
                }
    }

    private void loadEnemyImages() {
        crabbyArray = new BufferedImage[5][9];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_ATLAS);
        for (int j = 0; j < crabbyArray.length; j++)
            for (int i = 0; i < crabbyArray[j].length; i++)
                crabbyArray[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
    }

    public void resetAllEnemies() {
        for (Crabby c : crabbies)
            c.resetEnemy();
    }
}
