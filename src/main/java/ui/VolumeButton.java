// Daniil Nikonenko
// PJV Semestral

package ui;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.VolumeButtons.*;

public class VolumeButton extends PauseButton {

    private BufferedImage[] images;
    private BufferedImage slider;
    private int index;
    private boolean mousePressed, mouseOver;
    private int buttonX;
    private final int minX;
    private final int maxX;
    private float volume = 0f;

    public VolumeButton(int x, int y, int width, int height) {
        super(x + width / 2, y, VOLUME_WIDTH, VOLUME_HEIGHT);
        bounds.x -= VOLUME_WIDTH / 2;
        this.x = x;
        this.width = width;
        this.height = height;
        minX = x + VOLUME_WIDTH / 2;
        maxX = x + width - VOLUME_WIDTH / 2;
        buttonX = x + width / 2;
        loadImages();

    }

    private void loadImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
        images = new BufferedImage[3];
        for (int i = 0; i < images.length; i++)
            images[i] = temp.getSubimage(i * VOLUME_WIDTH_DEFAULT, 0, VOLUME_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);

        slider = temp.getSubimage(3 * VOLUME_WIDTH_DEFAULT, 0, SLIDER_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
    }


    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        else if (mousePressed)
            index = 2;
    }

    public void draw(Graphics g) {
        g.drawImage(slider, x, y, width, height, null);
        g.drawImage(images[index], buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, height, null);
    }

    public void changeX(int x) {
        if (x < minX)
            buttonX = minX;
        else buttonX = Math.min(x, maxX);

        updateVolumeValue();
        bounds.x = x;
    }

    private void updateVolumeValue() {
        float range = maxX - minX;
        float value = buttonX - minX;
        volume = value / range;
    }

    public void resetBools() {
        mousePressed = false;
        mouseOver = false;
    }

    public void setMouseOver(Boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public Boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(Boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public float getVolume() {
        return volume;
    }
}

