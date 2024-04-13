// Daniil Nikonenko
// 19.03.2024
// PJV Semestral: Game

package main;

import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;

import java.awt.*;

// Class representing the main game logic
public class Game implements Runnable {

    private GameWindow gameWindow; // Variable for managing the game window
    private GamePanel gamePanel; // Variable for managing the game panel
    private Thread gameThread; // Thread for the main game loop
    private final int FPS_SET = 120; // Set frames per second
    private final int UPS_SET = 200; // Set updates per second

    private Playing playing;
    private Menu menu;


    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;


    // Constructor for the Game class
    public Game() {
        initClasses();
        gamePanel = new GamePanel(this); // Create a new game panel
        gameWindow = new GameWindow(gamePanel); // Create a game window based on the game panel
        gamePanel.setFocusable(true);
        gamePanel.requestFocus(); // Set focus to the game panel
        startGameLoop(); // Start the game loop
    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);

    }

    // Method to start the game loop
    private void startGameLoop() {
        gameThread = new Thread(this); // Create a new thread for the game loop
        gameThread.start(); // Start the thread
    }

    // Method for updating the game logic
    public void Update() {
        switch (Gamestate.state){
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            default:
                break;
        }
    }

    public void render(Graphics g) {

        switch (Gamestate.state){
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            default:
                break;
        }

    }

    // Method required by the Runnable interface, contains the main game loop
    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET; // Calculate time per frame
        double timePerUpdate = 1000000000.0 / UPS_SET; // Calculate time per update
        long lastCheck = System.currentTimeMillis(); // Get current time in milliseconds

        int updates = 0, frames = 0; // Update and frame counters
        double deltaU = 0, deltaF = 0; // Variables for tracking delta time
        long previousTime = System.nanoTime(); // Get current time in nanoseconds

        // Main game loop
        while (true) {
            long currentTime = System.nanoTime(); // Get current time in nanoseconds

            deltaU += (currentTime - previousTime) / timePerUpdate; // Calculate delta time for updates
            deltaF += (currentTime - previousTime) / timePerFrame; // Calculate delta time for frames
            previousTime = currentTime; // Update previous time

            if (deltaU >= 1) {
                Update(); // Call the update method
                updates++; // Increase the update counter
                deltaU--; // Reset the delta
            }

            if (deltaF >= 1) {
                gamePanel.repaint(); // Repaint the game panel
                frames++; // Increase the frame counter
                deltaF--; // Reset the delta
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis(); // Update last check time
                System.out.println("FPS: " + frames + " | UPS: " + updates); // Display frames per second and updates per second
                frames = 0; // Reset frames counter
                updates = 0; // Reset updates counter
            }

        }
    }

    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING)
            playing.getPlayer().resetDirBooleans();
    }

    public Menu getMenu(){
        return menu;
    }

    public  Playing getPlaying(){
        return  playing;
    }

    public void QuitGame(){
        System.exit(0);
    }
}