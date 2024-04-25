package entities;


import gamestates.Playing;
import utils.LoadSave;

import java.awt.*;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethods.CanMoveHere;
import static utils.HelpMethods.IsFloor;

public class Pinkstar extends Enemy {

    private boolean preRoll = true;
    private int tickSinceLastDmgToPlayer;
    private int tickAfterRollInIdle;
    private int rollDurationTick, rollDuration = 300;
    private int walkSpeed = 1;

    public Pinkstar(float x, float y) {
        super(x, y, PINKSTAR_WIDTH, PINKSTAR_HEIGHT,
                PINKSTAR, LoadSave.PINKSTAR_ATLAS,
                5, 8,
                PINKSTAR_WIDTH_DEFAULT, PINKSTAR_HEIGHT_DEFAULT);
        this.active = true;
        xDrawOffset = PINKSTAR_DRAW_OFFSET_X;
        yDrawOffset = PINKSTAR_DRAW_OFFSET_Y;

        enemyType = PINKSTAR;
        initHitBox(17, 21);
    }

    public void update(int[][] lvlData, Playing playing) {
        updateBehavior(lvlData, playing);
        updateAnimationTick();
        checkDrawDir(walkDir);
    }

    public void draw(Graphics g, int xLevelOffset) {
        super.draw(g, xLevelOffset);
    }

    private void updateBehavior(int[][] levelData, Playing playing) {
        if (firstUpdate)
            firstUpdateCheck(levelData);

        if (inAir)
            inAirChecks(levelData, playing);
        else {
            switch (state) {
                case IDLE:
                    preRoll = true;
                    if (tickAfterRollInIdle >= 120) {
                        if (IsFloor(hitBox, walkSpeed, walkDir, levelData))
                            newState(RUNNING);
                        else
                            inAir = true;
                        tickAfterRollInIdle = 0;
                        tickSinceLastDmgToPlayer = 60;
                    } else
                        tickAfterRollInIdle++;
                    break;
                case RUNNING:
                    if (canSeePlayer(levelData, playing.getPlayer())) {
                        newState(ATTACK);
                        setWalkDir(playing.getPlayer());
                    }
                    move(levelData, playing);
                    break;
                case ATTACK:
                    if (preRoll) {
                        if (aniIndex >= 3)
                            preRoll = false;
                    } else {
                        move(levelData, playing);
                        checkDmgToPlayer(playing.getPlayer());
                        checkRollOver(playing);
                    }
                    break;
                case HIT:
                    if (aniIndex <= GetSpriteAmount(enemyType, state) - 2)
                        pushBack(pushBackDir, levelData, 2f);
                    updatePushBackDrawOffset();
                    tickAfterRollInIdle = 120;

                    break;
            }
        }
    }

    private void checkDmgToPlayer(Player player) {
        if (hitBox.intersects(player.getHitBox()))
            if (tickSinceLastDmgToPlayer >= 60) {
                tickSinceLastDmgToPlayer = 0;
                player.changeHealth(-GetEnemyDamage(enemyType), this);
            } else
                tickSinceLastDmgToPlayer++;
    }

    private void setWalkDir(Player player) {
        if (player.getHitBox().x > hitBox.x)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    protected void move(int[][] lvlData, Playing playing) {
        float xSpeed = 0;

        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (state == ATTACK)
            xSpeed *= 2;

        if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData))
            if (IsFloor(hitBox, xSpeed, walkDir, lvlData)) {
                hitBox.x += xSpeed;
                return;
            }

        if (state == ATTACK) {
            rollOver(playing);
            rollDurationTick = 0;
        }

        changeWalkDir();

    }

    private void checkRollOver(Playing playing) {
        rollDurationTick++;
        if (rollDurationTick >= rollDuration) {
            rollOver(playing);
            rollDurationTick = 0;
        }
    }

    private void rollOver(Playing playing) {
        newState(IDLE);
//		playing.addDialogue((int) hitbox.x, (int) hitbox.y, QUESTION);
    }

}
