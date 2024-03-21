// Daniil Nikonenko
// 19.03.2024
// PJV Semestral: Game
// A class that manage game structure, player input and application window.

package main;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private  GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private  int frames = 0;
    private long lastCheck = 0;
    public Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET;
        long lastFrame = System.nanoTime();

        while (true){
            long now = System.nanoTime();

            if(now - lastFrame >= timePerFrame){
                gamePanel.repaint();
                lastFrame = now;
                frames++;
            }

            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck =  System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }


        }

    }
}
