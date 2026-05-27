import model.Board;
import model.Piece;
import persistence.BoardSerializer;
import logic.MoveValidator;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();

        // Save
        BoardSerializer.saveGame(board, "savegame.dat");

        // Move a piece
        board.movePiece(6, 0, 5, 0);
        board.printBoard();

        // Load back
        Piece[][] savedGrid = BoardSerializer.loadGame("savegame.dat");
        board = new Board(true);
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                board.getGrid()[r][c] = savedGrid[r][c];

        System.out.println("After loading:");
        board.printBoard();

        // MoveValidator tests
        MoveValidator validator = new MoveValidator();

        System.out.println("Pawn one step forward: " + validator.isValidMove(6, 0, 5, 0, board));
        System.out.println("Pawn two steps forward: " + validator.isValidMove(6, 0, 4, 0, board));
        System.out.println("Pawn backward: " + validator.isValidMove(6, 0, 7, 0, board));
        System.out.println("Rook blocked: " + validator.isValidMove(7, 0, 5, 0, board));
        System.out.println("Knight jump: " + validator.isValidMove(7, 1, 5, 2, board));
    }
}