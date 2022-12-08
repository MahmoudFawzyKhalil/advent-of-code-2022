import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day8_2 {
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
        // [     j
        // i [3, 0, 3, 7, 3], [1, 1, 2, 3, 1]
        //   [2, 5, 5, 1, 2], [0, 1, 1, 1, 2]
        //   [6, 5, 3, 3, 2]
        // ]

//        int[][] map = Arrays.stream(exampleInput.split("\n"))
//                            .map(s -> s.split(""))
//                            .map(arr -> Arrays.stream(arr)
//                                              .mapToInt(Integer::parseInt)
//                                              .toArray())
//                            .toArray(int[][]::new);

        int[][] map = input.stream()
                            .map(s -> s.split(""))
                            .map(arr -> Arrays.stream(arr)
                                              .mapToInt(Integer::parseInt)
                                              .toArray())
                            .toArray(int[][]::new);

        Arrays.stream(map)
              .map(Arrays::toString)
              .forEach(System.out::println);

        int maxScenicScore = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int currentScenicScore = 1;
                currentScenicScore *= visibleLeft(map, i, j);
                currentScenicScore *= visibleRight(map, i, j);
                currentScenicScore *= visibleTop(map, i, j);
                currentScenicScore *= visibleBottom(map, i, j);

                maxScenicScore = Math.max(maxScenicScore, currentScenicScore);
            }
        }

        System.out.println("maxScenicScore = " + maxScenicScore);

    }

    private static int visibleTop(int[][] map, int i, int j) {
        int visibleTrees = 0;

        for (int k = i - 1; k >= 0; k--) {
            visibleTrees++;
            if (map[k][j] >= map[i][j]) {
                break;
            }
        }

        return visibleTrees;
    }

    private static int visibleBottom(int[][] map, int i, int j) {
        int visibleTrees = 0;

        for (int k = i + 1; k < map.length; k++) {
            visibleTrees++;

            if (map[k][j] >= map[i][j]) {
                break;
            }
        }

        return visibleTrees;
    }

    private static int visibleRight(int[][] map, int i, int j) {
        int visibleTrees = 0;

        for (int k = j + 1; k < map[i].length; k++) {
            visibleTrees++;

            if (map[i][k] >= map[i][j]) {
                break;
            }
        }

        return visibleTrees;
    }

    private static int visibleLeft(int[][] map, int i, int j) {

        int visibleTrees = 0;

        for (int k = j - 1; k >= 0; k--) {
            visibleTrees++;

            if (map[i][k] >= map[i][j]) {
                break;
            }
        }

        return visibleTrees;
    }

}