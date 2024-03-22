// Daniil Nikonenko
// 19.03.2024
// PJV Semestral: GameWindow

package main;

import javax.swing.*;

// Class representing the game window
public class GameWindow {

    private JFrame jFrame;

    // Constructor for the GameWindow class
    public GameWindow(GamePanel gamePanel) {
        jFrame = new JFrame();

        // Set up the game window properties
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePanel);

        // Center the frame on the screen
        jFrame.setLocationRelativeTo(null);

        // Set the window to be non-resizable
        jFrame.setResizable(false);

        // Pack the components inside the frame
        jFrame.pack();

        // Make the frame visible
        jFrame.setVisible(true);
    }
}
