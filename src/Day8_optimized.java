import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day8_optimized {
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

        // i = 1, j = 1
        // [  v           v
        //   [0, 0, 3, 7, 3],
        // > [2, 5, 5, 1, 2],
        //   [6, 5, 3, 3, 2] <
        // ]

        int[] maxTops = new int[map.length];
        Arrays.fill(maxTops, -1);

        int[] maxBottoms = new int[map.length];
        Arrays.fill(maxBottoms, -1);

        int maxLeft = -1;
        int maxRight = -1;

        boolean[][] alreadyCounted = new boolean[map.length][map[0].length];

        int[][] scenicScores = new int[map.length][map[0].length];

        for (int[] scenicScore : scenicScores) {
            Arrays.fill(scenicScore, 1);
        }

        int curViewableLeft = 0;
        int curViewableRight = 0;
        int[] curViewableTop = new int[map.length];
        int[] curViewableBottom = new int[map.length];

        for (int i1 = 0, i2 = map.length - 1; i1 < map.length; i1++, i2--) {
            for (int j1 = 0, j2 = map[i1].length - 1; j1 < map[i1].length; j1++, j2--) {


                int curFromTheTopLeft = map[i1][j1];
                if (curFromTheTopLeft > maxLeft || curFromTheTopLeft > maxTops[j1]) {
                    maxLeft = Math.max(maxLeft, curFromTheTopLeft);
                    maxTops[j1] = Math.max(maxTops[j1], curFromTheTopLeft);
                    if (!alreadyCounted[i1][j1]) {
                        alreadyCounted[i1][j1] = true;
                        numberOfVisibleTrees++;
                    }
                }

                if (j1 > 0) {
                    if (curFromTheTopLeft > map[i1][j1 - 1]) {
                        curViewableLeft += 1;
                    } else {
                        curViewableLeft = 1;
                    }

                    scenicScores[i1][j1] *= curViewableLeft;
                }
                if (i1 > 0) {
                    if (curFromTheTopLeft > map[i1 - 1][j1]) {
                        curViewableTop[j1] += 1;
                    } else {
                        curViewableTop[j1] = 1;
                    }

                    scenicScores[i1][j1] *= curViewableTop[i1];
                }

                int curFromTheBottomRight = map[i2][j2];
                if (curFromTheBottomRight > maxRight || curFromTheBottomRight > maxBottoms[j2]) {
                    maxRight = Math.max(maxRight, curFromTheBottomRight);
                    maxBottoms[j2] = Math.max(maxBottoms[j2], curFromTheBottomRight);
                    if (!alreadyCounted[i2][j2]) {
                        alreadyCounted[i2][j2] = true;
                        numberOfVisibleTrees++;
                    }
                }

                if (j2 < map[i2].length - 1) {
                    if (curFromTheBottomRight > map[i2][j2 + 1]) {
                        curViewableRight += 1;
                    } else {
                        curViewableRight = 1;
                    }

                    scenicScores[i2][j2] *= curViewableRight;
                }


                if (i2 < map.length - 1) {
                    if (curFromTheBottomRight > map[i2 + 1][j2]) {
                        curViewableBottom[j2] += 1;
                    } else {
                        curViewableBottom[j2] = 1;
                    }

                    scenicScores[i2][j2] *= curViewableBottom[i2];
                }

            }

            maxLeft = -1;
            maxRight = -1;
            curViewableLeft = 0;
            curViewableRight = 0;
        }

        System.out.println();
        var maxScenicScore = Arrays.stream(scenicScores)
                                   .peek(arr -> System.out.println(Arrays.toString(arr)))
                                   .flatMapToInt(Arrays::stream)
                                   .max()
                                   .getAsInt();

        System.out.println("numberOfVisibleTrees = " + numberOfVisibleTrees);
        System.out.println("maxScenicScore = " + maxScenicScore);
    }
}