// Daniil Nikonenko
// PJV Semestral

package gamestates;

import main.Game;
import ui.MenuButton;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.*;


public class Menu extends State implements StateMethods {

    private final MenuButton[] buttons = new MenuButton[3];
    private BufferedImage uiBackgroundImage;
    private BufferedImage[] backgroundGIF;
    private int menuX, menuY, menuWidth, menuHeight;

    //BackGroundAnimation
    private int aniTick = 0, aniIndex = 0;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
    }

    private void loadBackground() {
        backgroundGIF = LoadSave.GetGIF(LoadSave.MENU_BACKGROUND);
        uiBackgroundImage = LoadSave.GetSpriteAtlas(LoadSave.UI_MENU_BACKGROUND);

        menuWidth = (int) (uiBackgroundImage.getWidth() * SCALE);
        menuHeight = (int) (uiBackgroundImage.getHeight() * SCALE);
        menuX = GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45 * SCALE);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(GAME_WIDTH / 2, (int) (150 * SCALE), 0, GameState.PLAYING);
        buttons[1] = new MenuButton(GAME_WIDTH / 2, (int) (220 * SCALE), 1, GameState.OPTIONS);
        buttons[2] = new MenuButton(GAME_WIDTH / 2, (int) (290 * SCALE), 2, GameState.QUIT);
    }

    @Override
    public void update() {
        for (MenuButton mb : buttons)
            mb.update();
        updateBGAnimationTick();
    }

    private void updateBGAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= backgroundGIF.length) {
                aniIndex = 0;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundGIF[aniIndex], 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        g.drawImage(uiBackgroundImage, menuX, menuY, menuWidth, menuHeight, null);
        for (MenuButton mb : buttons)
            mb.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb))
                if (mb.isMousePressed())
                    mb.applyGamestate();
        }
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton mb : buttons)
            mb.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            } else
                mb.setMouseOver(false);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
