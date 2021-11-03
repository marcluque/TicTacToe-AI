package de.marcluque.tictactoeai.map;

/*
 * Created with <3 by marcluque, November 2021
 */
public class Utility {

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

    public static int compute(int map) {
        int maxMap = (map & 0b111111111000000000) >> 9;
        int minMap = map & 0b111111111;

        for (int j : TERMINAL_TEST) {
            // MAX
            if ((maxMap & j) == j) {
                return 1;
            }
            // MIN
            else if ((minMap & j) == j) {
                return -1;
            }
        }

        boolean mapFull = ((map & 0b111111111) | (map & 0b111111111000000000) >> 9) == 0b111111111;
        return mapFull ? 0 : 100;
    }

}