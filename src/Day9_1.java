import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Day9_1 {
    private static Pair head = new Pair();

    private static Pair tail = new Pair();

    private static Pair nextTailMovement = new Pair();

    private static Set<Location> positionsVisitedAtLeastOnce = new HashSet<>();

    public static void main(String[] args) throws Exception {
        var inputPath = Path.of("src/Day9.txt");
        var input = Files.readAllLines(inputPath);

        // 3 [  ] [  ] [  ] [  ]
        // 2 [  ] [  ] [  ] [  ]
        // 1 [  ] [  ] [H ] [  ]
        // 0 [  ] [T ] [  ] [  ]
        //    0    1    2    3

        positionsVisitedAtLeastOnce.add(new Location(tail.x, tail.y));

        for (String movement : input) {
            int steps = getSteps(movement);

            if (movement.startsWith("R")) {
                for (int i = 0; i < steps; i++) {
                    head.x++;
                    calculateAndApplyNextTailMovement();
                }
            } else if (movement.startsWith("U")) {
                for (int i = 0; i < steps; i++) {
                    head.y++;
                    calculateAndApplyNextTailMovement();
                }
            } else if (movement.startsWith("L")) {
                for (int i = 0; i < steps; i++) {
                    head.x--;
                    calculateAndApplyNextTailMovement();
                }
            } else if (movement.startsWith("D")) {
                for (int i = 0; i < steps; i++) {
                    head.y--;
                    calculateAndApplyNextTailMovement();
                }
            }
        }

        int numberOfPositionsVisitedAtLeastOnce = positionsVisitedAtLeastOnce.size();
        System.out.println("numberOfPositionsVisitedAtLeastOnce = " + numberOfPositionsVisitedAtLeastOnce);
    }

    private static void calculateAndApplyNextTailMovement() {
        int xDifference = head.x - tail.x;
        nextTailMovement.x = Math.abs(xDifference) - 1;

        if (nextTailMovement.x == -1) {
            nextTailMovement.x = 0;
        }

        int yDifference = head.y - tail.y;
        nextTailMovement.y = Math.abs(yDifference) - 1;

        if (nextTailMovement.y == -1) {
            nextTailMovement.y = 0;
        }

        if (nextTailMovement.y > 0 && head.x != tail.x) {
            tail.x = head.x;
        }

        if (nextTailMovement.x > 0 && head.y != tail.y) {
            tail.y = head.y;
        }

        tail.x += (nextTailMovement.x * Integer.signum(xDifference));
        tail.y += (nextTailMovement.y * Integer.signum(yDifference));

        positionsVisitedAtLeastOnce.add(new Location(tail.x, tail.y));
    }

    private static int getSteps(String movement) {
        return Integer.parseInt(movement.split(" ")[1]);
    }

    record Location(int x, int y) {
    }

    static class Pair {
        public int x = 0;

        public int y = 0;
    }
}