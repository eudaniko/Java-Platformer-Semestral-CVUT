// Daniil Nikonenko
// 19.03.2024
// PJV Semestral: KeyboardInputs

package inputs;

import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utils.Constants.Directions.*;



// Class for handling keyboard inputs
public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;

    // Constructor for the KeyboardInputs class
    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Action when a key is typed
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Action when a key is pressed
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.setDirection(UP);
                break;
            case KeyEvent.VK_A:
                gamePanel.setDirection(LEFT);
                break;
            case KeyEvent.VK_S:
                gamePanel.setDirection(DOWN);
                break;
            case KeyEvent.VK_D:
                gamePanel.setDirection(RIGHT);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Action when a key is released
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
            default:
                gamePanel.setMoving(false);
                break;
        }
    }
}
