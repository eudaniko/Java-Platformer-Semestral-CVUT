// Daniil Nikonenko
// 19.03.2024
// PJV Semestral: GameWindow
// A class, that define application window on the desktop, finish program if closed.

package main;

import javax.swing.*;

public class GameWindow {
    private JFrame jFrame;

    public GameWindow(GamePanel gamePanel){
        jFrame = new JFrame();

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePanel);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
