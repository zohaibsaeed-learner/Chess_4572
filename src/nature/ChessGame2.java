package nature;
public class ChessGame2 {
    private GameState gameState;
    private Board board;
    private ChessReferee referee;
    private int fiftyMoveCounter = 0; // Tracks the 50-move draw rule

    public ChessGame() {
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