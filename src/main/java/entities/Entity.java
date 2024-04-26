// Daniil Nikonenko
// PJV Semestral

package entities;

import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.DEAD;
import static utils.Constants.GameConstants.DRAW_HIT_BOX;
import static utils.Constants.GameConstants.SCALE;
import static utils.HelpMethods.CanMoveHere;

public abstract class Entity {

    protected boolean active = true;
    protected float x, y;
    protected int width, height;
    protected int flipX = 0, flipW = 1;
    protected int walkDir;
    protected Rectangle2D.Float hitBox;
    protected BufferedImage[][] sprites;
    protected int aniTick, aniIndex;
    protected int state;
    protected float airSpeed = 5;
    protected boolean inAir = true;
    protected int maxHealth, currentHealth;
    protected Rectangle2D.Float attackBox;
    protected boolean hasAttackBox;
    protected float walkSpeed;
    protected float xDrawOffset;
    protected float yDrawOffset;

    protected int pushBackDir;
    protected float pushDrawOffset;
    protected int pushBackOffsetDir = UP;


    public Entity(float x, float y, int width, int height,
                  String entityAtlas, int aniAmount, int maxAniLength,
                  int widthDefault, int heightDefault) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        loadAnimations(entityAtlas, aniAmount, maxAniLength, widthDefault, heightDefault);
    }

    protected void loadAnimations(String entityAtlas, int aniAmount, int maxAniLength, int widthDefault, int heightDefault) {
        BufferedImage img = LoadSave.GetSpriteAtlas(entityAtlas);
        sprites = new BufferedImage[aniAmount][maxAniLength];

        for (int j = 0; j < aniAmount; j++) {
            for (int i = 0; i < maxAniLength; i++) {
                sprites[j][i] = img.getSubimage(i * widthDefault, j * heightDefault, widthDefault, heightDefault);
            }
        }
    }

    protected void draw(Graphics g, int xLevelOffset) {
        if (active) {
            g.drawImage(sprites[state][aniIndex],
                    (int) (hitBox.x - xDrawOffset) - xLevelOffset + flipX,
                    (int) (hitBox.y - yDrawOffset),
                    width * flipW, height, null);
            if (DRAW_HIT_BOX) {
                drawHitBox(g, xLevelOffset);
                drawAttackBox(g, xLevelOffset);
            }
        }

    }

    //for debugging the hitBox
    protected void drawHitBox(Graphics g, int xLevelOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x - xLevelOffset, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }


    protected void drawAttackBox(Graphics g, int xLevelOffset) {
        g.setColor(Color.red);
        if (hasAttackBox)
            g.drawRect((int) (attackBox.x - xLevelOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    public void changeHealth(int deltaHealth, Entity e) {
        if (state == Constants.PlayerConstants.HIT && this.getClass() == Player.class)
            return;
        currentHealth += deltaHealth;
        if (currentHealth <= 0)
            setEntityState(DEAD);
        else {
            if (this.getClass() == Player.class)
                setEntityState(Constants.PlayerConstants.HIT);
            else
                setEntityState(Constants.EnemyConstants.HIT);
        }

        if (e == null)
            return;
        pushBackOffsetDir = UP;
        pushDrawOffset = 0;
        if (e.getHitBox().x < hitBox.x)
            pushBackDir = RIGHT;
        else
            pushBackDir = LEFT;
    }

    protected void updatePushBackDrawOffset() {
        float speed = 1f;
        float limit = -30f;

        if (pushBackOffsetDir == UP) {
            pushDrawOffset -= speed;
            if (pushDrawOffset <= limit)
                pushBackOffsetDir = DOWN;
        } else {
            pushDrawOffset += speed;
            if (pushDrawOffset >= 0)
                pushDrawOffset = 0;
        }
    }

    protected void pushBack(int pushBackDir, int[][] lvlData, float speedMulti) {
        float xSpeed = 0;
        if (pushBackDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (CanMoveHere(hitBox.x + xSpeed * speedMulti, hitBox.y, hitBox.width, hitBox.height, lvlData))
            hitBox.x += xSpeed * speedMulti;
    }

    protected void initHitBox(int width, int height) {
        hitBox = new Rectangle2D.Float(x, y, (int) (width * SCALE), (int) (height * SCALE));
    }

    protected void initAttackBox(float x, float y, int width, int height) {
        this.hasAttackBox = true;
        attackBox = new Rectangle2D.Float(x, y, (int) (width * SCALE), (int) (height * SCALE));
    }


    protected void updateAttackBoxDir(int walkDir) {
        attackBox.y = hitBox.y;
        if (walkDir == RIGHT)
            attackBox.x = hitBox.x;
        else
            attackBox.x = hitBox.x - hitBox.width;
    }

    protected void flipByDir(int walkDir) {
        if (walkDir == RIGHT) {
            flipW = -1;
            flipX = width;
        } else {
            flipW = 1;
            flipX = 0;

        }
    }


    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public int getEntityState() {
        return state;
    }


    public int getAniIndex() {
        return aniIndex;
    }

    protected void setEntityState(int state) {
        this.state = state;
        aniTick = 0;
        aniIndex = 0;
    }

}
