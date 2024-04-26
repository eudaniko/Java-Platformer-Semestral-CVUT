package entities;


import gamestates.Playing;
import utils.HelpMethods;
import utils.LoadSave;

import static utils.Constants.Directions.LEFT;
import static utils.Constants.EnemyConstants.*;

public class Shark extends Enemy {

    public Shark(float x, float y) {
        super(x, y, SHARK_WIDTH, SHARK_HEIGHT, SHARK, LoadSave.SHARK_ATLAS, 5, 8, SHARK_WIDTH_DEFAULT, SHARK_HEIGHT_DEFAULT);
        initHitBox(18, 22);
        walkSpeed = 1;
        xDrawOffset = SHARK_DRAWOFFSET_X;
        yDrawOffset = SHARK_DRAWOFFSET_Y;
        initAttackBox(20, 20, 40, 20);
    }

    protected void update(int[][] lvlData, Playing playing) {
        updateBehavior(lvlData, playing);
        updateAnimationTick();
        updateAttackBoxDir(walkDir);
    }

    private void updateBehavior(int[][] levelData, Playing playing) {
        if (firstUpdate)
            firstUpdateCheck(levelData);

        if (inAir)
            inAirChecks(levelData, playing);
        else {
            switch (state) {
                case IDLE:
                    if (HelpMethods.IsFloor(hitBox, levelData))
                        newState(RUNNING);
                    else
                        inAir = true;
                    break;
                case RUNNING:
                    if (canSeePlayer(levelData, playing.getPlayer())) {
                        turnTowardsPlayer(playing.getPlayer());
                        if (isPlayerCloseForAttack(playing.getPlayer()))
                            newState(ATTACK);
                    }
                    move(levelData);
                    break;
                case ATTACK:
                    if (aniIndex == 0)
                        attackChecked = false;
                    else if (aniIndex == 3) {
                        if (!attackChecked)
                            checkPlayerHit(attackBox, playing.getPlayer());
                        attackMove(levelData, playing);
                    }

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

    protected void attackMove(int[][] lvlData, Playing playing) {
        float xSpeed = 0;

        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (HelpMethods.CanMoveHere(hitBox.x + xSpeed * 4, hitBox.y, hitBox.width, hitBox.height, lvlData))
            if (HelpMethods.IsFloor(hitBox, xSpeed * 4, walkDir, lvlData)) {
                hitBox.x += xSpeed * 4;
                return;
            }
        newState(IDLE);
//        playing.addDialogue((int) hitBox.x, (int) hitBox.y, EXCLAMATION);
    }

}
