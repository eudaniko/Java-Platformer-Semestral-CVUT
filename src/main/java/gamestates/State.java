// Daniil Nikonenko
// PJV Semestral

package gamestates;

import audio.AudioPlayer;
import main.Game;
import ui.MenuButton;

import java.awt.event.MouseEvent;

public class State {

    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGameState(GameState state) {
        switch (state) {
            case MENU, OPTIONS:
                game.getAudioPlayer().stopEffect();
                game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
                break;
            case PLAYING:
                game.getAudioPlayer().setLevelSong(getGame().getPlaying().getLevelManager().getCurrentLevelIndex());
                break;
        }
        GameState.state = state;
    }

}
