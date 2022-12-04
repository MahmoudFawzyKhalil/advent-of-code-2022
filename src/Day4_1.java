import java.nio.file.Files;
import java.nio.file.Path;

public class Day4_1 {
    public static void main(String[] args) throws Exception {
        var inputPath = Path.of("src/Day4.txt");
        var input = Files.readAllLines(inputPath);


        long fullyContainedPairs = input.stream()
                                        .map(Pair::ofInputString)
                                        .filter(Pair::isOneRangeFullyContainedInTheOther)
                                        .count();

        System.out.println("input.size() = " + input.size());
        System.out.println("fullyContainedPairs = " + fullyContainedPairs);


        long partialOverlapPairs = input.stream()
                                        .map(Pair::ofInputString)
                                        .filter(Pair::doesOneRangeOverlapAtAllWithTheOther)
                                        .count();

        System.out.println("partialOverlapPairs = " + partialOverlapPairs);

        // 6 - 6    a >= x && b <= y
        // 4 - 6

        // 5 - 6
        // 4 - 6

        // 4 - 5
        // 5 - 6

        // 5 - 6
        // 5 - 6

        // 2 - 3
        // 6 - 7

        // 10 - 14        (a >= x || b <= y) && !(b < x) && !(a > y)
        // 4 - 9
    }

    record Pair(int a, int b, int x, int y) {


        public boolean isOneRangeFullyContainedInTheOther() {
            return (a >= x && b <= y) || (x >= a && y <= b);
        }

        public boolean doesOneRangeOverlapAtAllWithTheOther() {
            return (a >= x || b <= y) && !(b < x) && !(a > y)
                    || (x >= a || y <= b) && !(y < a) && !(x > b);
        }

        public static Pair ofInputString(String input) {
            var pairs = input.split(",");
            var first = pairs[0].split("-");
            var second = pairs[1].split("-");

            return new Pair(
                    Integer.parseInt(first[0]),
                    Integer.parseInt(first[1]),
                    Integer.parseInt(second[0]),
                    Integer.parseInt(second[1])
            );
        }
    }
}