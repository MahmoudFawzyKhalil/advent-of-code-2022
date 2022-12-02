import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

public class Day2_2 {
    public static void main(String[] args) throws Exception {
        var inputPath = Path.of("src/Day2.txt");
        var input = Files.readAllLines(inputPath);

        int myScoreIfOtherColumnIsMatchOutcome = input.stream()
                       .map(Match::ofString)
                       .mapToInt(Match::myScore)
                       .sum();

        System.out.println(myScoreIfOtherColumnIsMatchOutcome);

    }

    enum Choice {
        ROCK(1) {
            @Override
            public int scoreAgainst(Choice opponent) {
                return switch (opponent) {
                    case PAPER -> ROCK.score + LOSE;
                    case ROCK -> ROCK.score + TIE;
                    case SCISSORS -> ROCK.score + WIN;
                };
            }

            @Override
            public Choice choiceThatFulfillsOutcome(char outcome) {
                return switch (outcome){
                    case 'X' -> SCISSORS;
                    case 'Y' -> this;
                    case 'Z' -> PAPER;
                    default -> throw new IllegalArgumentException();
                };
            }
        },
        PAPER(2) {
            @Override
            public int scoreAgainst(Choice opponent) {
                return switch (opponent) {
                    case SCISSORS -> PAPER.score + LOSE;
                    case PAPER -> PAPER.score + TIE;
                    case ROCK -> PAPER.score + WIN;
                };
            }

            @Override
            public Choice choiceThatFulfillsOutcome(char outcome) {
                return switch (outcome){
                    case 'X' -> ROCK;
                    case 'Y' -> this;
                    case 'Z' -> SCISSORS;
                    default -> throw new IllegalArgumentException();
                };
            }
        },
        SCISSORS(3) {
            @Override
            public int scoreAgainst(Choice opponent) {
                return switch (opponent) {
                    case ROCK -> SCISSORS.score + LOSE;
                    case SCISSORS -> SCISSORS.score + TIE;
                    case PAPER -> SCISSORS.score + WIN;
                };
            }

            @Override
            public Choice choiceThatFulfillsOutcome(char outcome) {
                return switch (outcome){
                    case 'X' -> PAPER;
                    case 'Y' -> this;
                    case 'Z' -> ROCK;
                    default -> throw new IllegalArgumentException();
                };
            }
        };


        static final Map<Character, Choice> charToChoice = ofEntries(
                entry('A', ROCK),
                entry('B', PAPER),
                entry('C', SCISSORS)
        );

        private static final int LOSE = 0;

        private static final int TIE = 3;

        private static final int WIN = 6;

        private final int score;

        Choice(int score) {
            this.score = score;
        }

        public abstract int scoreAgainst(Choice opponent);

        public abstract Choice choiceThatFulfillsOutcome(char outcome);

        static Choice ofChar(char c) {
            return charToChoice.get(c);
        }
    }

    record Match(Choice opponentChoice, Choice myChoice) {


        int myScore() {
            return myChoice.scoreAgainst(opponentChoice);
        }

        int opponentScore() {
            return opponentChoice.scoreAgainst(myChoice);
        }

        static Match ofString(String inputString) {
            var opponentChoice = Choice.ofChar(inputString.charAt(0));
            char outcome = inputString.charAt(2);

            Choice myChoice = opponentChoice.choiceThatFulfillsOutcome(outcome);

            return new Match(opponentChoice, myChoice);
        }
    }
}