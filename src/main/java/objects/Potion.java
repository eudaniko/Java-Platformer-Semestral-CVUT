package objects;


import main.Game;

public class Potion extends GameObject {


    public Potion(int x, int y, int objectType) {
        super(x, y, objectType);
        doAnimation = true;
        initHitBox(7, 14);
        this.xDrawOffset = (int) (3 * Game.SCALE);
        this.yDrawOffset = (int) (2 * Game.SCALE);

    }

    public void update() {
        if(doAnimation)
            updateAnimationTick();

    }
}


