package entities;

import main.Game;


import static utils.Constants.EnemyConstants.*;

public class Crabby extends Enemy {
    public Crabby(float x, float y) {
        super(x, y , CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitBox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
    }

    public void update(int[][] levelData, Player player) {
        updateMove(levelData, player);
        updateAnimationTick();
    }


    private void updateMove(int[][] levelData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(levelData);

        if (inAir)
            updateInAir(levelData);
            else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:

                    if(canSeePlayer(levelData, player))
                        turnTowardsPlayer(player);
                    if (isPlayerCloseForAttack(player))
                        newState(ATTACK);
                    move(levelData);
                    break;
            }
        }
    }
}
