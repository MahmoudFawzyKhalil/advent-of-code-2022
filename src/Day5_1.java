import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.List;
import java.util.stream.Collectors;

public class Day5_1 {
    static List<StackOfCrates<Character>> stacks = List.of(
            StackOfCrates.ofCharacters("TDWZVP"),
            StackOfCrates.ofCharacters("LSWVFJD"),
            StackOfCrates.ofCharacters("ZMLSVTBH"),
            StackOfCrates.ofCharacters("RSJ"),
            StackOfCrates.ofCharacters("CZBGFMLW"),
            StackOfCrates.ofCharacters("QWVHZRGB"),
            StackOfCrates.ofCharacters("VJPCBDN"),
            StackOfCrates.ofCharacters("PTBQ"),
            StackOfCrates.ofCharacters("HGZRC")
    );

    public static void main(String[] args) throws Exception {
        var inputPath = Path.of("src/Day5.txt");
        var input = Files.readAllLines(inputPath);


        input.stream()
             .skip(10)
             .map(Instruction::ofInputString)
             .forEach(Instruction::execute);

        String topCrates = stacks.stream()
                                 .map(StackOfCrates::pop)
                                 .map(String::valueOf)
                                 .collect(Collectors.joining());

        System.out.println("topCrates = " + topCrates);
    }

    record Instruction(int n, int from, int to) {
        public static Instruction ofInputString(String input) {
            var tokens = input.split(" ");
            return new Instruction(
                    Integer.parseInt(tokens[1]),
                    Integer.parseInt(tokens[3]) - 1,
                    Integer.parseInt(tokens[5]) - 1
            );
        }

        void execute() {
            stacks.get(from).move(n, stacks.get(to));
        }
    }

    static class StackOfCrates<T> {
        private final ArrayDeque<T> delegateStack = new ArrayDeque<>();

        static StackOfCrates<Character> ofCharacters(String characters) {
            StackOfCrates<Character> stack = new StackOfCrates<>();

            for (int i = 0; i < characters.length(); i++) {
                stack.push(characters.charAt(i));
            }

            return stack;
        }

        public void push(T element) {
            delegateStack.push(element);
        }

        public T pop() {
            return delegateStack.pop();
        }

        public void movePart1(int n, StackOfCrates<? super T> to) {
            for (int i = 0; i < n; i++) {
                to.push(this.pop());
            }
        }

        public void move(int n, StackOfCrates<? super T> to) {
            ArrayDeque<T> reverser = new ArrayDeque<>();
            for (int i = 0; i < n; i++) {
                reverser.push(this.pop());
            }

            for (int i = 0; i < n; i++) {
                to.push(reverser.pop());
            }
        }
    }
}