package com.marcluque.tictactoeai.map;

import com.marcluque.tictactoeai.utils.LoggerUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/*
 * Created with <3 by marcluque, March 2020
 */
public final class Map {

    private static int intMap;

    private static final Set<Integer> MOVES = new HashSet<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8));

    private static int visitedStates;

    private static int returnMove = -1;

    private static final int MAX = 1;

    private static final int MIN = 0;

    private static final Logger LOGGER = LoggerUtil.createLogger("com.marcluque.tictactoeai.map",
            "%2$s %n", Map.class);

    private Map() {}

    /**
     * Position is 0 to 8, player is 0 or 1
     */
    public static int setTile(int intMap, int position, int player) {
        // Check tile is not in use
        if ((intMap & (1 << position + 9)) == (1 << position + 9) || (intMap & (1 << position)) == (1 << position)) {
            LOGGER.info(() -> String.format("Position %d is already set, please pick another position!", position + 1));
            return Integer.MIN_VALUE;
        } else {
            // Else write 1 to the target position
            intMap |= (1 << position + (player * 9));
        }

        return intMap;
    }

    public static int getMapRepresentation() {
        return intMap;
    }

    public static void setMapRepresentation(final int intMap) {
        Map.intMap = intMap;
    }

    public static Set<Integer> getMoves() {
        return MOVES;
    }

    public static int getVisitedStates() {
        return visitedStates;
    }

    public static void setVisitedStates(int visitedStates) {
        Map.visitedStates = visitedStates;
    }

    public static int getReturnMove() {
        return returnMove;
    }

    public static void setReturnMove(final int returnMove) {
        Map.returnMove = returnMove;
    }

    public static int getMAX() {
        return MAX;
    }

    public static int getMIN() {
        return MIN;
    }
}