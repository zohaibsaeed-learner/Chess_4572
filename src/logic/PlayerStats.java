package logic;

public class PlayerStats {
    private final String username;
    private int gamesPlayed, wins, losses, draws, eloRank;

    public PlayerStats(String username) {
        this.username = username;
        this.eloRank = 1200;
    }

    public void recordMatchResult(String outcome, int opponentElo) {
        gamesPlayed++;
        double score = outcome.equals("win") ? 1.0 : outcome.equals("loss") ? 0.0 : 0.5;
        if (outcome.equals("win")) wins++;
        else if (outcome.equals("loss")) losses++;
        else draws++;
        double expected = 1.0 / (1.0 + Math.pow(10.0, (opponentElo - eloRank) / 400.0));
        eloRank = (int) Math.round(eloRank + 32 * (score - expected));
    }

    public void displayStatsPanel() {
        System.out.println("=== STATS: " + username + " ===");
        System.out.println("Elo: " + eloRank + " | Games: " + gamesPlayed);
        System.out.println(wins + "W / " + losses + "L / " + draws + "D");
    }

    public int getEloRank() { return eloRank; }
    public String getUsername() { return username; }
}