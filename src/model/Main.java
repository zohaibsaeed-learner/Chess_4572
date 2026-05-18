import model.Piece;
import model.Board;
import persistence.BoardSerializer;

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
    }
}