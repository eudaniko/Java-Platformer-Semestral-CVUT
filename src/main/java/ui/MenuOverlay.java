// Daniil Nikonenko
// PJV Semestral

package ui;

import gamestates.GameState;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static gamestates.GameState.*;
import static utils.Constants.GameConstants.GAME_WIDTH;
import static utils.Constants.GameConstants.SCALE;

public class MenuOverlay {
    private final Game game;
    private BufferedImage uiBackgroundImage;
    private int bgX, bgY, bgW, bgH;
    private final MenuButton[] buttons = new MenuButton[3];

    public MenuOverlay(Game game) {
        this.game = game;
        loadBackground();
        loadButtons();
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(GAME_WIDTH / 2, (int) (150 * SCALE), 0, PLAYING);
        buttons[1] = new MenuButton(GAME_WIDTH / 2, (int) (220 * SCALE), 1, OPTIONS);
        buttons[2] = new MenuButton(GAME_WIDTH / 2, (int) (290 * SCALE), 2, QUIT);
    }

    private void loadBackground() {
        uiBackgroundImage = LoadSave.GetSpriteAtlas(LoadSave.MAIN_MENU_UI);
        bgW = (int) (uiBackgroundImage.getWidth() * SCALE);
        bgH = (int) (uiBackgroundImage.getHeight() * SCALE);
        bgX = GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (46 * SCALE);
    }

    public void update() {
        for (MenuButton mb : buttons)
            mb.update();
    }


    public void draw(Graphics g) {
        g.drawImage(uiBackgroundImage, bgX, bgY, bgW, bgH, null);
        for (MenuButton mb : buttons)
            mb.draw(g);
    }

    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons)
            if (isIn(e, mb))
                mb.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb))
                if (mb.isMousePressed())
                    mb.applyGameState();
        }
        resetButtons();
    }


private void resetButtons() {
    for (MenuButton mb : buttons)
        mb.resetBools();
}

public void mouseMoved(MouseEvent e) {
    for (MenuButton mb : buttons) {
        if (isIn(e, mb)) {
            mb.setMouseOver(true);
            break;
        } else
            mb.setMouseOver(false);
    }
}

private boolean isIn(MouseEvent e, MenuButton b) {
    return b.getBounds().contains(e.getX(), e.getY());
}

}
