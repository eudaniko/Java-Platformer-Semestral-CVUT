// Daniil Nikonenko
// 19.03.2024
// PJV Semestral: Game
// A class that manage game structure, player input and application window.

package main;

public class Game {

    private GameWindow gameWindow;
    private  GamePanel gamePanel;

    public Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();


    }
}
