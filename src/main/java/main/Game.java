// Daniil Nikonenko
// PJV Semestral

package main;

import audio.AudioPlayer;
import entities.EnemyManager;
import gamestates.GameState;
import gamestates.Menu;
import gamestates.Playing;
import levels.LevelManager;
import objects.ObjectManager;
import utils.Constants;

import java.awt.*;


// Class representing the main game logic
public class Game implements Runnable {

    private final GamePanel gamePanel; // Variable for managing the game panel
    private Thread gameThread; // Thread for the main game loop
    private Playing playing;
    private Menu menu;
    private AudioPlayer audioPlayer;


    // Constructor for the Game class
    public Game() {
        initClasses();
        gamePanel = new GamePanel(this); // Create a new game panel
        new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus(); // Set focus to the game panel

        startGameLoop(); // Start the game loop
    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
        audioPlayer = new AudioPlayer();
    }

    // Method to start the game loop
    private void startGameLoop() {
        gameThread = new Thread(this); // Create a new thread for the game loop
        gameThread.start(); // Start the thread
    }

    // Method for updating the game logic
    public void Update() {
        switch (GameState.state) {
            case MENU, OPTIONS:
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

        switch (GameState.state) {
            case MENU, OPTIONS:
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
        double timePerFrame = 1000000000.0 / Constants.GameConstants.FPS_SET; // Calculate time per frame
        double timePerUpdate = 1000000000.0 / Constants.GameConstants.UPS_SET; // Calculate time per update
        long lastCheck = System.currentTimeMillis(); // Get current time in milliseconds

        int updates = 0, frames = 0; // Update and frame counters
        double deltaU = 0, deltaF = 0; // Variables for tracking delta time
        long previousTime = System.nanoTime(); // Get current time in nanoseconds

        // Main game loop
        while (gameThread.isAlive()) {
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
        if (GameState.state == GameState.PLAYING)
            playing.getPlayer().resetDirBooleans();
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public AudioPlayer getAudioPlayer(){return audioPlayer;}

    public void QuitGame() {
        System.exit(0);
    }
}