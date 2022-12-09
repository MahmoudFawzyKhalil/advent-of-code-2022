import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Day9_2 {
    private static Set<Location> positionsVisitedAtLeastOnceByEndOfRope = new HashSet<>();

    public static void main(String[] args) throws Exception {
        var inputPath = Path.of("src/Day9.txt");
        var input = Files.readAllLines(inputPath);


        // 3 [  ] [  ] [  ] [  ]
        // 2 [  ] [  ] [  ] [H ]
        // 1 [  ] [  ] [  ] [  ]
        // 0 [  ] [T ] [  ] [  ]
        //    0    -1   -2    3

        Knot lastTail = new Knot(0, 0);
        Knot cur = lastTail;
        Knot next;
        for (int i = 0; i < 9; i++) {
            next = new Knot(0, 0);
            cur.setHead(next);
            next.setTail(cur);
            cur = next;
        }

        Knot firstHead = cur;

        String example = """
                R 4
                U 4
                L 3
                D 1
                R 4
                D 1
                L 5
                R 2
                """;

        String example2 = """
                R 5
                U 8
                L 8
                D 3
                R 17
                D 10
                L 25
                U 20
                """;

        String[] exampleArr = example.split("\n");

        for (String movement : input) {
            int steps = getSteps(movement);
            if (movement.startsWith("R")) {
                for (int i = 0; i < steps; i++) {
                    firstHead.moveRight();
                    positionsVisitedAtLeastOnceByEndOfRope.add(new Location(lastTail.getX(), lastTail.getY()));
                    lastTail.print();
                }
            } else if (movement.startsWith("U")) {
                for (int i = 0; i < steps; i++) {
                    firstHead.moveUp();
                    positionsVisitedAtLeastOnceByEndOfRope.add(new Location(lastTail.getX(), lastTail.getY()));
                    lastTail.print();
                }
            } else if (movement.startsWith("L")) {
                for (int i = 0; i < steps; i++) {
                    firstHead.moveLeft();
                    positionsVisitedAtLeastOnceByEndOfRope.add(new Location(lastTail.getX(), lastTail.getY()));
                    lastTail.print();
                }
            } else if (movement.startsWith("D")) {
                for (int i = 0; i < steps; i++) {
                    firstHead.moveDown();
                    positionsVisitedAtLeastOnceByEndOfRope.add(new Location(lastTail.getX(), lastTail.getY()));
                    lastTail.print();
                }
            }
        }

        int numberOfPositionsVisitedAtLeastOnce = positionsVisitedAtLeastOnceByEndOfRope.size();
        System.out.println("numberOfPositionsVisitedAtLeastOnce = " + numberOfPositionsVisitedAtLeastOnce);
    }

    private static int getSteps(String movement) {
        return Integer.parseInt(movement.split(" ")[1]);
    }

    record Location(int x, int y) {
    }

    static class Knot {
        private int x;

        private int y;

        private boolean iAmTheChosenOne;

        private Knot head;

        private Knot tail;

        public Knot(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Knot(int x, int y, Knot head) {
            this.x = x;
            this.y = y;
            this.head = head;
        }

        public void moveRight(int steps) {
            for (int i = 0; i < steps; i++) {
                x++;
                if (tail != null) {
                    tail.followHead();
                }
            }
        }

        public void moveRight() {
            moveRight(1);
        }

        public void moveUp(int steps) {
            for (int i = 0; i < steps; i++) {
                y++;
                if (tail != null) {
                    tail.followHead();
                }
            }
        }

        public void moveUp() {
            moveUp(1);
        }

        public void moveLeft(int steps) {
            for (int i = 0; i < steps; i++) {
                x--;
                if (tail != null) {
                    tail.followHead();
                }
            }
        }

        public void moveLeft() {
            moveLeft(1);
        }

        public void moveDown(int steps) {
            for (int i = 0; i < steps; i++) {
                y--;
                if (tail != null) {
                    tail.followHead();
                }
            }
        }

        public void moveDown() {
            moveDown(1);
        }

        public void followHead() { // -3 -2 -1 0 1 2 3
            int xDifference = head.getX() - this.x;
            int nextXMovement = Math.abs(xDifference) - 1;

            if (nextXMovement == -1) {
                nextXMovement = 0;
            }

            int yDifference = head.getY() - this.y;
            int nextYMovement = Math.abs(yDifference) - 1;

            if (nextYMovement == -1) {
                nextYMovement = 0;
            }

            // TAKE INTO ACCOUNT THE DOUBLE DIAGONAL MOVE
            if (nextXMovement > 0 && nextYMovement > 0) {
                moveDiagonal(nextXMovement, Integer.signum(xDifference), nextYMovement, Integer.signum(yDifference));
                return;
            }
            // TAKE INTO ACCOUNT THE DOUBLE DIAGONAL MOVE

            if (nextYMovement > 0 && head.getX() != this.x) {
                this.x = head.getX();
            }

            if (nextXMovement > 0 && head.getY() != this.y) {
                this.y = head.getY();
            }




            if (Integer.signum(xDifference) == -1) {
                moveLeft(nextXMovement);
            } else {
                moveRight(nextXMovement);
            }

            if (Integer.signum(yDifference) == -1) {
                moveDown(nextYMovement);
            } else {
                moveUp(nextYMovement);
            }
        }

        private void moveDiagonal(int nextXMovement, int xDirection, int nextYMovement, int yDirection) {
            x += nextXMovement * xDirection;
            y += nextYMovement * yDirection;
            if (tail != null) {
                tail.followHead();
            }
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setHead(Knot head) {
            this.head = head;
        }

        public Knot getHead() {
            return head;
        }

        public void setTail(Knot tail) {
            this.tail = tail;
        }

        public Knot getTail() {
            return tail;
        }

        public void print() {
            System.out.println(x + ", " + y);
        }
    }

    static class Pair {
        public int x = 0;

        public int y = 0;
    }
}