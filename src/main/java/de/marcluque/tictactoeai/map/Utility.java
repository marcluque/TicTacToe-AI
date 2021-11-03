package de.marcluque.tictactoeai.map;

/*
 * Created with <3 by marcluque, November 2021
 */
public final class Utility {

    public static final int MID_GAME_UTILITY = 100;

    public static final int COMPUTER_WON = 1;

    public static final int PLAYER_WON = -1;

    public static final int DRAW = 0;

    private static final int[] TERMINAL_TEST = new int[8];

    static {
        // ROW 1
        TERMINAL_TEST[0] = 0b001001001;
        // ROW 2
        TERMINAL_TEST[1] = TERMINAL_TEST[0] << 1;
        // ROW 3
        TERMINAL_TEST[2] = TERMINAL_TEST[0] << 2;

        // COL 1
        TERMINAL_TEST[3] = 0b000000111;
        // COL 2
        TERMINAL_TEST[4] = TERMINAL_TEST[3] << 3;
        // COL 3
        TERMINAL_TEST[5] = TERMINAL_TEST[3] << 6;

        // DIAG 1
        TERMINAL_TEST[6] = 0b100010001;
        // DIAG 2
        TERMINAL_TEST[7] = 0b001010100;
    }

    private Utility() {}

    public static int compute(final int intMap) {
        final int maxMap = (intMap & 0b111111111000000000) >> 9;
        final int minMap = intMap & 0b111111111;
        final boolean mapFull = ((intMap & 0b111111111) | (intMap & 0b111111111000000000) >> 9)
                == 0b111111111;
        int result = mapFull ? DRAW : MID_GAME_UTILITY;

        for (int j : TERMINAL_TEST) {
            // MAX
            if ((maxMap & j) == j) {
                result = COMPUTER_WON;
            }
            // MIN
            else if ((minMap & j) == j) {
                result = PLAYER_WON;
            }
        }

        return result;
    }

}