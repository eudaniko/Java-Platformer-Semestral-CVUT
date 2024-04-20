// Daniil Nikonenko
// PJV Semestral

package gamestates;

import main.Game;
import ui.MenuButton;
import ui.MenuOverlay;
import ui.OptionsOverlay;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static gamestates.GameState.*;
import static utils.Constants.GameConstants.*;


public class Menu extends State implements StateMethods {

    private BufferedImage[] backgroundGIF;
    private final OptionsOverlay optionsOverlay;
    private final MenuOverlay menuOverlay;

    //BackGroundAnimation
    private int aniTick = 0, aniIndex = 0;

    public Menu(Game game) {
        super(game);
        backgroundGIF = LoadSave.GetGIF(LoadSave.MENU_BACKGROUND);
        optionsOverlay = new OptionsOverlay(game);
        menuOverlay = new MenuOverlay(game);
    }

    @Override
    public void update() {
        if (state == MENU)
            menuOverlay.update();
        else if (state == OPTIONS)
            optionsOverlay.update();


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
        if (state == MENU)
            menuOverlay.draw(g);
        else if (state == OPTIONS)
            optionsOverlay.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (state == MENU) {
            menuOverlay.mousePressed(e);
        } else if (state == OPTIONS)
            optionsOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (state == MENU)
            menuOverlay.mouseReleased(e);
        else if (state == OPTIONS)
            optionsOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (state == MENU)
            menuOverlay.mouseMoved(e);
        else if (state == OPTIONS)
            optionsOverlay.mouseMoved(e);

    }

    public void mouseDragged(MouseEvent e) {
        optionsOverlay.mouseDragged(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
