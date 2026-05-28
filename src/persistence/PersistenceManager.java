package persistence;

public class PersistenceManager {
    private EloCalculator eloCalculator;
    public PersistenceManager() {
        this.eloCalculator = new EloCalculator();
    }

    public boolean saveGame(String gameId, String serializedBoardData) {
        if (gameId == null || gameId.trim().isEmpty() ||
                serializedBoardData == null || serializedBoardData.trim().isEmpty()) {

            System.out.println("ERROR: Cannot save game. Missing Game ID or Board Data.");
            return false;
        }

        System.out.println("DEBUG: Packaging game [" + gameId + "] for the database...");

        // Depends on Abubaker's database object and exact method name (e.g., dbConnection.insertGame)
        boolean isSaved = dbConnection.insertGame(gameId, serializedBoardData);

        if (isSaved) {
            System.out.println("SUCCESS: Game [" + gameId + "] saved to database.");
        } else {
            System.out.println("ERROR: Database rejected the save request.");
        }

        return isSaved;

    }

    public String loadGame(String gameId) {
        if (gameId == null || gameId.trim().isEmpty()) {
            System.out.println("ERROR: Cannot load game. Missing Game ID.");
            return null;
        }

        System.out.println("DEBUG: Requesting game [" + gameId + "] from the database...");

        // Depends on Abubaker's database object and exact method name (e.g., dbConnection.fetchGame)
        String gameData = dbConnection.fetchGame(gameId);

        if (gameData != null) {
            System.out.println("SUCCESS: Game [" + gameId + "] loaded successfully.");
        } else {
            System.out.println("ERROR: Database could not find or load the game.");
        }

        return gameData;
    }

    public boolean recordMatchResult(String player1Id, String player2Id, String matchResult) {
        if (player1Id == null || player2Id == null || matchResult == null || matchResult.trim().isEmpty()) {
            System.out.println("ERROR: Cannot record match. Missing player IDs or result.");
            return false;
        }

        System.out.println("DEBUG: Processing match result [" + matchResult + "] for P1: " + player1Id + " and P2: " + player2Id);

        // Depends on Abubaker's database method names for fetching current player rankings
        int p1CurrentElo = dbConnection.getPlayerElo(player1Id);
        int p2CurrentElo = dbConnection.getPlayerElo(player2Id);

        // Depends on your own EloCalculator class methods to process the math logic
        int p1NewElo = eloCalculator.calculateNewRating(p1CurrentElo, p2CurrentElo, matchResult, true);
        int p2NewElo = eloCalculator.calculateNewRating(p2CurrentElo, p1CurrentElo, matchResult, false);

        // Depends on Abubaker's database method names for saving updated player rankings
        boolean p1Saved = dbConnection.updatePlayerElo(player1Id, p1NewElo);
        boolean p2Saved = dbConnection.updatePlayerElo(player2Id, p2NewElo);

        if (p1Saved && p2Saved) {
            System.out.println("SUCCESS: New Elo ratings saved for both players.");
            return true;
        } else {
            System.out.println("ERROR: Database failed to update Elo ratings.");
            return false;
        }
    }
}






