// Daniil Nikonenko
// PJV Semestral

package utils;

// Constants class containing game-related constant values
public class Constants {
    public static class GameConstants {
        public static final int TILES_DEFAULT_SIZE = 32;
        public final static float SCALE = 1.5f;
        public static final float GRAVITY = 0.04f * GameConstants.SCALE;
        public static final int TILES_IN_WIDTH = 26;
        public static final int TILES_IN_HEIGHT = 14;
        public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
        public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
        public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

        public final static int FPS_SET = 120;
        public final static int UPS_SET = 200;
        public static final int ANI_SPEED = 25;
        public static final boolean DRAW_HIT_BOX = false;
    }


    public static final class ObjectConstants {
        public static final int RED_POTION = 1;
        public static final int BLUE_POTION = 0;
        public static final int BARREL = 2;
        public static final int BOX = 3;
        public static final int SPIKE = 4;
        public static final int CANNON_LEFT = 5;
        public static final int CANNON_RIGHT = 6;
        public static final int S_TREE = 7;
        public static final int A_TREE = 8;

        public static final int RED_POTION_VALUE = 15;
        public static final int BLUE_POTION_VALUE = 10;

        public static final int CONTAINER_WIDTH_DEFAULT = 40;
        public static final int CONTAINER_HEIGHT_DEFAULT = 30;
        public static final int CONTAINER_WIDTH = (int) (CONTAINER_WIDTH_DEFAULT * GameConstants.SCALE);
        public static final int CONTAINER_HEIGHT = (int) (CONTAINER_HEIGHT_DEFAULT * GameConstants.SCALE);

        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int) (POTION_WIDTH_DEFAULT * GameConstants.SCALE);
        public static final int POTION_HEIGHT = (int) (POTION_HEIGHT_DEFAULT * GameConstants.SCALE);

        public static final int SPIKE_WIDTH_DEFAULT = 32;
        public static final int SPIKE_HEIGHT_DEFAULT = 32;
        public static final int SPIKE_WIDTH = (int) (SPIKE_WIDTH_DEFAULT * GameConstants.SCALE);
        public static final int SPIKE_HEIGHT = (int) (SPIKE_HEIGHT_DEFAULT * GameConstants.SCALE);

        public static final int CANNON_WIDTH_DEFAULT = 40;
        public static final int CANNON_HEIGHT_DEFAULT = 26;
        public static final int CANNON_WIDTH = (int) (CANNON_WIDTH_DEFAULT * GameConstants.SCALE);
        public static final int CANNON_HEIGHT = (int) (CANNON_HEIGHT_DEFAULT * GameConstants.SCALE);

        public static final int CANNON_BALL_WIDTH_HEIGHT_DEFAULT = 15;
        public static final int CANNON_BALL_WIDTH_HEIGHT = (int) (CANNON_BALL_WIDTH_HEIGHT_DEFAULT * GameConstants.SCALE);
        public static final float CANNON_BALL_SPEED = 0.5f * GameConstants.SCALE;

        public static final int GRASS_WIDTH_HEIGHT_DEFAULT = 32;
        public static final int GRASS_WIDTH_HEIGHT = (int) (GRASS_WIDTH_HEIGHT_DEFAULT * GameConstants.SCALE);
        public static final int GRASS_RARELY = 2;

        public static final int S_TREE_WIDTH_DEFAULT = 39;
        public static final int S_TREE_HEIGHT_DEFAULT = 92;
        public static final int S_TREE_WIDTH = (int) (S_TREE_WIDTH_DEFAULT * GameConstants.SCALE);
        public static final int S_TREE_HEIGHT = (int) (S_TREE_HEIGHT_DEFAULT * GameConstants.SCALE);

        public static final int A_TREE_WIDTH_DEFAULT = 62;
        public static final int A_TREE_HEIGHT_DEFAULT = 54;
        public static final int A_TREE_WIDTH = (int) (S_TREE_WIDTH_DEFAULT * GameConstants.SCALE);
        public static final int A_TREE_HEIGHT = (int) (S_TREE_HEIGHT_DEFAULT * GameConstants.SCALE);


        public static int GetSpriteAmount(int object_type) {
            return switch (object_type) {
                case BARREL, BOX -> 8;
                case RED_POTION, BLUE_POTION, CANNON_LEFT, CANNON_RIGHT -> 7;
                case A_TREE, S_TREE -> 4;
                default -> 1;
            };
        }
    }

    public static class EnemyConstants {
        public static final int CRABBY = 0;

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        public static final int CRABBY_WIDTH_DEFAULT = 72;
        public static final int CRABBY_HEIGHT_DEFAULT = 32;

        public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * GameConstants.SCALE);
        public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * GameConstants.SCALE);

        public static final int CRABBY_DRAW_OFFSET_X = (int) (26 * GameConstants.SCALE);
        public static final int CRABBY_DRAW_OFFSET_Y = (int) (9 * GameConstants.SCALE);

        public static int GetSpriteAmount(int enemyType, int enemyState) {
            if (enemyType == CRABBY) {
                switch (enemyState) {
                    case IDLE:
                        return 9;
                    case RUNNING:
                        return 6;
                    case ATTACK:
                        return 7;
                    case HIT:
                        return 4;
                    case DEAD:
                        return 5;
                }
            }
            return 0;
        }

        public static int GetMaxHealth(int enemy_type) {
            return switch (enemy_type) {
                case CRABBY -> 30;
                default -> 0;
            };
        }

        public static int GetEnemyDamage(int enemyType) {
            if (enemyType == CRABBY) {
                return 10;
            }
            return 100;
        }

    }

    public static class Environment {
        public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
        public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
        public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
        public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

        public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * GameConstants.SCALE);
        public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * GameConstants.SCALE);
        public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * GameConstants.SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * GameConstants.SCALE);


    }

    public static class UI {
        public static class StatusBar {
            // Status Bar
            public static final int statusBarWidth = (int) (192 * GameConstants.SCALE);
            public static final int statusBarHeight = (int) (58 * GameConstants.SCALE);
            public static final int statusBarX = (int) (10 * GameConstants.SCALE);
            public static final int statusBarY = (int) (10 * GameConstants.SCALE);

            // Health Bar
            public static final int healthBarWidth = (int) (150 * GameConstants.SCALE);
            public static final int healthBarHeight = (int) (4 * GameConstants.SCALE);
            public static final int healthBarXStart = (int) (34 * GameConstants.SCALE);
            public static final int healthBarYStart = (int) (14 * GameConstants.SCALE);

            // Power Bar
            public static final int PowerBarWidth = (int) (104 * GameConstants.SCALE);
            public static final int PowerBarHeight = (int) (2 * GameConstants.SCALE);
            public static final int PowerBarXStart = (int) (44 * GameConstants.SCALE);
            public static final int PowerBarYStart = (int) (34 * GameConstants.SCALE);
        }

        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * GameConstants.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * GameConstants.SCALE);

        }

        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * GameConstants.SCALE);
        }

        public static class URMButtons {
            public static final int URM_SIZE_DEFAULT = 56;
            public static final int URM_SIZE = (int) (URM_SIZE_DEFAULT * GameConstants.SCALE);
        }

        public static class VolumeButtons {
            public static final int VOLUME_WIDTH_DEFAULT = 28;
            public static final int VOLUME_HEIGHT_DEFAULT = 44;
            public static final int SLIDER_WIDTH_DEFAULT = 215;

            public static final int VOLUME_WIDTH = (int) (VOLUME_WIDTH_DEFAULT * GameConstants.SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_HEIGHT_DEFAULT * GameConstants.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_WIDTH_DEFAULT * GameConstants.SCALE);

        }
    }

    // Directions constants for player movement
    public static class Directions {
        public static final int LEFT = 0;
        public static final int RIGHT = 2;
    }

    // Player animation constants
    public static class PlayerConstants {
        public static final int PLAYER_DAMAGE = 10;
        public static final int PLAYER_ATTACK_HIT_BOX_WIDTH = (int) (42 * GameConstants.SCALE);
        public static final int PLAYER_ATTACK_HIT_BOX_HEIGHT = (int) (30 * GameConstants.SCALE);
        public static final int PLAYER_MAX_HEALTH = 100;
        public static final int PLAYER_MAX_POWER = 100;
        public static final int MAX_ATTACKS_AMOUNT = 7;
        public static final int POWER_FOR_ATTACK = (PLAYER_MAX_POWER / MAX_ATTACKS_AMOUNT);
        public static final int POWER_DEFAULT_INCREASE = 4;
        public static final float PLAYER_WALK_SPEED = GameConstants.SCALE;

        public static final float xDrawOffset = 21 * GameConstants.SCALE;
        public static final float yDrawOffset = 3 * GameConstants.SCALE;
        public static final float jumpSpeed = -2.25f * GameConstants.SCALE;
        public static final float fallSpeedAfterCollision = 0.5f * GameConstants.SCALE;


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
                case HIT -> 2;
                case JUMP, ATTACK -> 3;
                default -> 1;
            };
        }
    }
}
