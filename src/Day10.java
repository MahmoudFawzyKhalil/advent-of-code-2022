import java.io.Console;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;

public class Day10 {

    public static void main(String[] args) throws Exception {
        var inputPath = Path.of("src/Day10.txt");
        var input = Files.readAllLines(inputPath);

        var example = """
                addx 15
                addx -11
                addx 6
                addx -3
                addx 5
                addx -1
                addx -8
                addx 13
                addx 4
                noop
                addx -1
                addx 5
                addx -1
                addx 5
                addx -1
                addx 5
                addx -1
                addx 5
                addx -1
                addx -35
                addx 1
                addx 24
                addx -19
                addx 1
                addx 16
                addx -11
                noop
                noop
                addx 21
                addx -15
                noop
                noop
                addx -3
                addx 9
                addx 1
                addx -3
                addx 8
                addx 1
                addx 5
                noop
                noop
                noop
                noop
                noop
                addx -36
                noop
                addx 1
                addx 7
                noop
                noop
                noop
                addx 2
                addx 6
                noop
                noop
                noop
                noop
                noop
                addx 1
                noop
                noop
                addx 7
                addx 1
                noop
                addx -13
                addx 13
                addx 7
                noop
                addx 1
                addx -33
                noop
                noop
                noop
                addx 2
                noop
                noop
                noop
                addx 8
                noop
                addx -1
                addx 2
                addx 1
                noop
                addx 17
                addx -9
                addx 1
                addx 1
                addx -3
                addx 11
                noop
                noop
                addx 1
                noop
                addx 1
                noop
                noop
                addx -13
                addx -19
                addx 1
                addx 3
                addx 26
                addx -30
                addx 12
                addx -1
                addx 3
                addx 1
                noop
                noop
                noop
                addx -9
                addx 18
                addx 1
                addx 2
                noop
                noop
                addx 9
                noop
                noop
                noop
                addx -1
                addx 2
                addx -37
                addx 1
                addx 3
                noop
                addx 15
                addx -21
                addx 22
                addx -6
                addx 1
                noop
                addx 2
                addx 1
                noop
                addx -10
                noop
                noop
                addx 20
                addx 1
                addx 2
                addx 2
                addx -6
                addx -11
                noop
                noop
                noop
                """;

        var exampleInput = example.split("\n");

        CPU cpu = new CPU();
        CRT crt = new CRT(cpu);

        Set<Integer> interestingSignals = Set.of(
                20, 60, 100, 140, 180, 220
        );

        int sumOfInterestingSignalStrengths = 0;


        for (int clock = 1, i = 0; i < input.size(); clock++) {
            if (!cpu.isBusy()) {
                cpu.executeInstruction(input.get(i));
                i++;
            }

            if (interestingSignals.contains(clock)) {
                sumOfInterestingSignalStrengths +=
                        cpu.getCurrentSignalStrength(clock);
            }

            crt.print(clock);
            cpu.tick();
        }

        System.out.println(sumOfInterestingSignalStrengths);
    }

    public static class CRT {

        public static final int CRT_WIDTH = 40;

        private final CPU cpu;

        public CRT(CPU cpu) {
            this.cpu = cpu;
        }

        public void print(int clock) {
            int offset = (clock - 1) % CRT_WIDTH;
            int x = cpu.getX();

            // x - 1 >= offset >= x + 1
            if (offset <= x + 1 && offset >= x - 1) {
                System.out.print("#");
                System.out.print("#");
            } else {
                System.out.print(".");
                System.out.print(".");
            }

            if (clock % CRT_WIDTH == 0) {
                System.out.print("\n");
            }
        }
    }

    public static class CPU {
        int x = 1;

        int busyCycles = 0;

        int v = 0;

        public void executeInstruction(String instruction) {
            if (instruction.startsWith("addx")) {
                var v = Integer.parseInt(instruction.replace("addx ", ""));
                busyCycles += 2;
                this.v = v;
            } else if (instruction.startsWith("noop")) {
                v = 0;
                busyCycles++;
            }
        }

        public void tick() {
            busyCycles--;
            if (busyCycles == 0) {
                x += v;
            }
        }

        public int getX() {
            return x;
        }

        public boolean isBusy() {
            return busyCycles > 0;
        }

        public int getCurrentSignalStrength(int clock) {
            return clock * x;
        }
    }
}