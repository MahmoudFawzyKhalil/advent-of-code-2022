import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static java.util.Map.*;

public class Day2_1 {
    public static void main(String[] args) throws Exception {
        var inputPath = Path.of("src/Day2.txt");
        var input = Files.readAllLines(inputPath);

        int myScoreIfStrategyGuideIsntALie = input.stream()
                       .map(Match::ofString)
                       .mapToInt(Match::opponentScore)
                       .sum();

        System.out.println(myScoreIfStrategyGuideIsntALie);

    }

    record Match(Choice opponentChoice, Choice myChoice) {


        static Match ofString(String inputString) {
            var opponentChoice = Choice.ofChar(inputString.charAt(0));
            var myChoice = Choice.ofChar(inputString.charAt(2));
            return new Match(opponentChoice, myChoice);
        }

        int myScore() {
            return myChoice.scoreAgainst(opponentChoice);
        }

        int opponentScore() {
            return opponentChoice.scoreAgainst(myChoice);
        }
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
        };


        static final Map<Character, Choice> charToChoice = ofEntries(
                entry('A', ROCK),
                entry('B', PAPER),
                entry('C', SCISSORS),
                entry('X', ROCK),
                entry('Y', PAPER),
                entry('Z', SCISSORS)
        );

        static Choice ofChar(char c) {
            return charToChoice.get(c);
        }

        private final int score;

        private static final int LOSE = 0;

        private static final int TIE = 3;

        private static final int WIN = 6;

        public abstract int scoreAgainst(Choice opponent);


        Choice(int score) {
            this.score = score;
        }
    }
}