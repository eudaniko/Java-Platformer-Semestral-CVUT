// Daniil Nikonenko
// PJV Semestral

package ui;

import gamestates.GameState;
import gamestates.Playing;
import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.GAME_WIDTH;
import static utils.Constants.GameConstants.SCALE;
import static utils.Constants.UI.URMButtons.URM_SIZE;

public class GameOverOverlay {

    private final Playing playing;
    private UrmButton menuButton, playButton;

    private BufferedImage backgroundImage;
    private int bgX, bgY, bgW, bgH;


    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createUrmButtons();
    }

    public void update() {
        menuButton.update();
        playButton.update();

    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Constants.GameConstants.GAME_WIDTH, Constants.GameConstants.GAME_HEIGHT);
        g.drawImage(backgroundImage, bgX, bgY, bgW, bgH, null);

        menuButton.draw(g);
        playButton.draw(g);
    }

    private void loadBackground() {
        backgroundImage = LoadSave.GetSpriteAtlas(LoadSave.DEATH_MENU_UI);
        bgW = (int) (backgroundImage.getWidth() * SCALE);
        bgH = (int) (backgroundImage.getHeight() * SCALE);
        bgX = GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (100 * SCALE);
    }

    private void createUrmButtons() {
        int menuX = (int) (335 * SCALE);
        int replayX = (int) (440 * SCALE);
        int bY = (int) (195 * SCALE);

        menuButton = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
        playButton = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 0);

    }


    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuButton))
            menuButton.setMousePressed(true);
        else if (isIn(e, playButton))
            playButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuButton)) {
            if (menuButton.isMousePressed())
                playing.setGameState(GameState.MENU);

        } else if (isIn(e, playButton)) {
            if (playButton.isMousePressed()) {
                playing.resetAll();
                playing.setGameState(GameState.PLAYING);
            }
        }
        menuButton.resetBools();
        playButton.resetBools();

    }

    public void mouseMoved(MouseEvent e) {
        menuButton.setMouseOver(false);
        playButton.setMouseOver(false);

        if (isIn(e, menuButton))
            menuButton.setMouseOver(true);
        else if (isIn(e, playButton))
            playButton.setMouseOver(true);
    }


    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

}
