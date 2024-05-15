// Daniil Nikonenko
// PJV Semestral

package utils;


import static utils.Constants.GameConstants.SCALE;
import static utils.LoadSave.GOLD_COIN;

// Constants class containing game-related constant values
public class Constants {
    public static class GameConstants {
        public static final int TILES_DEFAULT_SIZE = 32;
        public final static float SCALE = 1.5f;
        public static final float GRAVITY = 0.04f * SCALE;
        public static final float GRAVITY_IN_WATER = 0.1f * SCALE;
        public static final int TILES_IN_WIDTH = 26;
        public static final int TILES_IN_HEIGHT = 14;
        public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
        public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
        public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

        public final static int FPS_SET = 120;
        public final static int UPS_SET = 200;
        public static final int ANI_SPEED = 25;
        public static final float MUSIC_VOLUME_DEFAULT = 0.7f;
        public static final boolean DRAW_HIT_BOX = true;

    }


    public static final class ObjectConstants {
        public static final int BLUE_POTION = 0;
        public static final int RED_POTION = 1;
        public static final int BARREL = 2;
        public static final int BOX = 3;
        public static final int SPIKE = 4;
        public static final int CANNON_LEFT = 5;
        public static final int CANNON_RIGHT = 6;
        public static final int TREE_ONE = 7;
        public static final int TREE_TWO = 8;
        public static final int TREE_THREE = 9;
        public static final int SHIP_LEFT = 10;
        public static final int SHIP_RIGHT = 11;
        public static final int GOLD_COIN = 20;
        public static final int RUM = 30;


        public static final int RED_POTION_DELTA_VALUE = 15;
        public static final int BLUE_POTION_DELTA_VALUE = 10;
        public static final int RUM_DURATION = 10;

        public static final int CONTAINER_WIDTH_DEFAULT = 40;
        public static final int CONTAINER_HEIGHT_DEFAULT = 30;
        public static final int CONTAINER_WIDTH = (int) (CONTAINER_WIDTH_DEFAULT * SCALE);
        public static final int CONTAINER_HEIGHT = (int) (CONTAINER_HEIGHT_DEFAULT * SCALE);

        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int) (POTION_WIDTH_DEFAULT * SCALE);
        public static final int POTION_HEIGHT = (int) (POTION_HEIGHT_DEFAULT * SCALE);

        public static final int SPIKE_WIDTH_DEFAULT = 32;
        public static final int SPIKE_HEIGHT_DEFAULT = 32;
        public static final int SPIKE_WIDTH = (int) (SPIKE_WIDTH_DEFAULT * SCALE);
        public static final int SPIKE_HEIGHT = (int) (SPIKE_HEIGHT_DEFAULT * SCALE);

        public static final int CANNON_WIDTH_DEFAULT = 40;
        public static final int CANNON_HEIGHT_DEFAULT = 26;
        public static final int CANNON_WIDTH = (int) (CANNON_WIDTH_DEFAULT * SCALE);
        public static final int CANNON_HEIGHT = (int) (CANNON_HEIGHT_DEFAULT * SCALE);

        public static final int CANNON_BALL_WIDTH_HEIGHT_DEFAULT = 15;
        public static final int CANNON_BALL_WIDTH_HEIGHT = (int) (CANNON_BALL_WIDTH_HEIGHT_DEFAULT * SCALE);
        public static final float CANNON_BALL_SPEED = 0.5f * SCALE;

        public static final int GRASS_RARELY = 0;

        public static final int TREE_ONE_WIDTH_DEFAULT = 39;
        public static final int TREE_ONE_HEIGHT_DEFAULT = 92;

        public static final int TREE_THREE_WIDTH_DEFAULT = 62;
        public static final int TREE_THREE_HEIGHT_DEFAULT = 54;

        public static final int SHIP_WIDTH_DEFAULT = 78;
        public static final int SHIP_HEIGHT_DEFAULT = 72;
        public static final float SHIP_SPEED = 0.3f * SCALE;

        public static final int COIN_SIZE_DEFAULT =  16;
        public static final int COIN_SIZE = (int) (COIN_SIZE_DEFAULT * SCALE);


        public static int GetSpriteAmount(int object_type) {
            return switch (object_type) {
                case BARREL, BOX -> 8;
                case RED_POTION, BLUE_POTION, CANNON_LEFT, CANNON_RIGHT, RUM -> 7;
                case TREE_ONE, TREE_TWO, TREE_THREE, SHIP_LEFT, SHIP_RIGHT, GOLD_COIN -> 4;
                default -> 1;
            };
        }
    }

    public static class EnemyConstants {
        public static final int CRABBY = 0;
        public static final int PINKSTAR = 1;
        public static final int SHARK = 2;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int CRABBY_WIDTH_DEFAULT = 72;
        public static final int CRABBY_HEIGHT_DEFAULT = 32;
        public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * SCALE);
        public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * SCALE);
        public static final int CRABBY_DRAW_OFFSET_X = (int) (26 * SCALE);
        public static final int CRABBY_DRAW_OFFSET_Y = (int) (9 * SCALE);

        public static final int PINKSTAR_WIDTH_DEFAULT = 34;
        public static final int PINKSTAR_HEIGHT_DEFAULT = 30;
        public static final int PINKSTAR_WIDTH = (int) (PINKSTAR_WIDTH_DEFAULT * SCALE);
        public static final int PINKSTAR_HEIGHT = (int) (PINKSTAR_HEIGHT_DEFAULT * SCALE);
        public static final int PINKSTAR_DRAW_OFFSET_X = (int) (9 * SCALE);
        public static final int PINKSTAR_DRAW_OFFSET_Y = (int) (7 * SCALE);

        public static final int SHARK_WIDTH_DEFAULT = 34;
        public static final int SHARK_HEIGHT_DEFAULT = 30;
        public static final int SHARK_WIDTH = (int) (SHARK_WIDTH_DEFAULT * SCALE);
        public static final int SHARK_HEIGHT = (int) (SHARK_HEIGHT_DEFAULT * SCALE);
        public static final int SHARK_DRAWOFFSET_X = (int) (8 * SCALE);
        public static final int SHARK_DRAWOFFSET_Y = (int) (6 * SCALE);

        public static int GetSpriteAmount(int enemyType, int enemyState) {
            switch (enemyState) {
                case IDLE:
                    if (enemyType == CRABBY)
                        return 9;
                    else if (enemyType == PINKSTAR || enemyType == SHARK)
                        return 8;
                case RUNNING:
                    return 6;
                case ATTACK:
                    if (enemyType == SHARK)
                        return 8;
                    return 7;
                case HIT:
                    return 4;
                case DEAD:
                    return 5;
            }

            return 0;
        }

        public static int GetMaxHealth(int enemy_type) {
            return switch (enemy_type) {
                case CRABBY -> 30;
                case PINKSTAR -> 60;
                case SHARK -> 50;
                default -> 0;
            };
        }

        public static int GetEnemyDamage(int enemyType) {
            return switch (enemyType) {
                case CRABBY -> 10;
                case PINKSTAR -> 60;
                case SHARK -> 40;
                default -> 100;
            };
        }

    }

    public static class Environment {
        public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
        public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
        public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
        public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

        public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * SCALE);
        public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * SCALE);
        public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * SCALE);


    }

    public static class UI {
        public static class StatusBar {
            // Status Bar
            public static final int statusBarWidth = (int) (192 * SCALE);
            public static final int statusBarHeight = (int) (58 * SCALE);
            public static final int statusBarX = (int) (10 * SCALE);
            public static final int statusBarY = (int) (10 * SCALE);

            // Health Bar
            public static final int healthBarWidth = (int) (150 * SCALE);
            public static final int healthBarHeight = (int) (4 * SCALE);
            public static final int healthBarXStart = (int) (34 * SCALE);
            public static final int healthBarYStart = (int) (14 * SCALE);

            // Power Bar
            public static final int PowerBarWidth = (int) (104 * SCALE);
            public static final int PowerBarHeight = (int) (2 * SCALE);
            public static final int PowerBarXStart = (int) (44 * SCALE);
            public static final int PowerBarYStart = (int) (34 * SCALE);
        }

        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * SCALE);

        }

        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * SCALE);
        }

        public static class URMButtons {
            public static final int URM_SIZE_DEFAULT = 56;
            public static final int URM_SIZE = (int) (URM_SIZE_DEFAULT * SCALE);
        }

        public static class VolumeButtons {
            public static final int VOLUME_WIDTH_DEFAULT = 28;
            public static final int VOLUME_HEIGHT_DEFAULT = 44;
            public static final int SLIDER_WIDTH_DEFAULT = 215;

            public static final int VOLUME_WIDTH = (int) (VOLUME_WIDTH_DEFAULT * SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_HEIGHT_DEFAULT * SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_WIDTH_DEFAULT * SCALE);

        }
    }

    // Directions constants for player movement
    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    // Player animation constants
    public static class PlayerConstants {
        public static final int PLAYER_GREEN_INDEX = 100;
        public static final int PLAYER_DAMAGE = 10;
        public static final int PLAYER_ATTACK_BOX_WIDTH = 30;
        public static final int PLAYER_ATTACK_BOX_HEIGHT = 30;
        public static final int PLAYER_MAX_HEALTH = 100;
        public static final int PLAYER_MAX_POWER = 100;
        public static final int MAX_ATTACKS_AMOUNT = 1;
        public static final int POWER_ATTACK_DAMAGE = 20;
        public static final int POWER_FOR_ATTACK = (PLAYER_MAX_POWER / MAX_ATTACKS_AMOUNT);
        public static final int POWER_GROW_SPEED = 15;
        public static final float PLAYER_WALK_SPEED = SCALE;
        public static final float PUSH_BACK_SPEED = 0.75f * SCALE;

        public static final float xDrawOffset = 21 * SCALE;
        public static final float yDrawOffset = 3 * SCALE;
        public static final float jumpSpeed = -2.25f * SCALE;
        public static final float fallSpeedAfterCollision = 0.5f * SCALE;


        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int ATTACK = 4;
        public static final int HIT = 5;
        public static final int DEAD = 6;

        // Method to get the number of sprite frames based on player action type
        public static int GetSpriteAmount(int playerAction) {
            return switch (playerAction) {
                case DEAD -> 8;
                case RUNNING -> 6;
                case IDLE -> 5;
                case HIT, JUMP, ATTACK -> 3;
                default -> 1;
            };
        }
    }
}
