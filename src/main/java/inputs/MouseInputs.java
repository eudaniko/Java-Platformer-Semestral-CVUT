// Daniil Nikonenko
// PJV Semestral

package inputs;

import gamestates.GameState;
import gamestates.Menu;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Objects;


// Class for handling mouse inputs
public class MouseInputs implements MouseListener, MouseMotionListener {

    private final GamePanel gamePanel;

    // Constructor for the MouseInputs class
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state) {
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseClicked(e);
                break;
            case OPTIONS:
                gamePanel.getGame().getMenu().mouseClicked(e);
                break;
            case QUIT:
                gamePanel.getGame().QuitGame();
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Action when the mouse button is pressed
        switch (GameState.state) {
            case MENU, OPTIONS:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mousePressed(e);
                break;
            case QUIT:
                gamePanel.getGame().QuitGame();
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Action when the mouse button is released
        switch (GameState.state) {
            case MENU, OPTIONS:
                gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseReleased(e);
                break;
            case QUIT:
                gamePanel.getGame().QuitGame();
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Action when the mouse enters a component
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Action when the mouse exits a component
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (Objects.requireNonNull(GameState.state) == GameState.PLAYING)
            gamePanel.getGame().getPlaying().mouseDragged(e);
        else if (Objects.requireNonNull(GameState.state) == GameState.OPTIONS)
            gamePanel.getGame().getMenu().mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state) {
            case MENU, OPTIONS:
                gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseMoved(e);
                break;
            case QUIT:
                gamePanel.getGame().QuitGame();
                break;
            default:
                break;
        }
    }
}