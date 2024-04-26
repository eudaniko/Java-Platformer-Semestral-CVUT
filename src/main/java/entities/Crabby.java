// Daniil Nikonenko
// PJV Semestral

package entities;

import gamestates.Playing;
import utils.HelpMethods;
import utils.LoadSave;

import java.awt.*;

import static utils.Constants.EnemyConstants.*;
import static utils.Constants.GameConstants.SCALE;

public class Crabby extends Enemy {

    //AttackBox
    private int attackBoxOffsetX = (int) (30 * SCALE);

    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, 0, LoadSave.CRABBY_ATLAS, 5, 9, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
        initHitBox(22, 19);
        active = true;
        xDrawOffset = CRABBY_DRAW_OFFSET_X;
        yDrawOffset = CRABBY_DRAW_OFFSET_Y;
        initAttackBox(x, y, 82, 19);
        walkSpeed = 0.3f * SCALE;
    }

    protected void update(int[][] levelData, Playing playing) {
        updateBehaviour(levelData, playing.getPlayer());
        updateAnimationTick();
        updateAttackBoxDir();
    }

    public void draw(Graphics g, int xLevelOffset) {
        super.draw(g, xLevelOffset);
    }

    protected void updateAttackBoxDir() {
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
                    if (HelpMethods.IsFloor(hitBox, levelData))
                        newState(RUNNING);
                    else
                        inAir = true;
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
                    //TODO add enemy interactions with spikes
//                    if (aniIndex <= GetSpriteAmount(enemyType, state) - 2)
//                        pushBack(pushBackDir, levelData, 2f);
//                    updatePushBackDrawOffset();
                    break;
            }
        }
    }
}
