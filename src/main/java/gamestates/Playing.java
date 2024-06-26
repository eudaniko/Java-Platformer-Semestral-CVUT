// Daniil Nikonenko
// PJV Semestral

package gamestates;

import audio.AudioPlayer;
import effects.Rain;
import entities.EnemyManager;
import levels.LevelManager;
import entities.Player;
import objects.ObjectManager;
import main.Game;
import ui.*;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import static utils.Constants.Environment.*;
import static utils.Constants.GameConstants.*;
import static utils.LoadSave.COINS_COUNTER;

public class Playing extends State implements StateMethods {

    private Player player;
    private CoinCounter coinCounter;

    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;
    private GameOverOverlay gameOverOverlay;
    private GameCompletedOverlay gameCompletedOverlay;
    private boolean gameOver = false, gameCompleted = false, playerDying = false;
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean levelCompleted = false;

    private int xLevelOffset;
    private int maxLevelOffsetX;

    private final BufferedImage backgroundImage;
    private final BufferedImage bigCloud;
    private final BufferedImage smallCloud;
    private final int[] smallCloudsPos;
    private Rain rain;
    private boolean isRaining = true;



    public Playing(Game game) {
        super(game);
        initClasses();
        backgroundImage = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND);
        bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUD);

        smallCloudsPos = new int[8];
        Random rnd = new Random();
        for (int i = 0; i < smallCloudsPos.length; i++)
            smallCloudsPos[i] = (int) (70 * SCALE) + rnd.nextInt((int) (100 * SCALE));

        calculateLevelOffset();
        loadStartLevel();
    }

    public void loadNextLevel() {
       coinCounter.changeGlobalCoins(coinCounter.getCurrentCoins());
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        game.getAudioPlayer().setLevelSong(levelManager.getCurrentLevelIndex());

    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void calculateLevelOffset() {
        maxLevelOffsetX = levelManager.getCurrentLevel().getMaxLevelOffset();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);
        player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE), this);
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        gameCompletedOverlay = new GameCompletedOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
        rain = new Rain();
        coinCounter = new CoinCounter();
    }

    public Player getPlayer() {
        return player;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public void unpauseGame() {
        paused = false;
    }

    @Override
    public void update() {
        if (paused)
            pauseOverlay.update();
        else if (gameOver)
            gameOverOverlay.update();
        else if (playerDying)
            player.update();
        else if (gameCompleted)
            gameCompletedOverlay.update();

        else {
            if (levelCompleted)
                levelCompletedOverlay.update();
            else {
                levelManager.update();
                player.update();
                enemyManager.update(levelManager.getCurrentLevel().getLevelData());
                objectManager.update();
                checkCLoseToBorder();
                if (isRaining)
                    rain.update(xLevelOffset);
                coinCounter.update();
            }
        }
    }

    private void checkCLoseToBorder() {
        int playerX = (int) (player.getHitBox().x);
        int diff = playerX - xLevelOffset;

        int leftBorder = (int) (0.4 * GAME_WIDTH);
        int rightBorder = (int) (0.6 * GAME_WIDTH);
        if (diff > rightBorder)
            xLevelOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLevelOffset += diff - leftBorder;

        if (xLevelOffset > maxLevelOffsetX)
            xLevelOffset = maxLevelOffsetX;
        else if (xLevelOffset < 0)
            xLevelOffset = 0;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);

        drawClouds(g);
        if (isRaining)
            rain.draw(g, xLevelOffset);
        levelManager.draw(g, xLevelOffset);
        player.draw(g, xLevelOffset);
        enemyManager.draw(g, xLevelOffset);
        objectManager.draw(g, xLevelOffset);
        levelManager.drawWater(g, xLevelOffset);
        coinCounter.draw(g, xLevelOffset);
        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (levelCompleted)
            levelCompletedOverlay.draw(g);
        else if (gameOver)
            gameOverOverlay.draw(g);
        else if (gameCompleted)
            gameCompletedOverlay.draw(g);
    }

    private void drawClouds(Graphics g) {
        for (int i = 0; i < 3; i++)
            g.drawImage(bigCloud, BIG_CLOUD_WIDTH * i - (int) (xLevelOffset * 0.3), (int) (204 * SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);

        for (int i = 0; i < smallCloudsPos.length; i++)
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i, smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
    }

    public void resetAll() {
        //Reset playing, enemies, level, etc..
        gameOver = false;
        unpauseGame();
        levelCompleted = false;
        gameCompleted = false;
        playerDying = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
        coinCounter.reset();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        game.getAudioPlayer().stopSong();
        game.getAudioPlayer().gameOver();
    }

    public void setGameComplete(boolean gameCompleted) {
        this.gameCompleted = gameCompleted;
        game.getAudioPlayer().stopSong();
        game.getAudioPlayer().lvlCompleted();
    }

    public void setMaxLevelOffsetX(int maxLevelOffsetX) {
        this.maxLevelOffsetX = maxLevelOffsetX;
    }

    public void setLevelComplete(boolean levelCompleted) {
        this.levelCompleted = levelCompleted;
        game.getAudioPlayer().stopSong();
        game.getAudioPlayer().lvlCompleted();
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.HitNearEnemies(attackBox, player.isPowerAttackActive());
    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        objectManager.checkObjectHit(attackBox);
    }


    public ObjectManager getObjectManager() {
        return objectManager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Action when the mouse is clicked
        if (!gameOver) {
            if (e.getButton() == MouseEvent.BUTTON1)
                player.setAttacking(true);
            else if (e.getButton() == MouseEvent.BUTTON3) {
                player.powerAttack();
            }

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (levelCompleted)
            levelCompletedOverlay.mousePressed(e);
        else if (paused)
            pauseOverlay.mousePressed(e);
        else if (gameCompleted)
            gameCompletedOverlay.mousePressed(e);
        else if (gameOver)
            gameOverOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (gameCompleted)
            gameCompletedOverlay.mouseReleased(e);
        else if (levelCompleted)
            levelCompletedOverlay.mouseReleased(e);
        else if (paused)
            pauseOverlay.mouseReleased(e);
        else if (gameOver)
            gameOverOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (levelCompleted)
            levelCompletedOverlay.mouseMoved(e);
        else if (paused)
            pauseOverlay.mouseMoved(e);
        else if (gameOver)
            gameOverOverlay.mouseMoved(e);
        else if (gameCompleted)
            gameCompletedOverlay.mouseMoved(e);
    }

    public void mouseDragged(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseDragged(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W, KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
            }

    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
        game.getAudioPlayer().playEffect(AudioPlayer.DIE);
    }

    public CoinCounter getCoinCounter(){return coinCounter;}
}

