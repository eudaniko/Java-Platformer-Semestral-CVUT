// Daniil Nikonenko
// 22.03.2024
// PJV Semestral: GamePanel

package utils;

// Constants class containing game-related constant values
public class Constants {

    // Directions constants for player movement
    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    // Player animation constants
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK_1 = 6;
        public static final int ATTACK_JUMP_1 = 7;

        // Method to get the number of sprite frames based on player action type
        public static int getSpriteAmount(int playerAction) {
            switch (playerAction) {
                case RUNNING:
                    return 6;
                case IDLE:
                    return 5;
                case HIT:
                    return 4;
                case JUMP:
                case ATTACK_1:
                case ATTACK_JUMP_1:
                    return 3;
                case GROUND:
                    return 2;
                case FALLING:
                default:
                    return 1;
            }
        }
    }
}
