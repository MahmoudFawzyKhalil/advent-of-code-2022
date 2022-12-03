import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.OptionalInt;

public class Day3_2 {
    public static void main(String[] args) throws Exception {
        var inputPath = Path.of("src/Day3.txt");
        var input = Files.readAllLines(inputPath);


        int totalPriority = 0;
        Group currentGroup;
        for (int i = 0; i < input.size(); i += 3) {
            currentGroup = new Group(
                    new Rucksack(input.get(i)),
                    new Rucksack(input.get(i + 1)),
                    new Rucksack(input.get(i + 2))
            );

            totalPriority += currentGroup.findPriorityOfCommonItem();
        }

        System.out.println(totalPriority);
    }

    record Rucksack(String contents) {

        public int[] toFrequencyArray() {
            int[] frequencyArray = new int[52];

            for (var c : contents.toCharArray()) {
                frequencyArray[letterToIndex(c)] += 1;
            }

            return frequencyArray;
        }

        public static int letterToIndex(int letter) {
            if (isLowerCase(letter)) {
                return letter - 'a';
            } else {
                return letter - 'A' + 26;
            }
        }

        private static boolean isLowerCase(int letter) {
            return letter >= 'a' && letter <= 'z';
        }

    }

    record Group(Rucksack first, Rucksack second, Rucksack third) {

        public int findPriorityOfCommonItem() {
            int[] firstFa = first.toFrequencyArray();
            int[] thirdFa = third.toFrequencyArray();

            int[] firstCommonWithSecondIndices = second.contents()
                                                       .chars()
                                                       .map(Rucksack::letterToIndex)
                                                       .filter(i -> firstFa[i] > 0)
                                                       .toArray();

            OptionalInt commonItemIndex = Arrays.stream(firstCommonWithSecondIndices)
                                                .filter(i -> thirdFa[i] > 0)
                                                .findAny();

            return commonItemIndex.getAsInt() + 1;
        }
    }
}