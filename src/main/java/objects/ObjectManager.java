// Daniil Nikonenko
// PJV Semestral

package objects;

import gamestates.Playing;
import levels.Level;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import static utils.Constants.ObjectConstants.*;
import static utils.HelpMethods.IsTileSolid;

public class ObjectManager {

    private final Playing playing;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> gameContainers;
    private ArrayList<Spike> spikes;
    private ArrayList<Cannon> cannons;
    private final ArrayList<Projectile> projectiles;
    private ArrayList<Grass> grasses;
    private ArrayList<Tree> trees;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        projectiles = new ArrayList<>();
    }

    public void loadObjects(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        gameContainers = new ArrayList<>(newLevel.getGameContainers());
        spikes = new ArrayList<>(newLevel.getSpikes());
        cannons = new ArrayList<>(newLevel.getCannons());
        grasses = new ArrayList<>(newLevel.getGrasses());
        trees = new ArrayList<>(newLevel.getTrees());
    }


    public void update() {
        updateObjects(potions);
        updateObjects(gameContainers);
        updateCannons();
        updateObjects(projectiles);
        checkProjectilesTouched();
    }


    private void updateObjects(ArrayList<? extends GameObject> objects) {
        for (GameObject obj : objects)
            if (obj.isActive())
                obj.update();
    }

    private void updateCannons() {
        for (Cannon c : cannons)
            if (c.isActive())
                c.update(playing.getPlayer(), playing.getLevelManager().getCurrentLevel().getLevelData(), projectiles);
    }


    public void draw(Graphics g, int xLevelOffset) {
        drawGameObjects(g, xLevelOffset, potions);
        drawGameObjects(g, xLevelOffset, gameContainers);
        drawGameObjects(g, xLevelOffset, spikes);
        drawGameObjects(g, xLevelOffset, cannons);
        drawGameObjects(g, xLevelOffset, projectiles);
        drawGameObjects(g, xLevelOffset, grasses);
        drawGameObjects(g, xLevelOffset, trees);

    }

    private void drawGameObjects(Graphics g, int xLevelOffset, ArrayList<? extends GameObject> arrayList) {
        for (GameObject gameObject : arrayList)
            gameObject.draw(g, xLevelOffset);
    }


    public void checkPotionsTouched() {
        for (Potion p : potions)
            if (p.isActive())
                if (playing.getPlayer().getHitBox().intersects(p.getHitBox())) {
                    p.setActive(false);
                    applyEffectsToPlayer(p);
                }
    }

    public void checkSpikesTouched() {
        for (Spike s : spikes)
            if (playing.getPlayer().getHitBox().intersects(s.getHitBox()))
                playing.getPlayer().changeHealth(-9999);
    }

    public void checkProjectilesTouched() {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile p = iterator.next();
            if (p.getHitBox().intersects(playing.getPlayer().getHitBox())) {
                playing.getPlayer().changeHealth(-30);
                p.setActive(false);
                iterator.remove();
            } else if (IsTileSolid(p.getTileX(), p.getTileY(), playing.getLevelManager().getCurrentLevel().getLevelData())) {
                p.setActive(false);
                iterator.remove();
            }
        }
    }

    public void applyEffectsToPlayer(Potion p) {
        switch (p.getObjectType()) {
            case RED_POTION:
                playing.getPlayer().changeHealth(RED_POTION_VALUE);
                break;
            case BLUE_POTION:
                playing.getPlayer().changePower(BLUE_POTION_VALUE);
                break;
        }
    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        for (GameContainer gc : gameContainers)
            if (gc.isActive() && !gc.doAnimation())
                if (gc.getHitBox().intersects(attackBox)) {
                    gc.setAnimation(true);
                    int type = RED_POTION;
                    if (gc.getObjectType() == BARREL)
                        type = BLUE_POTION;
                    potions.add(new Potion((int) (gc.getHitBox().x + gc.getHitBox().width / 2), (int) (gc.getHitBox().y - gc.getHitBox().height / 2), type));
                    return;
                }
    }

    public void resetAllObjects() {
        loadObjects(playing.getLevelManager().getCurrentLevel());
        for (Potion p : potions)
            p.reset();

        for (GameContainer gc : gameContainers)
            gc.reset();

        for (Cannon c : cannons)
            c.reset();
    }

}
