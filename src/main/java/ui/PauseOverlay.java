package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.PauseButtons.*;
import static utils.Constants.UI.URMButtons.URM_SIZE;
import static utils.Constants.UI.VolumeButtons.SLIDER_WIDTH;
import static utils.Constants.UI.VolumeButtons.VOLUME_WIDTH;

public class PauseOverlay {
    private  Playing playing;
    private BufferedImage backgroundImage;
    private int bgX, bgY, bgW, bgH;
    private SoundButton musicButton, sfxButton;
    private UrmButton menuButton, replayButton, unpauseButton;
    private VolumeButton volumeButton;

    public PauseOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButton();
    }

    private void createVolumeButton() {
        int vX = (int) (309 * Game.SCALE);
        int vY = (int)(278 * Game.SCALE);
        volumeButton = new VolumeButton(vX, vY,SLIDER_WIDTH, VOLUME_WIDTH);
    }

    private void createUrmButtons() {
        int menuX = (int) (313 * Game.SCALE);
        int replayX = (int) (387 * Game.SCALE);
        int unpauseX = (int) (462 * Game.SCALE);
        int bY = (int) (325 * Game.SCALE);

        menuButton = new UrmButton(menuX,  bY, URM_SIZE, URM_SIZE, 2);
        replayButton = new UrmButton(replayX,  bY, URM_SIZE, URM_SIZE, 1);
        unpauseButton = new UrmButton(unpauseX,  bY, URM_SIZE, URM_SIZE, 0);

    }

    private void createSoundButtons() {
        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (140 * Game.SCALE);
        int sfxY = (int) (186 * Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    private void loadBackground() {
        backgroundImage = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgW = (int) (backgroundImage.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImage.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (25 * Game.SCALE);
    }

    public void update() {
        musicButton.update();
        sfxButton.update();
        menuButton.update();
        replayButton.update();
        unpauseButton.update();
        volumeButton.update();
    }


    public void draw(Graphics g) {
        //Background
        g.drawImage(backgroundImage, bgX, bgY, bgW, bgH, null);

        //SoundButtons
        musicButton.draw(g);
        sfxButton.draw(g);

        menuButton.draw(g);
        replayButton.draw(g);
        unpauseButton.draw(g);

        volumeButton.draw(g );

    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton))
            musicButton.setMousePressed(true);
        else if (isIn(e, sfxButton))
            sfxButton.setMousePressed(true);
        else if (isIn(e, menuButton))
            menuButton.setMousePressed(true);
        else if (isIn(e,replayButton))
            replayButton.setMousePressed(true);
        else if (isIn(e, unpauseButton))
            unpauseButton.setMousePressed(true);
        else if (isIn(e, volumeButton))
            volumeButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed())
                musicButton.setMuted(!musicButton.isMuted());
        } else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed())
                sfxButton.setMuted(!sfxButton.isMuted());
        } else if (isIn(e, menuButton)) {
            if (menuButton.isMousePressed()) {
                Gamestate.state = Gamestate.MENU;
                playing.unpauseGame();
            }
        }else if (isIn(e,replayButton)){
            if (replayButton.isMousePressed())
                System.out.println("Restart Level!");
        } else if (isIn(e,unpauseButton)){
            if (unpauseButton.isMousePressed())
                playing.unpauseGame();
        }
        musicButton.resetBools();
        sfxButton.resetBools();
        menuButton.resetBools();
        replayButton.resetBools();
        unpauseButton.resetBools();
        volumeButton.resetBools();

    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuButton.setMouseOver(false);
        replayButton.setMouseOver(false);
        unpauseButton.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if (isIn(e, musicButton))
            musicButton.setMouseOver(true);
        else if (isIn(e, sfxButton))
            sfxButton.setMouseOver(true);
        else if (isIn(e, menuButton))
            menuButton.setMouseOver(true);
        else if (isIn(e, replayButton))
            replayButton.setMouseOver(true);
        else if (isIn(e, unpauseButton))
            unpauseButton.setMouseOver(true);
        else if (isIn(e, volumeButton))
            volumeButton.setMouseOver(true);
    }

    public void mouseDragged(MouseEvent e) {
        if(volumeButton.isMousePressed())
            volumeButton.changeX(e.getX());

    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

}
