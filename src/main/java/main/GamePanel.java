// Daniil Nikonenko
// 19.03.2024
// PJV Semestral: GamePanel

package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;


// Class representing the game panel for displaying game graphics
public class GamePanel extends JPanel {

    private MouseInputs mouseInputs; // Variable for managing mouse inputs
    private float xDelta = 100, yDelta = 100; // Variables for controlling player movement

    private BufferedImage heroAtlasImg; // Image for player animations
    private BufferedImage[][] animations; // Array to store animation frames
    private int aniSpeed = 10; // Animation speed
    private int aniTick = 0, aniIndex = 0; // Animation tick and index variables

    // Player actions and direction variables
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;

    // Constructor for the GamePanel class
    public GamePanel() {
        mouseInputs = new MouseInputs(this);

        // Load player sprite image
        heroAtlasImg = importImg("/player_sprites.png");
        // Load animations from the sprite sheet
        loadAnimations();

        setPanelSize();  // Set panel size
        addKeyListener(new KeyboardInputs(this));  // Add keyboard inputs
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    // Method to import image resources
    private BufferedImage importImg(String fileAdress) {
        InputStream is = getClass().getResourceAsStream(fileAdress);
        try {
            return ImageIO.read(is);
        } catch (IOException e) {
            // TO DO catch
            throw new RuntimeException(e);
        }
    }
    // Method to set player animation based on movement status
    private void setAnimation() {
        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }

    // Method to load animation frames from the sprite sheet
    private void loadAnimations() {
        animations = new BufferedImage[9][6];

        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = heroAtlasImg.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }

    // Method to update animation frames
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteAmount(playerAction)) aniIndex = 0;
        }
    }

    // Method to set player direction and movement status
    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }

    // Method to set player movement status
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    // Method to set the panel size
    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    // Method to update player position and animation
    private void updatePos() {
        if (moving) {
            switch (playerDir) {
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;
            }
        }
    }

    // Method to update game logic on each frame
    public void updateGame() {
        updateAnimationTick();
        setAnimation();
        updatePos();
    }

    // Method to paint components on the panel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the player animation frame
        g.drawImage(animations[playerAction][aniIndex], (int) xDelta, (int) yDelta, 256, 160, null);
    }
}
