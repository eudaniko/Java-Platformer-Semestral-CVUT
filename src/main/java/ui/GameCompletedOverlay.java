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

import static utils.Constants.GameConstants.*;
import static utils.Constants.UI.URMButtons.URM_SIZE;

public class GameCompletedOverlay {

    private final Playing playing;
    private UrmButton menuButton;

    private BufferedImage backgroundImage;
    private int bgX, bgY, bgW, bgH;


    public GameCompletedOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createUrmButtons();
    }

    public void update() {
        menuButton.update();
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Constants.GameConstants.GAME_WIDTH, Constants.GameConstants.GAME_HEIGHT);
        g.drawImage(backgroundImage, bgX, bgY, bgW, bgH, null);

        menuButton.draw(g);
    }

    private void loadBackground() {
        backgroundImage = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_MENU_UI);
        bgW = (int) (backgroundImage.getWidth() * SCALE);
        bgH = (int) (backgroundImage.getHeight() * SCALE);
        bgX = GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (100 * SCALE);
    }

    private void createUrmButtons() {
        int menuX = (GAME_WIDTH - URM_SIZE) / 2;
        int bY = (GAME_HEIGHT) / 2 + 50;

        menuButton = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);

    }


    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuButton))
            menuButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuButton)) {
            if (menuButton.isMousePressed())
                playing.setGameState(GameState.MENU);
        }
        menuButton.resetBools();

    }

    public void mouseMoved(MouseEvent e) {
        menuButton.setMouseOver(false);

        if (isIn(e, menuButton))
            menuButton.setMouseOver(true);
    }


    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

}
