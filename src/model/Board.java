package chess.model;

public class Board {
    private Piece[][] grid = new Piece[8][8];

    public Board() {
        initBoard();
    }

    // Empty constructor for MoveValidator's copyBoard()
    public Board(boolean empty) {
        // intentionally empty, no initBoard()
    }

    public Piece getPiece(int row, int col) {
        return grid[row][col];
    }

    public void movePiece(int fR, int fC, int tR, int tC) {
        grid[tR][tC] = grid[fR][fC];
        grid[fR][fC] = null;
    }

    public Piece[][] getGrid() {
        return grid;
    }

    private void initBoard() {
        // Black pieces - top
        grid[0][0] = new Rook("black");
        grid[0][1] = new Knight("black");
        grid[0][2] = new Bishop("black");
        grid[0][3] = new Queen("black");
        grid[0][4] = new King("black");
        grid[0][5] = new Bishop("black");
        grid[0][6] = new Knight("black");
        grid[0][7] = new Rook("black");
        for (int c = 0; c < 8; c++) grid[1][c] = new Pawn("black");

        // White pieces - bottom
        grid[7][0] = new Rook("white");
        grid[7][1] = new Knight("white");
        grid[7][2] = new Bishop("white");
        grid[7][3] = new Queen("white");
        grid[7][4] = new King("white");
        grid[7][5] = new Bishop("white");
        grid[7][6] = new Knight("white");
        grid[7][7] = new Rook("white");
        for (int c = 0; c < 8; c++) grid[6][c] = new Pawn("white");
    }

    public void printBoard() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                System.out.print(grid[r][c] == null ? "-- " : grid[r][c].getColor().charAt(0) + grid[r][c].getSymbol() + " ");
            }
            System.out.println();
        }
    }
}