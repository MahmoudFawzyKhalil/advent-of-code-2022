import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Day1 {
    public static void main(String[] args) throws Exception {
        var inputPath = Path.of("src/Day1.txt");
        var input = Files.readAllLines(inputPath);

        var sortedCalories = new PriorityQueue<Integer>(Comparator.reverseOrder());

        int currentElfTotal = 0;
        for (var line : input) {
            if (line.isEmpty()) {
                sortedCalories.offer(currentElfTotal);
                currentElfTotal = 0;
            } else {
                currentElfTotal += Integer.parseInt(line);
            }
        }


        Integer topThree2 = sortedCalories.stream()
                                          .sorted(Comparator.reverseOrder())
                                          .limit(3)
                                          .reduce(0, Integer::sum);

        int topThree1 = 0;
        for (int i = 0; i < 3; i++) {
            topThree1 += sortedCalories.poll();
        }

        System.out.println(sortedCalories);
        System.out.println(topThree1);
        System.out.println(topThree2);
    }
}