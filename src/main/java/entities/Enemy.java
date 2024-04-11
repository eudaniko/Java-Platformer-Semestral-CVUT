package entities;


import main.Game;

import java.awt.*;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethods.*;

public abstract class Enemy extends Entity {
    protected int aniIndex, aniTick, aniSpeed = 25;
    protected int enemyState, enemyType;
    protected boolean inAir, firstUpdate = true;
    protected float gravity = 0.4f * Game.SCALE;
    protected float fallSpeed = 5;
    protected float walkSpeed = 0.3f * Game.SCALE;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected  float viewDistance = 5 * Game.TILES_SIZE;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        intitHitBox(x, y, width, height);
    }

    protected void updateAnimationTick() {
        aniTick++;
        while (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
                aniIndex = 0;
                if (enemyState == ATTACK)
                    enemyState = IDLE;
            }
        }
    }


    public void update(int[][] levelData) {

    }

    protected void firstUpdateCheck(int[][] levelData) {
        if (firstUpdate) {
            if (!IsEntityOnFloor(hitbox, levelData))
                inAir = true;
            firstUpdate = false;
        }
    }

    protected void updateInAir(int[][] levelData) {
        if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, levelData)) {
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    protected void move(int[][] levelData) {
        float xSpeed = 0;

        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData))
            if (IsFloor(hitbox, xSpeed, walkDir, levelData)) {
                hitbox.x += xSpeed;
                return;
            }

        changeWalkDir();
    }

    protected void turnTowardsPlayer(Player player){
        if (player.hitbox.x > hitbox.x)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    protected void newState(int enemyState) {
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    protected boolean canSeePlayer(int[][] levelData, Player player) {
        int playerTileY = (int) (player.hitbox.y / Game.TILES_SIZE);

        if (playerTileY == tileY)
            if(isPlayerInRange(player)){
                if(isSightClear(levelData,hitbox, player.hitbox, tileY))
                    return true;
            }
        return false;
    }

    private boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= viewDistance;

    }

    protected boolean isPlayerCloseForAttack( Player player){
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance ;
    }

    public void draw(Graphics g, float xLevelOffset) {
//        drawHitBox(g, xLevelOffset);
    }

    protected void changeWalkDir() {
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
