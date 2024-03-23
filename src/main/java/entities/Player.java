package entities;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.*;

public class Player extends Entity{
    private BufferedImage[][] animations; // Array to store animation frames
    private int aniSpeed = 20; // Animation speed
    private int aniTick = 0, aniIndex = 0; // Animation tick and index variables
    // Player actions and direction variables
    private int playerAction = IDLE;
    private boolean moving = false,  attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void  update() {
        updatePos();
        updateAnimationTick();
        setAnimation();
    }
    public void render(Graphics g){
        // Draw the player animation frame
        g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, 128, 80, null);

    }

    // Method to import image resources and to load animation frames from the sprite sheet
    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[9][6];

        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }

    // Method to set player animation based on movement status
    private void setAnimation() {
        int startAni =playerAction;
        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
        if (attacking){
            playerAction = ATTACK_1;
        }

        if (startAni != playerAction) resetAniTick();
    }

    private void resetAniTick(){
        aniTick = 0;
        aniIndex = 0;

    }

    // Method to update animation frames
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)){
                aniIndex = 0;
                attacking = false;
            }
        }
    }
    // Method to update player position and animation
    private void updatePos() {
        moving = false;
        if (left && !right){
            x -= playerSpeed;
            moving = true;
        } else if (right && ! left) {
            x += playerSpeed;
            moving = true;
        }

        if(up && !down){
            y -= playerSpeed;
            moving = true;
        } else if (down && !up) {
            y += playerSpeed;
            moving = true;
        }

    }
    public void restetDirBooleans() {
        left = false;
        up = false;
        right = false;
        down = false;
    }

    public void setAttacking(boolean attacking){
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

}
