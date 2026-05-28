package nature;
import java.util.Scanner;

// =========================================================================
// 1. GAME OVER CONDITIONS ENUM
// =========================================================================
enum GameOverReason {
    CHECKMATE,
    STALEMATE,
    RESIGNATION,
    DRAW_MUTUAL
}

// =========================================================================
// 2. STATS / ANALYTICS PANEL CLASS
// =========================================================================
class PlayerStats {
    private final String username;
    private int gamesPlayed;
    private int wins;
    private int losses;
    private int draws;
    private int eloRank;

    // Standard starting defaults for chess profiles
    public PlayerStats(String username) {
        this.username = username;
        this.gamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.eloRank = 1200; // Standard base Elo rank
    }

    // Mathematical Win Percentage Calculation
    public double getWinPercentage() {
        if (gamesPlayed == 0) return 0.0;
        return ((double) wins / gamesPlayed) * 100.0;
    }

    /**
     * Updates profile values using the standard Elo Rating Formula.
     * Expected Score = 1 / (1 + 10^((OpponentElo - PlayerElo) / 400))
     */
    public void updateElo(int opponentElo, double scoreOutcome) {
        int kFactor = 32; // Maximum adjustment per game
        double expectedScore = 1.0 / (1.0 + Math.pow(10.0, (opponentElo - this.eloRank) / 400.0));

        // New Rating = Old Rating + K * (Actual Score - Expected Score)
        this.eloRank = (int) Math.round(this.eloRank + kFactor * (scoreOutcome - expectedScore));
    }

    public void recordMatchResult(String outcome, int opponentElo) {
        this.gamesPlayed++;
        switch (outcome.toLowerCase()) {
            case "win" -> {
                this.wins++;
                updateElo(opponentElo, 1.0); // 1.0 points for a win
            }
            case "loss" -> {
                this.losses++;
                updateElo(opponentElo, 0.0); // 0.0 points for a loss
            }
            case "draw" -> {
                this.draws++;
                updateElo(opponentElo, 0.5); // 0.5 points for a draw
            }
        }
    }

    public void displayStatsPanel() {
        System.out.println("\n=== ANALYTICS PANEL FOR: " + username.toUpperCase() + " ===");
        System.out.println("Elo Rating Score : " + eloRank);
        System.out.println("Total Matches    : " + gamesPlayed);
        System.out.println("Match Record     : " + wins + " W / " + losses + " L / " + draws + " D");
        System.out.printf("Calculated Win %%  : %.1f%%\n", getWinPercentage());
        System.out.println("==========================================");
    }

    public int getEloRank() { return eloRank; }
    public String getUsername() { return username; }
}

// =========================================================================
// 3. GAME-OVER DIALOG PANEL UI SIMULATION
// =========================================================================
class GameOverDialog {

    /**
     * Renders a descriptive confirmation dialog showing match points
     * and distributions based on how the match ended.
     */
    public static void showDialog(PlayerStats white, PlayerStats black, GameOverReason reason, String winner) {
        System.out.println("\n##########################################");
        System.out.println("            GAME OVER DIALOG              ");
        System.out.println("##########################################");

        // 1. Process trigger reason context text
        switch (reason) {
            case CHECKMATE -> System.out.println("Termination Flag: CHECKMATE! King cornered.");
            case STALEMATE -> System.out.println("Termination Flag: STALEMATE! No legal moves remaining.");
            case RESIGNATION -> System.out.println("Termination Flag: RESIGNATION! A competitor admitted defeat.");
            case DRAW_MUTUAL -> System.out.println("Termination Flag: DRAW! Insufficient material or agreement.");
        }

        // 2. Save structural variables for old ratings to show visual delta change
        int oldWhiteElo = white.getEloRank();
        int oldBlackElo = black.getEloRank();

        // 3. Apply profile database updates
        if (winner.equalsIgnoreCase("white")) {
            System.out.println("Match Outcome   : WHITE wins the match!");
            white.recordMatchResult("win", oldBlackElo);
            black.recordMatchResult("loss", oldWhiteElo);
        } else if (winner.equalsIgnoreCase("black")) {
            System.out.println("Match Outcome   : BLACK wins the match!");
            white.recordMatchResult("loss", oldBlackElo);
            black.recordMatchResult("win", oldWhiteElo);
        } else {
            System.out.println("Match Outcome   : The game ended in a DRAW split!");
            white.recordMatchResult("draw", oldBlackElo);
            black.recordMatchResult("draw", oldWhiteElo);
        }

        // 4. Print instant rating adjustments
        System.out.println("\n--- Live ELO Rating Updates ---");
        System.out.println(white.getUsername() + " (White): " + oldWhiteElo + " -> " + white.getEloRank()
                + " (" + (white.getEloRank() - oldWhiteElo >= 0 ? "+" : "") + (white.getEloRank() - oldWhiteElo) + ")");
        System.out.println(black.getUsername() + " (Black): " + oldBlackElo + " -> " + black.getEloRank()
                + " (" + (black.getEloRank() - oldBlackElo >= 0 ? "+" : "") + (black.getEloRank() - oldBlackElo) + ")");
        System.out.println("##########################################\n");
    }
}

// =========================================================================
// 4. MAIN PROGRAM MANAGEMENT EXECUTION
// =========================================================================
public class GameAnalyticsSystem1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Setting up two test player pGaofiles
        PlayerStats player1 = new PlayerStats("Alice");
        PlayerStats player2 = new PlayerStats("Bob");

        System.out.println("--- Starting Chess Analytics System ---");
        player1.displayStatsPanel();
        player2.displayStatsPanel();

        // SIMULATION 1: White wins via Checkmate
        System.out.println("\n[Match Simulation 1 Triggered...]");
        GameOverDialog.showDialog(player1, player2, GameOverReason.CHECKMATE, "white");

        // SIMULATION 2: Black wins via Resignation
        System.out.println("[Match Simulation 2 Triggered...]");
        GameOverDialog.showDialog(player1, player2, GameOverReason.RESIGNATION, "black");

        // SIMULATION 3: Game ends in a Stalemate
        System.out.println("[Match Simulation 3 Triggered...]");
        GameOverDialog.showDialog(player1, player2, GameOverReason.STALEMATE, "draw");

        // Display updated profile histories at the end of the day
        System.out.println("\n--- Final Profile Records After Today's Sessions ---");
        player1.displayStatsPanel();
        player2.displayStatsPanel();

        scanner.close();
    }
}