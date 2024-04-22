// Daniil Nikonenko
// PJV Semestral

package entities;

import audio.AudioPlayer;
import gamestates.Playing;
import utils.LoadSave;

import static utils.Constants.*;
import static utils.Constants.Directions.*;
import static utils.Constants.GameConstants.*;
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
    private boolean moving, attacking;
    private boolean attackChecked;
    private final int maxPower;
    private int currentPower;
    private boolean left, right;
    private boolean jump = false;
    private final BufferedImage statusBar;
    private int healthWidth = statusBarWidth;
    private int powerWidth = PowerBarWidth;
    private boolean powerAttackActive;
    private int powerAttackTick;
    private int powerGrowTick;

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
        resetAttackBox();
    }

    public void update() {
        updateHealthBar();
        updatePowerBar();

        if (currentHealth <= 0) {
            if (state != DEAD) {
                state = DEAD;
                aniTick = 0;
                aniIndex = 0;
                playing.setPlayerDying(true);
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);

                // Check if player died in air
                if (!IsEntityUpToFloor(hitBox, levelData)) {
                    inAir = true;
                    airSpeed = 0;
                }
            } else if (aniIndex == GetSpriteAmount(DEAD) - 1 && aniTick >= ANI_SPEED - 1) {
                playing.setGameOver(true);
                playing.getGame().getAudioPlayer().stopSong();
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
            } else {
                updateAnimationTick();

                // Fall if in air
                if (inAir)
                    if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
                        hitBox.y += airSpeed;
                        airSpeed += GRAVITY;
                    } else
                        inAir = false;

            }

            return;
        }

        updateAttackBox();

        if (state == HIT) {
            if (aniIndex <= GetSpriteAmount(state) - GetSpriteAmount(HIT))
                pushBack(pushBackDir, levelData, PUSH_BACK_SPEED);
            updatePushBackDrawOffset();
        }

        if (moving) {
            checkObjectsTouched();
//            checkInsideWater();
            if (powerAttackActive) {
                powerAttackTick++;
                if (powerAttackTick >= 35) {
                    powerAttackTick = 0;
                    powerAttackActive = false;
                }
            }
        }

        updatePos();
        if (moving) {
            checkObjectsTouched();
            if (powerAttackActive) {
                powerAttackTick++;
                if (powerAttackTick >= ANI_SPEED) {
                    powerAttackTick = 0;
                    powerAttackActive = false;
                }
            }
        }
        if (attacking || powerAttackActive)
            checkAttack();


        updateAttackBox();
        updateAnimationTick();
        setAnimation();
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

    private void checkAttack() {
        if (attackChecked || aniIndex != 1)
            return;
        attackChecked = true;
        if (powerAttackActive)
            attackChecked = false;


        playing.checkEnemyHit(attackBox);
        playing.checkObjectHit(attackBox);
        playing.getGame().getAudioPlayer().playAttackSound();

    }

    private void checkObjectsTouched() {
        playing.getObjectManager().checkSpikesTouched();
        playing.getObjectManager().checkPotionsTouched();
    }

    private void updateAttackBox() {
        if (right && left) {
            if (flipW == 1)
                setAttackBoxOnRightSide();
            else
                setAttackBoxOnLeftSide();

        } else if (right || (powerAttackActive && flipW == 1))
            setAttackBoxOnRightSide();
        else if (left || (powerAttackActive && flipW == -1))
            setAttackBoxOnLeftSide();

        attackBox.y = hitBox.y + (SCALE * 10);
    }

    private void setAttackBoxOnRightSide() {
        attackBox.x = hitBox.x + hitBox.width - (int) (SCALE * 5);
    }

    private void setAttackBoxOnLeftSide() {
        attackBox.x = hitBox.x - hitBox.width - (int) (SCALE * 10);
    }


    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    public void changeHealth(int deltaHealth) {
        if (deltaHealth < 0) {
            if (state == HIT)
                return;
            else
                setEntityState(HIT);
        }

        currentHealth += deltaHealth;
        currentHealth = Math.max(Math.min(currentHealth, maxHealth), 0);
    }

    public void changeHealth(int value, Enemy e) {
        if (state == HIT)
            return;
        changeHealth(value);
        pushBackOffsetDir = UP;
        pushDrawOffset = 0;

        if (e.getHitBox().x < hitBox.x)
            pushBackDir = RIGHT;
        else
            pushBackDir = LEFT;
    }

    public void changePower(int deltaPower) {
        currentPower += deltaPower;

        if (currentPower <= 0)
            currentPower = 0;
        else if (currentPower >= maxPower)
            currentPower = maxPower;

    }

    private void updatePowerBar() {
        powerWidth = (int) ((currentPower / (float) maxPower) * PowerBarWidth);

        powerGrowTick++;
        if (powerGrowTick >= POWER_GROW_SPEED) {
            powerGrowTick = 0;
            changePower(1);
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
        if (state == HIT)
            return;

        if (moving)
            state = RUNNING;
        else
            state = IDLE;

        if (inAir) {
            if (airSpeed < 0)
                state = JUMP;
            else
                state = FALLING;
        }

        if (powerAttackActive) {
            state = ATTACK;
            aniIndex = 1;
            aniTick = 0;
            return;
        }

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
            if (aniIndex >= GetSpriteAmount(state)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
                if (state == HIT) {
                    setEntityState(IDLE);
                    airSpeed = 0f;
                    if (!IsFloor(hitBox, 0, 0, levelData))
                        inAir = true;
                }
            }
        }
    }

    // Method to update player position and animation
    private void updatePos() {
        moving = false;

        if (jump)
            jump();

        if (!inAir)
            if (!powerAttackActive)
                if ((!left && !right) || (right && left))
                    return;

        float xSpeed = 0;

        if (left && !right) {
            xSpeed -= walkSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right && !left) {
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (powerAttackActive) {
            if ((!left && !right) || (left && right)) {
                if (flipW == -1)
                    xSpeed = -walkSpeed;
                else
                    xSpeed = walkSpeed;
            }

            xSpeed *= 3;
        }

        if (!inAir)
            if (IsEntityUpToFloor(hitBox, levelData))
                inAir = true;


        if (inAir && !powerAttackActive) {
            if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, levelData)) {
                hitBox.y += airSpeed;
                airSpeed += GRAVITY;
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
        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
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
            if (powerAttackActive) {
                powerAttackActive = false;
                powerAttackTick = 0;
            }
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
        powerAttackActive = false;
        powerAttackTick = 0;

        resetAttackBox();
        hitBox.x = x;
        hitBox.y = y;

        if (IsEntityUpToFloor(hitBox, levelData))
            inAir = true;
    }

    private void resetAttackBox() {
        if (flipW == 1)
            setAttackBoxOnRightSide();
        else
            setAttackBoxOnLeftSide();
    }

    public int getTileY() {
        return (int) (hitBox.y / GameConstants.TILES_SIZE);
    }


    public void powerAttack() {
        if (powerAttackActive)
            return;
        if (currentPower >= POWER_FOR_ATTACK) {
            powerAttackActive = true;
            changePower(-POWER_FOR_ATTACK);
        }
    }

    public boolean isPowerAttackActive() {
        return powerAttackActive;
    }
}
