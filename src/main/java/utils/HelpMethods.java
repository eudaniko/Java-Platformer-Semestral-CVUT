// Daniil Nikonenko
// PJV Semestral

package utils;

import entities.Player;
import objects.*;

import javax.imageio.ImageIO;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static utils.Constants.Directions.*;
import static utils.Constants.GameConstants.GAME_HEIGHT;
import static utils.Constants.GameConstants.TILES_SIZE;

public class HelpMethods {

    public static boolean IsEntityInWater(Rectangle2D.Float hitBox, int[][] lvlData) {
        // Will only check if entity touch top water. Can't reach bottom water if not
        // touched top water.
        if (GetTileValue(hitBox.x, hitBox.y + hitBox.height, lvlData) != 48)
            if (GetTileValue(hitBox.x + hitBox.width, hitBox.y + hitBox.height, lvlData) != 48)
                return false;
        return true;
    }

    private static int GetTileValue(float xPos, float yPos, int[][] lvlData) {
        int xCord = (int) (xPos / TILES_SIZE);
        int yCord = (int) (yPos / TILES_SIZE);
        return lvlData[yCord][xCord];
    }

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

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];

        switch (value) {
            case 11, 48, 49:
                return false;
            default:
                return true;
        }
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

    public static boolean IsFloor(Rectangle2D.Float hitBox, int[][] lvlData) {
        if (!IsSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData))
            if (!IsSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData))
                return false;
        return true;
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


    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/gameLevels");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for (int i = 0; i < filesSorted.length; i++)
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals((i + 1) + ".png"))
                    filesSorted[i] = files[j];

            }

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for (int i = 0; i < imgs.length; i++)
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return imgs;
    }

}
