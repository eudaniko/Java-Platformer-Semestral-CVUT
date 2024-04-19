// Daniil Nikonenko
// PJV Semestral

package utils;

import entities.Crabby;
import entities.Player;
import levels.Level;
import objects.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.CRABBY;
import static utils.Constants.GameConstants.GAME_HEIGHT;
import static utils.Constants.GameConstants.TILES_SIZE;
import static utils.Constants.ObjectConstants.*;

public class HelpMethods {

    public static boolean CanCannonSeePlayer(Player player, Cannon cannon, int yTile, int[][] levelData) {
        int firstXTile = (int) (cannon.getHitBox().x / TILES_SIZE);
        int secondXTile = (int) (player.getHitBox().x / TILES_SIZE);

        if (firstXTile > secondXTile)
            return IsAllTilesClear(secondXTile, firstXTile, yTile, levelData);
        else
            return IsAllTilesClear(firstXTile, secondXTile, yTile, levelData);
    }


    public static boolean IsAllTilesClear(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++)
            if (IsTileSolid(xStart + i, y, lvlData))
                return false;
        return true;
    }

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
        if (!IsSolid(x, y, levelData))
            if (!IsSolid(x + width, y + height, levelData))
                if (!IsSolid(x + width, y, levelData))
                    return !IsSolid(x, y + height, levelData);
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] levelData) {
        int maxWidth = levelData[0].length * TILES_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= GAME_HEIGHT)
            return true;

        float xIndex = x / TILES_SIZE;
        float yIndex = y / TILES_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, levelData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] levelData) {
        int value = levelData[yTile][xTile];

        return value != 11;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = (int) (hitBox.x / TILES_SIZE);
        if (xSpeed > 0) {
            //Right
            int tileXPos = currentTile * TILES_SIZE;
            int xOffset = (int) (TILES_SIZE - hitBox.width);
            return tileXPos + xOffset - 1;
        } else
            //Left
            return currentTile * TILES_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = (int) (hitBox.y / TILES_SIZE);

        if (airSpeed > 0) {
            //Falling
            int tilePos = currentTile * TILES_SIZE;
            int yOffset = (int) (TILES_SIZE - hitBox.height);
            return tilePos + yOffset - 1;
        } else {
            //Jumping
            return currentTile * TILES_SIZE;
        }

    }

    public static boolean IsEntityUpToFloor(Rectangle2D.Float hitBox, int[][] levelData) {
        // Check the pixel below bottomLeft and bottomRight
        if (!IsSolid(hitBox.x, hitBox.y + hitBox.height + 1, levelData))
            return !IsSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, levelData);
        return false;
    }

    public static boolean IsFloor(Rectangle2D.Float hitBox, float xSpeed, int walkDir, int[][] levelData) {
        if (walkDir == RIGHT)
            return IsSolid(hitBox.x + xSpeed + hitBox.width, hitBox.y + hitBox.height + 1, levelData);
        else
            return IsSolid(hitBox.x + xSpeed, hitBox.y + hitBox.height + 1, levelData);
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] levelData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, levelData))
                return false;
        }
        return true;
    }

    public static boolean isSightClear(int[][] levelData, Rectangle2D.Float firstHitBox,
                                       Rectangle2D.Float secondHitBox, int yTile) {
        int firstXTile = (int) (firstHitBox.x / TILES_SIZE);
        int secondXTile = (int) (secondHitBox.x / TILES_SIZE);

        if (firstXTile > secondXTile)
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, levelData);
        else
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, levelData);
    }


    public static int[][] GetLevelData(BufferedImage image) {
        int[][] levelData = new int[image.getHeight()][image.getWidth()];


        for (int j = 0; j < image.getHeight(); j++)
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getRed();

                if (value >= 48) value = 0;
                levelData[j][i] = value;
            }
        return levelData;
    }

    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/gameLevels");
        File file = null;

        try {
            if (url != null) {
                file = new File(url.toURI());
            }
        } catch (URISyntaxException e) {
            System.err.println("Cannot read gameLevels URL!");
            throw new RuntimeException(e);
        }

        File[] files = Objects.requireNonNull(file).listFiles();
        File[] filesSorted = new File[0];
        if (files != null) {
            filesSorted = new File[files.length];
        }

        for (int i = 0; i < filesSorted.length; i++)
            for (File f : files) {
                if (f.getName().equals((i + 1) + ".png"))
                    filesSorted[i] = f;
            }

        BufferedImage[] levelImages = new BufferedImage[filesSorted.length];

        for (int i = 0; i < levelImages.length; i++) {
            try {
                levelImages[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return levelImages;
    }

    public static ArrayList<Crabby> GetCrabs(BufferedImage image) {
        ArrayList<Crabby> list = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++)
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == CRABBY)
                    list.add(new Crabby(i * TILES_SIZE, j * TILES_SIZE));
            }

        return list;
    }

    public static Point GetPlayerSpawn(BufferedImage image) {
        for (int j = 0; j < image.getHeight(); j++)
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100)
                    return new Point(i * TILES_SIZE, j * TILES_SIZE);
            }

        return new Point(TILES_SIZE, TILES_SIZE);


    }

    public static ArrayList<Potion> GetPotions(BufferedImage image) {
        ArrayList<Potion> list = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++)
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if (value == RED_POTION || value == BLUE_POTION)
                    list.add(new Potion(i * TILES_SIZE, j * TILES_SIZE, value));
            }

        return list;
    }

    public static ArrayList<GameContainer> GetGameContainers(BufferedImage image) {
        ArrayList<GameContainer> list = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++)
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if (value == BOX || value == BARREL)
                    list.add(new GameContainer(i * TILES_SIZE, j * TILES_SIZE, value));
            }

        return list;
    }

    public static ArrayList<Spike> GetSpikes(BufferedImage image) {
        ArrayList<Spike> list = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++)
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if (value == SPIKE)
                    list.add(new Spike(i * TILES_SIZE, j * TILES_SIZE, value));
            }

        return list;
    }

    public static ArrayList<Cannon> GetCannons(BufferedImage image) {
        ArrayList<Cannon> list = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++)
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if (value == CANNON_LEFT || value == CANNON_RIGHT)
                    list.add(new Cannon(i * TILES_SIZE, j * TILES_SIZE, value));
            }

        return list;
    }

    public static ArrayList<Grass> GetGrasses(int[][] levelData, Level level) {
        ArrayList<Grass> list = new ArrayList<>();
        Random random = new Random();

        for (int y = 0; y < Constants.GameConstants.TILES_IN_HEIGHT; y++)
            for (int x = 0; x < levelData[0].length; x++) {
                int index = level.getTileSpriteIndex(x, y);
                if (index >= 0 && index <= 4 || index >= 31 && index <= 33 || index == 28 || index == 29 || index >= 34 && index <= 37) {
                    // outside_sprites.png grass indexes
                    int grassType = random.nextInt(-(GRASS_RARELY), 2);
                    if (grassType >= 0)
                        list.add(new Grass(x, y, grassType));
                }
            }

        return list;
    }
}
