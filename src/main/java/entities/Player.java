package entities;

import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import static utils.HelpMethods.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {
    private BufferedImage[][] animations; // Array to store animation frames
    private int aniSpeed = 25; // Animation speed
    private int aniTick = 0, aniIndex = 0; // Animation tick and index variables
    // Player actions and direction variables
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 1.0f * Game.SCALE;
    private int[][] levelData;
    public float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 3 * Game.SCALE;

    // Jumping | Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;
    private boolean jump = false;


    // Status Bar
    private BufferedImage statusBar;
    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    // Health Bar
    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);

    private int maxHealth = 100;
    private int currentHealth = 50;
    private int healthWidth = statusBarWidth;

    // Attack
    private Rectangle2D.Float attackBox;
    private boolean attackChecked;
    private Playing playing;

    private int flipX = 0;
    private int flipW = 1;


    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        statusBar = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
        loadAnimations();
        initHitBox(x, y, (int) (20 * Game.SCALE), (int) (28 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(hitbox.x, hitbox.y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
    }

    public void update() {
        updateHealthBar();
        if(currentHealth <= 0){
            playing.setGameOver(true);
            return;
        }
        updateAttackBox();

        updatePos();
        if (attacking){
            attackChecked = false;
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();
    }

    private void checkAttack() {
        if(attackChecked || aniIndex != 1)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);

    }

    private void updateAttackBox() {
        if (right) {
            attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 10);
        } else if (left) {
            attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 10);
        } else
            attackBox.y = hitbox.y + (Game.SCALE * 10);
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    public void changeHealth(int deltaHealth) {
        currentHealth += deltaHealth;

        if (currentHealth <= 0)
            currentHealth = 0;
            //gameOver();
        else if (currentHealth >= maxHealth)
            currentHealth = maxHealth;
    }

    public void render(Graphics g, int levelOffset) {
        // Draw the player animation frame
        g.drawImage(animations[playerAction][aniIndex],
                (int) (hitbox.x - xDrawOffset) - levelOffset + flipX,
                (int) (hitbox.y - yDrawOffset),
                width * flipW, height, null);
        //        drawHitBox(g, levelOffset);
        drawUI(g);
        //drawAttackBox(g, levelOffset);

    }

    private void drawAttackBox(Graphics g, int levelOffsetX) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - levelOffsetX, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBar, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);

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
        if (!IsEntityOnFloor(hitbox, levelData))
            inAir = true;
    }

    // Method to set player animation based on movement status
    private void setAnimation() {
        int startAni = playerAction;
        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if (inAir) {
            if (airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        }

        if (attacking) {
            playerAction = ATTACK;
            if(startAni != ATTACK){
                aniIndex = 1;
                aniTick = 0;
            }
        }

        if (startAni != playerAction)
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;

    }

    // Method to update animation frames
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
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

        float xSpeed = 0, ySpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir) {
            if (!IsEntityOnFloor(hitbox, levelData))
                inAir = true;
        }

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
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
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }

    }

    public void resetDirBooleans() {
        left = false;
        up = false;
        right = false;
        down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll() {
        resetDirBooleans();
        attacking = false;
        inAir = false;
        moving = false;
        playerAction = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;

        if (!IsEntityOnFloor(hitbox, levelData))
            inAir = true;
    }
}
