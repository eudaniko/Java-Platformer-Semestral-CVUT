// Daniil Nikonenko
// 19.03.2024
// PJV Semestral: GamePanel

package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static utils.Constants.GameConstants.GAME_HEIGHT;
import static utils.Constants.GameConstants.GAME_WIDTH;


// Class representing the game panel for displaying game graphics
public class GamePanel extends JPanel {
    private MouseInputs mouseInputs; // Variable for managing mouse inputs
    private Game game;

    // Constructor for the GamePanel class
    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;

        setPanelSize();  // Set panel size
        addKeyListener(new KeyboardInputs(this));  // Add keyboard inputs
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }


    // Method to set the panel size
    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("size "+ GAME_WIDTH + " : " + GAME_HEIGHT);
    }

    // Method to update game logic on each frame
    public void updateGame() {
    }

    // Method to paint components on the panel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame(){
        return game;
    }
}
