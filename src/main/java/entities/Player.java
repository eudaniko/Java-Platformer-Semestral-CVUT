// Daniil Nikonenko
// PJV Semestral

package entities;

import gamestates.Playing;
import utils.LoadSave;

import static utils.Constants.*;
import static utils.Constants.GameConstants.ANI_SPEED;
import static utils.Constants.GameConstants.SCALE;
import static utils.Constants.UI.StatusBar.*;
import static utils.HelpMethods.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {

    //Drawing
    private int[][] levelData;
    private BufferedImage[][] animations;
    private int flipX = 0, flipW = 1;

    // Moving && Jumping && Attacking
    private final Playing playing;
    private boolean moving, attacking, gettingHit;
    private boolean attackChecked;
    private final int maxPower;
    private int currentPower;
    private boolean left, right;
    private boolean jump = false;
    private final BufferedImage statusBar;
    private int healthWidth = statusBarWidth;
    private int powerWidth = PowerBarWidth;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = PLAYER_MAX_HEALTH;
        this.currentHealth = maxHealth;
        this.maxPower = PLAYER_MAX_POWER;
        this.currentPower = maxPower;
        this.walkSpeed = PLAYER_WALK_SPEED;
        statusBar = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
        loadAnimations();
        initHitBox(20, 28);
        initAttackBox();
    }

    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;
        hitBox.x = spawn.x;
        hitBox.y = spawn.y;
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(hitBox.x - hitBox.width, hitBox.y, PlayerConstants.PLAYER_ATTACK_HIT_BOX_WIDTH, PlayerConstants.PLAYER_ATTACK_HIT_BOX_HEIGHT);
    }

    public void update() {
        updateStatusBar();

        if (currentHealth <= 0) {
            if (state != DEAD) {
                state = DEAD;
                aniTick = 0;
                aniIndex = 0;
                playing.setPlayerDying(true);
            } else if (aniIndex == GetSpriteAmount(DEAD) - 1 && aniTick >= ANI_SPEED - 1)
                playing.setGameOver(true);
            else
                updateAnimationTick();
            return;
        }


        updatePos();
        if (moving)
            checkObjectsTouched();
        if (attacking)
            checkAttack();
        updateAttackBox();
        updateAnimationTick();
        setAnimation();
    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 1)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
        playing.checkObjectHit(attackBox);

    }

    private void checkObjectsTouched() {
        playing.getObjectManager().checkSpikesTouched();
        playing.getObjectManager().checkPotionsTouched();
    }

    private void updateAttackBox() {
        if (right)
            attackBox.x = hitBox.x;
        else if (left)
            attackBox.x = hitBox.x - hitBox.width;
        attackBox.y = hitBox.y + (SCALE * 10);
    }


    private void updateStatusBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
        powerWidth = (int) ((currentPower / (float) maxPower) * PowerBarWidth);
    }

    public void changeHealth(int deltaHealth) {
        currentHealth += deltaHealth;
        gettingHit = true;
        if (currentHealth <= 0)
            currentHealth = 0;
        else if (currentHealth >= maxHealth)
            currentHealth = maxHealth;
    }

    public void changePower(int deltaPower) {
        currentPower += deltaPower;

        if (currentPower <= 0)
            currentPower = 0;
        else if (currentPower >= maxPower)
            currentPower = maxPower;

    }

    public void render(Graphics g, int levelOffset) {
        // Draw the player animation frame
        g.drawImage(animations[state][aniIndex],
                (int) (hitBox.x - xDrawOffset) - levelOffset + flipX,
                (int) (hitBox.y - yDrawOffset),
                width * flipW, height, null);
        drawUI(g);
        if (GameConstants.DRAW_HIT_BOX) {
            drawHitBox(g, levelOffset);
            drawAttackBox(g, levelOffset);
        }
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBar, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);

        g.setColor(Color.YELLOW);
        g.fillRect(PowerBarXStart + statusBarX, PowerBarYStart + statusBarY, powerWidth, PowerBarHeight);

    }

    // Method to import image resources and to load animation frames from the sprite sheet
    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[7][8];

        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if (IsEntityUpToFloor(hitBox, levelData))
            inAir = true;
    }

    // Method to set player animation based on movement status
    private void setAnimation() {
        int startAni = state;
        if (gettingHit)
            state = HIT;
        else if (inAir) {
            if (airSpeed < 0)
                state = JUMP;
            else
                state = FALLING;
        } else if (moving)
            state = RUNNING;
        else
            state = IDLE;

        if (attacking) {
            state = ATTACK;
            if (startAni != ATTACK) {
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }

        if (startAni != state)
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;

    }

    // Method to update animation frames
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= GameConstants.ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            changePower(POWER_DEFAULT_INCREASE);
            if (aniIndex >= GetSpriteAmount(state)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
                gettingHit = false;
            }
        }
    }

    // Method to update player position and animation
    private void updatePos() {
        moving = false;

        if (jump)
            jump();

        if (!inAir)
            if ((!left && !right) || (right && left))
                return;

        float xSpeed = 0;

        if (left) {
            xSpeed -= walkSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir) {
            if (IsEntityUpToFloor(hitBox, levelData))
                inAir = true;
        }

        if (inAir) {
            if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
                hitBox.y += airSpeed;
                airSpeed += GameConstants.GRAVITY;
                updateXPos(xSpeed);
            } else {
                hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        } else
            updateXPos(xSpeed);
        moving = true;
    }

    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, levelData)) {
            hitBox.x += xSpeed;
        } else {
            hitBox.x = GetEntityXPosNextToWall(hitBox, xSpeed);
        }

    }

    public void resetDirBooleans() {
        left = false;
        right = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll() {
        resetDirBooleans();
        attacking = false;
        inAir = false;
        moving = false;
        state = IDLE;
        currentHealth = maxHealth;
        currentPower = maxPower;

        hitBox.x = x;
        hitBox.y = y;

        if (IsEntityUpToFloor(hitBox, levelData))
            inAir = true;
    }

    public int getTileY() {
        return (int) (hitBox.y / GameConstants.TILES_SIZE);
    }

    public int getCurrentPower() {
        return currentPower;
    }
}
