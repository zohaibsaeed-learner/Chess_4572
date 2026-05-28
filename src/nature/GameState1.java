package nature;
enum GameStatus {
    WHITE_TURN,
    BLACK_TURN,
    CHECKMATE,
    STALEMATE,
    RESIGNED,
    DRAW
}

/**
 * GameState handles the turn-tracking, status monitoring,
 * and state transitions for the chess match.
 */
public class GameState1 {
    private GameStatus currentStatus;

    // Default constructor: A fresh chess game always starts with White's turn
    public GameState1() {
        this.currentStatus = GameStatus.WHITE_TURN;
    }

    // Returns the current status of the game
    public GameStatus getCurrentStatus() {
        return this.currentStatus;
    }

    // Manually update the game status (e.g., when your referee logic detects checkmate/draw)
    public void setCurrentStatus(GameStatus status) {
        this.currentStatus = status;
    }

    /**
     * Checks if the game has concluded.
     * @return true if the status is any game-over condition, false if the match is active.
     */
    public boolean isGameOver() {
        return currentStatus != GameStatus.WHITE_TURN && currentStatus != GameStatus.BLACK_TURN;
    }

    /**
     * Alternates the turn between White and Black.
     * Call this immediately after a player successfully completes a valid move.
     */
    public void switchTurn() {
        if (currentStatus == GameStatus.WHITE_TURN) {
            currentStatus = GameStatus.BLACK_TURN;
        } else if (currentStatus == GameStatus.BLACK_TURN) {
            currentStatus = GameStatus.WHITE_TURN;
        } else {
            System.out.println("Warning: Cannot switch turns. The game is already over!");
        }
    }

    /**
     * Prints a user-friendly status update to the console.
     * Useful for debugging or text-based logging in your GUI console.
     */
    public void displayStatus() {
        final String prefix = "Status: ";
        switch (currentStatus) {
            case WHITE_TURN -> System.out.println(prefix + "White's turn to move.");
            case BLACK_TURN -> System.out.println(prefix + "Black's turn to move.");
            case CHECKMATE -> System.out.println(prefix + "Game Over! Checkmate.");
            case STALEMATE -> System.out.println(prefix + "Game Over! Stalemate (Draw).");
            case RESIGNED -> System.out.println(prefix + "Game Over! A player has resigned.");
            case DRAW -> System.out.println(prefix + "Game Over! The game ended in a draw.");
        }
    }
}