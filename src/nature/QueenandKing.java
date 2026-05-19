package nature;

import java.util.ArrayList;
import java.util.List;

// ==========================================
// 1. POSITION RECORD
// ==========================================
/**
 * Represents a coordinate on the 8x8 chess board.
 */
record Position(int row, int col) {
    public boolean isValid() {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}

// ==========================================
// 2. ABSTRACT PIECE BASE CLASS
// ==========================================
abstract class Piece {
    protected Position position;
    protected final boolean isWhite;

    public Piece(Position position, boolean isWhite) {
        this.position = position;
        this.isWhite = isWhite;
    }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    public boolean isWhite() { return isWhite; }

    /**
     * Calculates all potential moves for this piece.
     * @param board The current 8x8 matrix representing the board state.
     */
    public abstract List<Position> getPossibleMoves(Piece[][] board);

    // Helper to quickly verify if a square is open or occupied by an enemy
    protected boolean canMoveTo(Position pos, Piece[][] board) {
        if (!pos.isValid()) return false;
        Piece target = board[pos.row()][pos.col()];
        return target == null || target.isWhite() != this.isWhite;
    }
}

// ==========================================
// 3. QUEEN SUBCLASS
// ==========================================
class Queen extends Piece {

    public Queen(Position position, boolean isWhite) {
        super(position, isWhite);
    }

    @Override
    public List<Position> getPossibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();

        // Queen directions: 4 orthogonal (Rook) + 4 diagonal (Bishop)
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int[] d : directions) {
            int nextRow = position.row() + d[0];
            int nextCol = position.col() + d[1];

            while (true) {
                Position nextPos = new Position(nextRow, nextCol);
                if (!nextPos.isValid()) break;

                Piece target = board[nextRow][nextCol];
                if (target == null) {
                    moves.add(nextPos);
                } else {
                    // Capture enemy piece, then stop sliding
                    if (target.isWhite() != this.isWhite) {
                        moves.add(nextPos);
                    }
                    break;
                }
                nextRow += d[0];
                nextCol += d[1];
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        return isWhite ? "Q" : "q";
    }
}

// ==========================================
// 4. KING SUBCLASS
// ==========================================
class King extends Piece {
    private boolean hasMoved = false;

    public King(Position position, boolean isWhite) {
        super(position, isWhite);
    }

    public boolean hasMoved() { return hasMoved; }
    public void setHasMoved(boolean hasMoved) { this.hasMoved = hasMoved; }

    @Override
    public List<Position> getPossibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();

        // King directions: 1 step in any direction
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int[] d : directions) {
            Position nextPos = new Position(position.row() + d[0], position.col() + d[1]);

            if (canMoveTo(nextPos, board)) {
                moves.add(nextPos);
            }
        }

        // Note: Castling conditions (checking if King/Rook moved, clear paths,
        // and checking if squares are under attack) should be integrated here
        // or handled by your main Game controller.

        return moves;
    }

    @Override
    public String toString() {
        return isWhite ? "K" : "k";
    }
}

// ==========================================
// 5. DEMO / VERIFICATION CLASS
// ==========================================
public class QueenandKing{
    public static void main(String[] args) {
        // Initialize an empty 8x8 board matrix
        Piece[][] board = new Piece[8][8];

        // 1. Place a White Queen in the center (row 4, col 4)
        Queen whiteQueen = new Queen(new Position(4, 4), true);
        board[4][4] = whiteQueen;

        // 2. Place a White King nearby (row 4, col 5) -> acts as an ally block
        King whiteKing = new King(new Position(4, 5), true);
        board[4][5] = whiteKing;

        // 3. Place an enemy piece in the path of the Queen (row 2, col 4) -> acts as a capture target
        King blackKing = new King(new Position(2, 4), false);
        board[2][4] = blackKing;

        // Calculate moves for the Queen
        List<Position> queenMoves = whiteQueen.getPossibleMoves(board);

        System.out.println("--- Chess Piece Integration Demo ---");
        System.out.println("White Queen is at: (4,4)");
        System.out.println("Ally King blocks at: (4,5)");
        System.out.println("Enemy King sits at: (2,4)");
        System.out.println("\nCalculated valid destination coordinates for the Queen:");

        for (Position pos : queenMoves) {
            System.out.println("-> Row: " + pos.row() + ", Col: " + pos.col());
        }

        // Verify Queen hit the enemy but didn't jump over it (Row 1, Col 4 should NOT be in the list)
        boolean jumpedObstacle = queenMoves.stream().anyMatch(p -> p.row() == 1 && p.col() == 4);
        System.out.println("\nDid the queen jump past the enemy at (2,4)? " + (jumpedObstacle ? "Yes❌" : "No✅ (Correct Behavior)"));
    }
}