// Daniil Nikonenko
// 19.03.2024
// PJV Semestral: GamePanel
// Main class that launch the game.

package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {

    private  MouseInputs mouseInputs;
    private int xDelta = 100, yDelta = 100;

    private BufferedImage img;
    public GamePanel(){
        mouseInputs = new MouseInputs(this);

        img = importImg("/player_sprites.png");

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private BufferedImage importImg(String fileAdress) {
        InputStream is = getClass().getResourceAsStream(fileAdress);

        try {
            return ImageIO.read(is);
        } catch (IOException e) {
            //TO DO catch
            throw new RuntimeException(e);
        }

    }

    private void setPanelSize(){
        Dimension size = new Dimension(1280,800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }
    public void changeXDelta(int value){
        xDelta += value;
    }

    public void changeYDelta(int value){
        yDelta += value;
    }

    public  void paintComponent (Graphics g){
        super.paintComponent(g);
        g.drawImage(img.getSubimage(0,0,64,40),xDelta, yDelta, 128, 80, null);
    }

}
