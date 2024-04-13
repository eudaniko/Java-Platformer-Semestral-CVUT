package entities;

import main.Game;


import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.Directions.RIGHT;
import static utils.Constants.EnemyConstants.*;

public class Crabby extends Enemy {

    //AttackBox
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitBox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int)( 82 * Game.SCALE), (int) (19 * Game.SCALE));
        attackBoxOffsetX =  (int) (Game.SCALE * 30);
    }

    public void update(int[][] levelData, Player player) {
        updateBehaviour(levelData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }


    private void updateBehaviour(int[][] levelData, Player player) {
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
                    if (aniIndex ==3 && !attackChecked)
                        checkPlayerHit( attackBox,player);
                    break;
                case HIT:

                    break;
            }
        }
    }

    public int flipX() {
        if (walkDir == RIGHT)
            return width;
        else
            return 0;

    }

    public int flipW() {
        if (walkDir == RIGHT)
            return -1;
        else
            return 1;
    }

    public void drawAttackBox(Graphics g, int xlevelOffset){
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xlevelOffset), (int)attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }
}
