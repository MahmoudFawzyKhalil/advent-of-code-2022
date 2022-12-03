import java.nio.file.Files;
import java.nio.file.Path;

public class Day3_1 {
    public static void main(String[] args) throws Exception {
        var inputPath = Path.of("src/Day3.txt");
        var input = Files.readAllLines(inputPath);

        int misplacedItemsPriorities = input.stream()
                       .map(Rucksack::ofInputString)
                       .mapToInt(Rucksack::findCommonItemPriority)
                       .sum();

        System.out.println(misplacedItemsPriorities);
    }

    record Rucksack(Compartment first, Compartment second) {

        public static Rucksack ofInputString(String input) {
            int mid = input.length() / 2;
            var firstHalf = input.substring(0, mid);
            var secondHalf = input.substring(mid);

            return new Rucksack(
                    new Compartment(firstHalf), new Compartment(secondHalf)
            );
        }

        public int findCommonItemPriority() {
            int commonItemIndex = first.findCommonItemIndexWith(second);
            return commonItemIndex + 1;
        }
    }

    record Compartment(String contents) {

        public int findCommonItemIndexWith(Compartment other) {
            int[] selfFa = this.toFrequencyArray();

            var commonItemIndex = other.contents()
                                  .chars()
                                  .map(Compartment::letterToIndex)
                                  .filter(i -> selfFa[i] > 0)
                                  .findAny();

            return commonItemIndex.getAsInt();
        }

        public int[] toFrequencyArray() {
            int[] frequencyArray = new int[52];

            for (var c : contents.toCharArray()) {
                frequencyArray[letterToIndex(c)] += 1;
            }

            return frequencyArray;
        }

        private static int letterToIndex(int letter) {
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
}