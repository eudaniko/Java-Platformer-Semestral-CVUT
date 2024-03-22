// Daniil Nikonenko
// 19.03.2024
// PJV Semestral: GamePanel
// Main class that launch the game.

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


public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;

    private BufferedImage heroAtlasImg;
    private BufferedImage[][] animations;
    private int aniSpeed = 10;
    private int aniTick = 0, aniIndex = 0;

    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;



    public GamePanel() {
        mouseInputs = new MouseInputs(this);

        heroAtlasImg = importImg("/player_sprites.png");
        loadAnimations();

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private BufferedImage importImg(String fileAdress) {
        InputStream is = getClass().getResourceAsStream(fileAdress);
        try {
            return ImageIO.read(is);
        } catch (IOException e) {
            //TO DO catch
            throw new RuntimeException(e);
        }

    }

    private void loadAnimations() {
        animations = new BufferedImage[9][6];

        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = heroAtlasImg.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }

    private void updateAnimationTick(){
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex>= getSpriteAmount(playerAction)) aniIndex = 0;
        }
    }
    public void setDirection(int direction){
        this.playerDir =direction;
        moving = true;
    }

    public void setMoving(boolean moving){
        this.moving = moving;
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }


    public void changeXDelta(int value) {
        xDelta += value;
    }

    public void changeYDelta(int value) {
        yDelta += value;
    }

    private void setAnimation(){
        if(moving) playerAction = RUNNING;
        else playerAction = IDLE;
    }

    private void updatePos(){
        if (moving){
            switch (playerDir){
                case LEFT:
                    xDelta -=5;
                    break;
                case UP:
                    yDelta -=5;
                    break;
                case RIGHT:
                    xDelta +=5;
                    break;
                case DOWN:
                    yDelta +=5;
                    break;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateAnimationTick();
        setAnimation();
        updatePos();
        g.drawImage(animations[playerAction][aniIndex], (int) xDelta, (int) yDelta, 256, 160, null);
    }

}
