// Daniil Nikonenko
// PJV Semestral

package entities;

import gamestates.Playing;

import java.awt.geom.Rectangle2D;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.Constants.*;
import static utils.Constants.GameConstants.*;
import static utils.HelpMethods.*;

public abstract class Enemy extends Entity {
    protected int enemyType;
    protected boolean firstUpdate = true;
    protected int walkDir;
    protected int tileY;
    protected float attackDistance = TILES_SIZE;
    protected float viewDistance = 5 * TILES_SIZE;
    protected boolean attackChecked;

    public Enemy(float x, float y, int width, int height, int enemyType, String entityAtlas, int aniAmount, int maxAniLength, int widthDefault, int heightDefault) {
        super(x, y, width, height, entityAtlas, aniAmount, maxAniLength, widthDefault, heightDefault);
        this.enemyType = enemyType;
        initHitBox(width, height);
        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, state)) {
                if (enemyType == CRABBY || enemyType == SHARK ) {
                    aniIndex = 0;

                    switch (state) {
                        case ATTACK, HIT -> state = IDLE;
                        case DEAD -> active = false;
                    }
                } else if (enemyType == PINKSTAR) {
                    if (state == ATTACK)
                        aniIndex = 3;
                    else {
                        aniIndex = 0;
                        if (state == HIT) {
                            state = IDLE;

                        } else if (state == DEAD)
                            active = false;
                    }
                }
            }
        }
    }

    protected void firstUpdateCheck(int[][] levelData) {
        if (firstUpdate) {
            if (IsEntityUpToFloor(hitBox, levelData))
                inAir = true;
            firstUpdate = false;
        }
    }

    protected void updateInAir(int[][] levelData) {
        if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
            hitBox.y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            inAir = false;
            hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
            tileY = (int) (hitBox.y / TILES_SIZE);
        }
    }

    protected void move(int[][] levelData) {
        float xSpeed;

        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData))
            if (IsFloor(hitBox, xSpeed, walkDir, levelData)) {
                hitBox.x += xSpeed;
                return;
            }

        changeWalkDir();
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitBox.x > hitBox.x)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }

    protected void newState(int enemyState) {
        this.state = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    public void hurt(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0)
            newState(DEAD);
        else
            newState(HIT);
    }

    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitBox))
            player.changeHealth(-GetEnemyDamage(enemyType), this);
        attackChecked = true;

    }

    protected void inAirChecks(int[][] lvlData, Playing playing) {
        if (state != HIT && state != DEAD) {
            updateInAir(lvlData);
            playing.getObjectManager().checkSpikesTouched();
            if (IsEntityInWater(hitBox, lvlData))
                hurt(maxHealth);
        }
    }


    protected boolean canSeePlayer(int[][] levelData, Player player) {
        int playerTileY = (int) (player.hitBox.y / TILES_SIZE);

        if (playerTileY == tileY)
            if (isPlayerInRange(player)) {
                return isSightClear(levelData, hitBox, player.hitBox, tileY);
            }
        return false;
    }

    private boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
        return absValue <= viewDistance;

    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
        return absValue <= attackDistance;
    }

    protected void changeWalkDir() {
        if (walkDir == LEFT)
            walkDir = RIGHT;
        else
            walkDir = LEFT;
    }


    public boolean isActive() {
        return active;
    }

    public void resetEnemy() {
        hitBox.x = x;
        hitBox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        airSpeed = 0;
    }
}
