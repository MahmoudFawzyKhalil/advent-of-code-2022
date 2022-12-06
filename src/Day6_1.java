import java.nio.file.Files;
import java.nio.file.Path;

public class Day6_1 {
    public static void main(String[] args) throws Exception {
        var inputPath = Path.of("src/Day6.txt");
        var input = Files.readAllLines(inputPath).get(0);

        //     v
        //       v
        // .....mfqfklm

        // Scala

        var sb = new StringBuilder();
        // O(n)
        iteration(input, sb);
        System.out.println(recursive(0, input, new StringBuilder()));
    }

    private static void iteration(String input, StringBuilder sb) {
        for (int i = 0; i < input.toCharArray().length; i++) {
            char c = input.charAt(i); // O(1)

            int indexInSb = sb.indexOf(String.valueOf(c)); // O(m)

            boolean characterIsUnique = indexInSb == -1;

            if (characterIsUnique) {
                sb.append(c); // O(1)
            } else {
                sb.replace(0, indexInSb + 1, ""); // O(m)
                sb.append(c); // O(1)
            }

            if (sb.length() == 14) {
                System.out.println("sb = " + sb);
                System.out.println("i = " + i);
                System.out.println("answer = " + (i + 1));
                return;
            }
        }
    }


    private static int recursive(int i, String input, StringBuilder sb) {
        if (sb.length() == 14) {
            return i;
        }

        char c = input.charAt(i); // O(1)

        int indexInSb = sb.indexOf(String.valueOf(c)); // O(m)

        boolean characterIsUnique = indexInSb == -1;

        if (characterIsUnique) {
            sb.append(c); // O(1)
        } else {
            sb.replace(0, indexInSb + 1, ""); // O(m)
            sb.append(c); // O(1)
        }

        return recursive(i + 1, input, sb);
    }
}