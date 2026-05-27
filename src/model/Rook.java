package model;

public class Rook extends Piece {
    public Rook(String color) {
        super(color);
    }

    @Override
    public String getSymbol() { return "R"; }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        // Must move in straight line
        if (fromRow != toRow && fromCol != toCol) return false;

        // Can't capture own piece
        if (board[toRow][toCol] != null && board[toRow][toCol].getColor().equals(this.getColor())) return false;

        // Path clearing — no piece in between
        int rowStep = Integer.signum(toRow - fromRow);
        int colStep = Integer.signum(toCol - fromCol);
        int r = fromRow + rowStep;
        int c = fromCol + colStep;
        while (r != toRow || c != toCol) {
            if (board[r][c] != null) return false;
            r += rowStep;
            c += colStep;
        }

        return true;
    }
}