// Daniil Nikonenko
// PJV Semestral

package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

// Class representing the game window
public class GameWindow {

    // Constructor for the GameWindow class
    public GameWindow(GamePanel gamePanel) {
        JFrame jFrame = new JFrame();

        // Set up the game window properties
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePanel);
        // Center the frame on the screen
        // Set the window to be non-resizable
        jFrame.setResizable(false);
        // Pack the components inside the frame
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        // Make the frame visible
        jFrame.setVisible(true);
        jFrame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
        });
    }
}
