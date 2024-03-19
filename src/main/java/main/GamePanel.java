// Daniil Nikonenko
// 19.03.2024
// PJV Semestral: GamePanel
// Main class that launch the game.

package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.JPanel;
import java.awt.Graphics;

public class GamePanel extends JPanel {

    private  MouseInputs mouseInputs;
    private int xDelta = 100, yDelta = 100;
    public GamePanel(){
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

    }

    public void ChangeXDelta(int value){
        xDelta += value;
        repaint();
    }

    public void ChangeYDelta(int value){
        yDelta += value;
        repaint();
    }

    public void SetRecPosition(int x, int y ){
        this.xDelta = x;
        this. yDelta = y;
        repaint();
    }

    public  void paintComponent (Graphics g){
        super.paintComponent(g);
        g.fillRect(xDelta,yDelta, 200, 50);
    }
}
