package de.marcluque.tictactoeai.map;

import de.marcluque.tictactoeai.utils.LoggerUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/*
 * Created with <3 by marcluque, March 2020
 */
public class Map {

    private static int mapRepresentation;

    private static final Set<Integer> MOVES = new HashSet<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8));

    private static int visitedStates = 0;

    private static int returnMove = -1;

    private static final int MAX = 1;

    private static final int MIN = 0;

    private static final Logger LOGGER = LoggerUtil.createLogger("de.marcluque.tictactoeai.map",
            "%2$s %n", Map.class);

    private Map() {}

    // Position is 0 to 8, player is 0 or 1
    public static int setTile(int map, int position, int player) {
        // Check tile is not in use
        if ((map & (1 << position + 9)) == (1 << position + 9) || (map & (1 << position)) == (1 << position)) {
            LOGGER.info(() -> String.format("Position %d is already set, please pick another position!", position + 1));
            return Integer.MIN_VALUE;
        } else {
            // Else write 1 to the target position
            map |= (1 << position + (player * 9));
        }

        return map;
    }

    public static int getMapRepresentation() {
        return mapRepresentation;
    }

    public static void setMapRepresentation(final int mapRepresentation) {
        Map.mapRepresentation = mapRepresentation;
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

    public static void setReturnMove(int returnMove) {
        Map.returnMove = returnMove;
    }

    public static int getMAX() {
        return MAX;
    }

    public static int getMIN() {
        return MIN;
    }
}