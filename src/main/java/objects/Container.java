package objects;

import main.Game;

import java.awt.*;

import static utils.Constants.ObjectConstants.*;

public class Container extends GameObject{


    public Container(int x, int y, int objectType) {
        super(x, y, objectType);
        doAnimation = false;
        createHitBox();
    }

    private void createHitBox() {
        if (objectType ==BOX){
            initHitBox(25, 18);
            xDrawOffset = (int) (7 * Game.SCALE);
            yDrawOffset = (int) (12 * Game.SCALE);
        }
        else if (objectType == BARREL) {
            initHitBox(23, 15);
            xDrawOffset = (int) (8 * Game.SCALE);
            yDrawOffset = (int) (5 * Game.SCALE);
        }
    }

    public void update(){
        if(doAnimation)
            updateAnimationTick();
    }
}
