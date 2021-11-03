package de.marcluque.tictactoeai.utils;

import de.marcluque.tictactoeai.map.Map;

import java.util.logging.Logger;

/*
 * Created with <3 by marcluque, November 2021
 */
public class MapUtil {

    private static final Logger LOGGER = LoggerUtil.createLogger("de.marcluque.tictactoeai.utils",
            "%2$s %n", MapUtil.class);

    private MapUtil() {}

    public static void printBoard(int map) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            String printSign = "[ ]";

            // Player 0
            int targetBit = 9 + i;
            if ((map & (1 << targetBit)) == (1 << targetBit)) {
                printSign = "[X]";
            }

            // Player 1
            targetBit = i;
            if ((map & (1 << targetBit)) == (1 << targetBit)) {
                printSign = "[O]";
            }

            out.append(printSign);

            if ((i + 1) % 3 == 0 && i != 8) {
                out.append("\n");
            }
        }

        LOGGER.info("Current board:");
        LOGGER.info(out::toString);
    }

    public static void printStats(String type, long endTime) {
        String out = String.format("\n" + "========================== Stats %s =========================", type) + "\n" +
                String.format("Found move: %d", (Map.getReturnMove() + 1)) + "\n" +
                String.format("Time: %d ns = %s µs = %s ms", endTime, endTime / 1_000d, endTime / 1_000_000d) + "\n" +
                String.format("Visited states: %d", Map.getVisitedStates()) + "\n" +
                String.format("Time/State: %d ns = %s µs = %s ms", endTime / Map.getVisitedStates(),
                        (endTime / 1_000d) / Map.getVisitedStates(), (endTime / 1_000_000d) / Map.getVisitedStates()) + "\n" +
                "=====================================================================" + "\n";

        LOGGER.info(out);
    }
}