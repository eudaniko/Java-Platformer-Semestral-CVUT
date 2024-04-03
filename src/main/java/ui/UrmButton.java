package ui;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.URMButtons.*;

public class UrmButton extends PauseButton {

    private BufferedImage[] images;
    private int rowIndex, index;
    private Boolean mouseOver = false, mousePressed = false ;

    public UrmButton(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImages();
    }

    private void loadImages() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
        images = new BufferedImage[3];

        for (int i = 0; i < images.length; i++)
            images[i] = temp.getSubimage(i * URM_SIZE_DEFAULT, rowIndex * URM_SIZE_DEFAULT, URM_SIZE_DEFAULT, URM_SIZE_DEFAULT);

    }


    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        else if (mousePressed)
            index = 2;
    }

    public void draw(Graphics g) {
        g.drawImage(images[index], x, y, URM_SIZE, URM_SIZE, null);
    }

    public void resetBools() {
        mousePressed = false;
        mouseOver = false;
    }

    public Boolean isMouseOver() {
        return mouseOver;
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
}
