// Daniil Nikonenko
// 19.03.2024
// PJV Semestral: MouseInputs

package inputs;

import gamestates.Gamestate;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


// Class for handling mouse inputs
public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;

    // Constructor for the MouseInputs class
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (Gamestate.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseClicked(e);
                break;
            case OPTIONS:
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
        switch (Gamestate.state){
            case MENU:
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
        switch (Gamestate.state){
            case MENU:
                gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseReleased(e);
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
        switch (Gamestate.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (Gamestate.state){
            case MENU:
                gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseMoved(e);
                break;
            default:
                break;
        }
    }
}