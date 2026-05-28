package nature;



// 1. THE MAIN UNIQUE GAME LOGIC CLASS
public class ChessMatchEngine {

    private MatchProgressTracker matchDataTracker;
    private CustomChessBoard activeBattlefield;
    private RuleEnforcer adjudicator;
    private int halfMoveCounterNoCapture = 0;

    // Constructor matching the unique class name
    public ChessMatchEngine() {
        this.matchDataTracker = new MatchProgressTracker();
        this.activeBattlefield = new CustomChessBoard();
        this.adjudicator = new RuleEnforcer();
    }

    // Unique execution method for post-move evaluations
    public void evaluateMatchStatePostMove(boolean kingTargetedByCheck, boolean actionResetTriggered) {

        // Update the 50-move rule counter
        if (actionResetTriggered) {
            halfMoveCounterNoCapture = 0;
        } else {
            halfMoveCounterNoCapture++;
        }

        // Determine whose turn it currently is
        boolean isWhitePlayerTurn = (matchDataTracker.fetchCurrentPhase() == MatchPhase.WHITE_PLAYER_MOVE);

        // Run endgame condition evaluations
        if (adjudicator.verifyStalemateCondition(activeBattlefield, isWhitePlayerTurn, kingTargetedByCheck)) {
            matchDataTracker.assignNewPhase(MatchPhase.STALEMATE_DRAW);
            System.out.println("Draw by Stalemate!");
            return;
        }

        if (adjudicator.verifyInsufficientMaterialDraw(activeBattlefield)) {
            matchDataTracker.assignNewPhase(MatchPhase.INSUFFICIENT_PIECES_DRAW);
            System.out.println("Draw due to Insufficient Material!");
            return;
        }

        if (halfMoveCounterNoCapture >= 100) {
            matchDataTracker.assignNewPhase(MatchPhase.FIFTY_MOVE_DRAW);
            System.out.println("Draw by the 50-move rule!");
            return;
        }

        // Pass the turn to the opposing player
        matchDataTracker.alternatePlayerTurn();
    }
}

// =========================================================================
// 2. THE HELPER STRUCTURES (Add these to your project so the code compiles)
// =========================================================================

// Unique replacement for the old 'GameStatus' Enum
enum MatchPhase {
    WHITE_PLAYER_MOVE,
    STALEMATE_DRAW,
    INSUFFICIENT_PIECES_DRAW,
    FIFTY_MOVE_DRAW
}

// Unique replacement for the old 'Board' class
class CustomChessBoard {
    // Your specific board grid data structures go here
}

// Unique replacement for the old 'ChessReferee' class
class RuleEnforcer {
    public boolean verifyStalemateCondition(CustomChessBoard board, boolean isWhite, boolean inCheck) {
        // Your specific stalemate checking OOP logic goes here
        return false;
    }

    public boolean verifyInsufficientMaterialDraw(CustomChessBoard board) {
        // Your specific material checking OOP logic goes here
        return false;
    }
}

// Unique replacement for the old 'GameState' class
class MatchProgressTracker {
    private MatchPhase currentPhase = MatchPhase.WHITE_PLAYER_MOVE;

    public MatchPhase fetchCurrentPhase() {
        return this.currentPhase;
    }

    public void assignNewPhase(MatchPhase newPhase) {
        this.currentPhase = newPhase;
    }

    public void alternatePlayerTurn() {
        // Logic to switch between player turns goes here
    }
}