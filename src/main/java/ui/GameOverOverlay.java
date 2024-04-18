package ui;

import gamestates.GameState;
import gamestates.Playing;
import main.Game;
import utils.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverOverlay {

    private Playing playing;

    public GameOverOverlay(Playing playing){
        this.playing =playing;
    }

    public void update(){

    }

    public void draw(Graphics g){
        g.setColor(new Color(0,0,0,200));
        g.fillRect(0,0, Constants.GameConstants.GAME_WIDTH, Constants.GameConstants.GAME_HEIGHT);

        g.setColor(Color.white);
        g.drawString("Game Over!", Constants.GameConstants.GAME_WIDTH / 2, 150);
        g.drawString("Press esc to enter Main Menu.", Constants.GameConstants.GAME_WIDTH / 2, 300);

    }

    public void keyPressed(KeyEvent e){
        if (e.getKeyCode()== KeyEvent.VK_ESCAPE){
            playing.resetAll();
            GameState.state = GameState.MENU;
        }
    }
}
