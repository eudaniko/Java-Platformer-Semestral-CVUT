// Daniil Nikonenko
// PJV Semestral

package objects;

import entities.Crabby;
import entities.Enemy;
import entities.Player;
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
    private final ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<Grass> grasses;
    private ArrayList<Tree> trees;
    private ArrayList<Ship> ships;

    private Level currentLevel;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        currentLevel = playing.getLevelManager().getCurrentLevel();
    }

    public void loadObjects(Level newLevel) {

        currentLevel = newLevel;
        potions = new ArrayList<>(newLevel.getPotions());
        gameContainers = new ArrayList<>(newLevel.getGameContainers());
        spikes = new ArrayList<>(newLevel.getSpikes());
        cannons = new ArrayList<>(newLevel.getCannons());
        grasses = new ArrayList<>(newLevel.getGrasses());
        trees = new ArrayList<>(newLevel.getTrees());
        ships = new ArrayList<>(newLevel.getShips());
        projectiles.clear();
    }


    public void update() {
        updateObjects(potions);
        updateObjects(gameContainers);
        updateObjects(trees);
        updateObjects(ships);
        updateShips();
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

    private void updateShips() {
        for (Ship ship : ships)
            if (ship.isActive()) {
                Player player = playing.getPlayer();
                checkPlayerOnShip(ship, player);
                ship.update(player, playing.getLevelManager().getCurrentLevel().getLevelData());
            }
    }


    public void draw(Graphics g, int xLevelOffset) {
        drawGameObjects(g, xLevelOffset, potions);
        drawGameObjects(g, xLevelOffset, gameContainers);
        drawGameObjects(g, xLevelOffset, spikes);
        drawGameObjects(g, xLevelOffset, cannons);
        drawGameObjects(g, xLevelOffset, projectiles);
        drawGameObjects(g, xLevelOffset, grasses);
        drawGameObjects(g, xLevelOffset, trees);
        drawGameObjects(g, xLevelOffset, ships);

    }

    private void drawGameObjects(Graphics g, int xLevelOffset, ArrayList<? extends GameObject> arrayList) {
        for (GameObject gameObject : arrayList)
            if (gameObject.isActive())
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
        for (Spike s : spikes){
            if (playing.getPlayer().getHitBox().intersects(s.getHitBox()))
                playing.getPlayer().changeHealth(-9999, null);
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

    public void checkProjectilesTouched() {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
            Projectile p = iterator.next();
            if (p.getHitBox().intersects(playing.getPlayer().getHitBox())) {
                playing.getPlayer().changeHealth(-30, null);
                p.setActive(false);
                iterator.remove();
            } else if (IsTileSolid(p.getTileX(), p.getTileY(), playing.getLevelManager().getCurrentLevel().getLevelData())) {
                p.setActive(false);
                iterator.remove();
            }
        }
    }

    public void checkPlayerOnShip(Ship ship, Player player) {
        if (ship.getHitBox().intersects(player.getHitBox())) {
            player.setOnShip(true);
        } else {
            player.setOnShip(false);
        }


    }

    public void applyEffectsToPlayer(Potion p) {
        switch (p.getObjectType()) {
            case RED_POTION:
                playing.getPlayer().changeHealth(RED_POTION_DELTA_VALUE, null);
                break;
            case BLUE_POTION:
                playing.getPlayer().changePower(BLUE_POTION_DELTA_VALUE);
                break;
        }
    }


    public void resetAllObjects() {
        resetObject(potions);
        resetObject(gameContainers);
        resetObject(cannons);
        resetObject(projectiles);
        resetObject(ships);
        loadObjects(playing.getLevelManager().getCurrentLevel());
    }

    private void resetObject(ArrayList<? extends GameObject> list) {
        for (GameObject gameObject : list)
            gameObject.reset();
    }

}
