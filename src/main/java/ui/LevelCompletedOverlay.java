package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.URMButtons.URM_SIZE;

public class LevelCompletedOverlay {
    private Playing playing;
    private BufferedImage backgroundImage;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuButton, continueButton;

    public LevelCompletedOverlay(Playing playing) {
        this.playing = playing;
        initBackGround();
        initUrmButtons();

    }

    private void initBackGround() {
        backgroundImage = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_COMPLETED_BG);
        bgW = (int) (backgroundImage.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImage.getHeight() * Game.SCALE);
        bgX = (int) ((Game.GAME_WIDTH / 2 * Game.SCALE) - bgW * Game.SCALE);
        bgY = (int) (75 * Game.SCALE);

    }

    private void initUrmButtons() {
        int menuX = (int) (330 * Game.SCALE);
        int continueX = (int) (415 * Game.SCALE);
        int bY = (int) (195 * Game.SCALE);
        menuButton = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
        continueButton = new UrmButton(continueX, bY, URM_SIZE, URM_SIZE, 0);
    }

    public void update() {
        continueButton.update();
        menuButton.update();

    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, bgX, bgY, bgW, bgH, null);
        continueButton.draw(g);
        menuButton.draw(g);
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, continueButton))
            continueButton.setMousePressed(true);
        else if (isIn(e, menuButton))
            menuButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, continueButton)) {
            if (continueButton.isMousePressed())
                playing.loadNextLevel();
        } else if (isIn(e, menuButton)) {
            if (menuButton.isMousePressed()) {
                Gamestate.state = Gamestate.MENU;
                playing.unpauseGame();
            }
        }
        continueButton.resetBools();
        menuButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        menuButton.setMouseOver(false);
        continueButton.setMouseOver(false);

        if (isIn(e, continueButton))
            continueButton.setMouseOver(true);
        else if (isIn(e, menuButton))
            menuButton.setMouseOver(true);
    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }


}
