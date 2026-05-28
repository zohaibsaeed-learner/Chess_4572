package nature;

// Mocking the GameStatus enum so the code compiles perfectly
enum GameStatusx {
    WHITE_TURN, STALEMATE, DRAW
}

// Mocking the GameState class with necessary methods
class GameStatex {
    private GameStatus currentStatus = GameStatus.WHITE_TURN;

    public GameStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(GameStatus status) {
        this.currentStatus = status;
    }

    public void switchTurn() {
        // Logic to switch turns goes here
    }
}

class Board {
    // Your board layout details go here
}

class ChessReferee {
    // FIXED: Added the missing isStalemate method structure
    public boolean isStalemate(Board board, boolean isWhiteTurn, boolean isKingInCheck) {
        // Your stalemate logic will go here. Returning false as a default placeholder.
        return false;
    }

    // FIXED: Added the missing isDrawByInsufficientMaterial method structure
    public boolean isDrawByInsufficientMaterial(Board board) {
        // Your insufficient material logic will go here. Returning false as a default placeholder.
        return false;
    }
}

public class ChessGame2 {
    private GameState gameState;
    private Board board;
    private ChessReferee referee;
    private int fiftyMoveCounter = 0; // Tracks the 50-move draw rule

    public ChessGame2() {
        this.gameState = new GameState();
        this.board = new Board();
        this.referee = new ChessReferee();
    }

    // This method executes directly inside your turn-based loop right after a move happens
    public void postMoveCheck(boolean isKingInCheck, boolean wasPawnMovedOrPieceCaptured) {

        // 1. Update fifty-move draw rule counter
        if (wasPawnMovedOrPieceCaptured) {
            fiftyMoveCounter = 0;
        } else {
            fiftyMoveCounter++;
        }

        // 2. Extract current turn metrics
        boolean isWhiteTurn = (gameState.getCurrentStatus() == GameStatus.WHITE_TURN);

        // 3. Run Endgame Evaluators
        if (referee.isStalemate(board, isWhiteTurn, isKingInCheck)) {
            gameState.setCurrentStatus(GameStatus.STALEMATE);
            System.out.println("Draw by Stalemate!");
            return;
        }

        if (referee.isDrawByInsufficientMaterial(board)) {
            gameState.setCurrentStatus(GameStatus.DRAW);
            System.out.println("Draw due to Insufficient Material!");
            return;
        }

        if (fiftyMoveCounter >= 100) { // 50 full turns = 100 total half-moves
            gameState.setCurrentStatus(GameStatus.DRAW);
            System.out.println("Draw by the 50-move rule!");
            return;
        }

        // 4. If no draw/stalemate/checkmate conditions met, proceed normally
        gameState.switchTurn();
    }
}