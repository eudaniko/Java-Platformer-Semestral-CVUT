// Daniil Nikonenko
// 19.03.2024
// PJV Semestral: MouseInputs

package inputs;

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
        // Action when the mouse is clicked
        System.out.println("Mouse Clicked!");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Action when the mouse button is pressed
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Action when the mouse button is released
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
        // Action when the mouse is dragged (button held down while moving)
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Action when the mouse is moved
        // Uncomment the following line to set rectangle position based on mouse coordinates
        // gamePanel.setRecPosition(e.getX(), e.getY());
    }
}