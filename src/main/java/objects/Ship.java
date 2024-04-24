package objects;

import entities.Player;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.GameConstants.SCALE;
import static utils.Constants.ObjectConstants.*;
import static utils.HelpMethods.CanMoveHere;

public class Ship extends GameObject {

    protected BufferedImage[] sprites;
    private int startX, startY;
    protected float hoverOffset;
    private int hoverDir = -1;
    private int dir = -1;
    private float shipSpeed = SHIP_SPEED * dir;
    private boolean shipMoving, arrived;

    public Ship(int x, int y, int objectType) {
        super(x, y - 14, objectType);
        startX = this.x;
        startY = this.y;

        yDrawOffset = (int) (50 * SCALE);

        int shipWidth = SHIP_WIDTH_DEFAULT;
        if (objectType == SHIP_RIGHT)
            shipWidth -= 10;
        initHitBox(shipWidth, 30);

    }

    public void update(Player player, int[][] levelData) {
        super.update();
        updatePos(levelData);
        updateHover(5, 0.02f);
        if (!arrived) {
            if (hitBox.intersects(player.getHitBox())) {
                player.getHitBox().x += shipSpeed;
                player.getAttackBox().x += shipSpeed;
                shipMoving = true;
            }
        }
    }

    private void updatePos(int[][] levelData) {
        if (shipMoving)
            if (CanMoveHere(hitBox.x, hitBox.y, hitBox.width, hitBox.height, levelData)) {
                hitBox.x += shipSpeed;
            } else {
                shipMoving = false;
                arrived = true;
            }
    }

    public void draw(Graphics g, int xLevelOffset) {
        super.draw(g, xLevelOffset);
        g.drawImage(sprites[getAniIndex()],
                (int) (hitBox.x - xLevelOffset), (int) (hitBox.y - yDrawOffset),
                (int) (SHIP_WIDTH_DEFAULT * SCALE), (int) (SHIP_HEIGHT_DEFAULT * SCALE), null);
    }

    protected void loadSprites() {
        sprites = new BufferedImage[4];

        String spriteAtlas;
        if (objectType == SHIP_LEFT)
            spriteAtlas = LoadSave.SHIP_LEFT_ATLAS;
        else spriteAtlas = LoadSave.SHIP_RIGHT_ATLAS;

        BufferedImage grassAtlas = LoadSave.GetSpriteAtlas(spriteAtlas);
        for (int i = 0; i < sprites.length; i++)
            sprites[i] = grassAtlas.getSubimage(i * SHIP_WIDTH_DEFAULT, 0, SHIP_WIDTH_DEFAULT, SHIP_HEIGHT_DEFAULT);

    }

    private void updateHover(int maxHoverOffset, float aniSpeed) {
        hoverOffset += (aniSpeed * SCALE * hoverDir);
        if (hoverOffset >= maxHoverOffset)
            hoverDir = -1;
        else if (hoverOffset < 0)
            hoverDir = 1;
        this.hitBox.y = y + hoverOffset;
    }

    public void changeDir() {
        if (dir == 1) dir = -1;
        else dir = 1;


    }

    public void reset() {
        this.hitBox.x = startX;
        this.hitBox.y = startY;
        shipMoving = false;
        arrived = false;
    }

}
