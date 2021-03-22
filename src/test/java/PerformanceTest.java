/*
 * Created with <3 by marcluque, March 2021
 */
public class PerformanceTest {

    public static void main(String[] args) {
        // Warm-up
        for (int i = 0; i < 100; i++) {
            MiniMaxSearch.search();
        }

        // Minimax
        long best = Integer.MAX_VALUE;
        for (int i = 0; i < 10000; i++) {
            Map.visitedStates = 0;
            long start = System.nanoTime();
            MiniMaxSearch.search();
            long end = System.nanoTime();
            if ((end - start) < best) {
                best = (end - start);
            }
        }

        System.out.println("=========================== Stats MiniMax ==========================");
        System.out.printf("Found move: %d%n", (Map.returnMove + 1));
        System.out.printf("Time: %d ns = %s µs = %s ms%n", best, best / 1_000d, best / 1_000_000d);
        System.out.printf("Visited states: %d%n", Map.visitedStates);
        System.out.printf("Time/State: %d ns = %s µs = %s ms%n", best / Map.visitedStates, (best / 1_000d) / Map.visitedStates, (best / 1_000_000d) / Map.visitedStates);
        System.out.println("====================================================================");
        System.out.println();

        // Alpha-Beta
        best = Integer.MAX_VALUE;
        for (int i = 0; i < 10000; i++) {
            Map.visitedStates = 0;
            long start = System.nanoTime();
            MiniMaxSearch.search();
            long end = System.nanoTime();
            if ((end - start) < best) {
                best = (end - start);
            }
        }

        System.out.println("=========================== Stats MiniMax ==========================");
        System.out.printf("Found move: %d%n", (Map.returnMove + 1));
        System.out.printf("Time: %d ns = %s µs = %s ms%n", best, best / 1_000d, best / 1_000_000d);
        System.out.printf("Visited states: %d%n", Map.visitedStates);
        System.out.printf("Time/State: %d ns = %s µs = %s ms%n", best / Map.visitedStates, (best / 1_000d) / Map.visitedStates, (best / 1_000_000d) / Map.visitedStates);
        System.out.println("====================================================================");
        System.out.println();
    }
}
