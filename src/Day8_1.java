import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day8_1 {
    public static void main(String[] args) throws Exception {
        var inputPath = Path.of("src/Day8.txt");
        var input = Files.readAllLines(inputPath);

        var exampleInput = """
                30373
                25512
                65332
                33549
                35390
                """;

        // i = 1, j = 1
        // [  v           v
        //   [0, 0, 3, 7, 3],
        // > [2, 5, 5, 1, 2],
        //   [6, 5, 3, 3, 2] <
        // ]

        int[][] map = Arrays.stream(exampleInput.split("\n"))
                            .map(s -> s.split(""))
                            .map(arr -> Arrays.stream(arr)
                                              .mapToInt(Integer::parseInt)
                                              .toArray())
                            .toArray(int[][]::new);

//        int[][] map = input.stream()
//                            .map(s -> s.split(""))
//                            .map(arr -> Arrays.stream(arr)
//                                              .mapToInt(Integer::parseInt)
//                                              .toArray())
//                            .toArray(int[][]::new);

        Arrays.stream(map)
              .map(Arrays::toString)
              .forEach(System.out::println);

        int numberOfVisibleTrees = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

                if (visibleLeft(map, i, j)) {
                    numberOfVisibleTrees++;
                    continue;
                }

                if (visibleRight(map, i, j)) {
                    numberOfVisibleTrees++;
                    continue;
                }

                if (visibleTop(map, i, j)) {
                    numberOfVisibleTrees++;
                    continue;
                }

                if (visibleBottom(map, i, j)) {
                    numberOfVisibleTrees++;
                    continue;
                }

            }
        }

        System.out.println("numberOfVisibleTrees = " + numberOfVisibleTrees);
    }

    private static boolean visibleTop(int[][] map, int i, int j) {
        boolean visibleTop = true;

        for (int k = 0; k < i; k++) {
            if (map[k][j] >= map[i][j]) {
                visibleTop = false;
                break;
            }
        }

        return visibleTop;
    }

    private static boolean visibleBottom(int[][] map, int i, int j) {
        boolean visibleBottom = true;

        for (int k = i + 1; k < map.length; k++) {
            if (map[k][j] >= map[i][j]) {
                visibleBottom = false;
                break;
            }
        }

        return visibleBottom;
    }

    private static boolean visibleRight(int[][] map, int i, int j) {
        boolean visibleRight = true;

        for (int k = j + 1; k < map[i].length; k++) {
            if (map[i][k] >= map[i][j]) {
                visibleRight = false;
                break;
            }
        }

        return visibleRight;
    }

    private static boolean visibleLeft(int[][] map, int i, int j) {
        boolean visibleLeft = true;

        for (int k = 0; k < j; k++) {
            if (map[i][k] >= map[i][j]) {
                visibleLeft = false;
                break;
            }
        }

        return visibleLeft;
    }

}