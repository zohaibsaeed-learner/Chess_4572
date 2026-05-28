package logic;

public class EloCalculator {
    private static final int K_FACTOR = 32;

    public int calculateNewRating(int currentElo, int opponentElo, String matchResult, boolean isPlayer1) {

        double ratingDifference = (double) (opponentElo - currentElo);
        double expectedScore = 1.0 / (1.0 + Math.pow(10.0, ratingDifference / 400.0));

        double actualScore = getActualScore(matchResult, isPlayer1);

        double ratingChange = K_FACTOR * (actualScore - expectedScore);

        return (int) Math.round(currentElo + ratingChange);
    }

    private double getActualScore(String matchResult, boolean isPlayer1) {

        // Converts Talha's incoming string to uppercase to avoid case-sensitive bugs
        String result = matchResult.toUpperCase();

        if (result.equals("DRAW")) {
            return 0.5;
        }

        // Matches the exact match result strings sent from Talha's GameController
        if (result.equals("P1_WIN")) {
            return isPlayer1 ? 1.0 : 0.0;
        }

        if (result.equals("P2_WIN")) {
            return isPlayer1 ? 0.0 : 1.0;
        }

        System.out.println("WARNING [EloCalculator]: Unrecognized match result '" + matchResult + "'. Defaulting to DRAW.");
        return 0.5;
    }

}