package utils;

import main.Game;

import java.awt.geom.Rectangle2D;

import static utils.Constants.Directions.*;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData) {
        if (!IsSolid(x, y, levelData))
            if (!IsSolid(x + width, y + height, levelData))
                if (!IsSolid(x + width, y, levelData))
                    if (!IsSolid(x, y + height, levelData))
                        return true;
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] levelData) {
        int maxWidth = levelData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, levelData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] levelData){
        int value = levelData[yTile][xTile];

        if (value >= 48 || value < 0 || value != 11)
            return true;
        return false;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
        int currentTile = (int) (hitBox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            //Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitBox.width);
            return tileXPos + xOffset - 1;
        } else
            //Left
            return currentTile * Game.TILES_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = (int) (hitBox.y / Game.TILES_SIZE);

        if (airSpeed > 0) {
            //Falling
            int tilePos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitBox.height);
            return tilePos + yOffset - 1;
        } else {
            //Jumping
            return currentTile * Game.TILES_SIZE;
        }

    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] levelData) {
        // Check the pixel below bottomLeft and bottomRight
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData))
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData))
                return false;
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int walkDir,int[][] levelData) {
        if (walkDir == RIGHT)
            return IsSolid(hitbox.x + xSpeed + hitbox.width, hitbox.y + hitbox.height + 1, levelData);
        else
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, levelData);
    }
    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] levelData){
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, levelData))
                return false;
        }
        return true;
    }

    public static boolean isSightClear(int[][] levelData, Rectangle2D.Float firstHitbox,
                                       Rectangle2D.Float secondHitbox, int yTile){
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

        if (firstXTile > secondXTile)
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, levelData);
        else
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, levelData);
    }
}
