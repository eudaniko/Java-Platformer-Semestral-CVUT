// Daniil Nikonenko
// PJV Semestral

package entities;

import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.Constants.GameConstants.SCALE;

public class Crabby extends Enemy {

    //AttackBox
    private int attackBoxOffsetX;

    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, 0, LoadSave.CRABBY_ATLAS, 5, 9, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
        initHitBox(22, 19);
        active = true;
        xDrawOffset = CRABBY_DRAW_OFFSET_X;
        yDrawOffset = CRABBY_DRAW_OFFSET_Y;
        initAttackBox();
        walkSpeed = 0.3f * SCALE;
    }

    private void initAttackBox() {
        this.hasAttackBox = true;
        attackBox = new Rectangle2D.Float(x, y, (int) (82 * SCALE), (int) (19 * SCALE));
        attackBoxOffsetX = (int) (SCALE * 30);
    }

    public void update(int[][] levelData, Player player) {
        updateBehaviour(levelData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    public void draw(Graphics g, int xLevelOffset) {
        super.draw(g, xLevelOffset);
    }

    private void updateAttackBox() {
        attackBox.x = hitBox.x - attackBoxOffsetX;
        attackBox.y = hitBox.y;
    }


    private void updateBehaviour(int[][] levelData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(levelData);

        if (inAir)
            updateInAir(levelData);
        else {
            switch (state) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer(levelData, player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseForAttack(player))
                            newState(ATTACK);
                    }
                    move(levelData);
                    break;
                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 3 && !attackChecked)
                        checkPlayerHit(attackBox, player);
                    break;
                case HIT:

                    break;
            }
        }
    }
}
