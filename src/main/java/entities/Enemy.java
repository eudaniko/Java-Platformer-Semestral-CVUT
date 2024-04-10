package entities;


import main.Game;

import java.awt.*;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethods.*;

public abstract class Enemy extends Entity {
    private int aniIndex, aniTick, aniSpeed = 25;
    private int enemyState, enemyType;
    private boolean inAir, firstUpdate = true;
    private float gravity = 0.4f * Game.SCALE;
    private float fallSpeed = 5;
    private float walkSpeed = 0.35f * Game.SCALE;
    private int walkDir = LEFT;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        intitHitBox(x, y, width, height);
    }

    private void updateAnimationTick() {
        aniTick++;
        while (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, enemyState))
                aniIndex = 0;
        }
    }


    public void update(int[][] levelData) {
        move(levelData);
        updateAnimationTick();
    }

    private void move(int[][] levelData) {
        if (firstUpdate) {
            if (!IsEntityOnFloor(hitbox, levelData))
                inAir = true;
            firstUpdate = false;
        }

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += fallSpeed;
                fallSpeed += gravity;
            } else {
                inAir = false;
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            }
        } else {
            switch (enemyState) {
                case IDLE:
                    enemyState = RUNNING;
                    break;
                case RUNNING:
                    float xSpeed = 0;

                    if (walkDir == LEFT)
                        xSpeed = -walkSpeed;
                    else
                        xSpeed = walkSpeed;

                    if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData))
                        if (IsFloor(hitbox, xSpeed, walkDir,levelData)) {
                            hitbox.x += xSpeed;
                            return;
                        }

                    changeWalkDir();

                    break;
            }
        }
    }

    public void draw(Graphics g, float xLevelOffset) {
//        drawHitBox(g, xLevelOffset);
    }

    private void changeWalkDir() {
        if (walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }


    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }
}
