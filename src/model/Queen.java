package model;

public class Queen extends Piece {
    public Queen(String color) {
        super(color);
    }

    @Override
    public String getSymbol() { return "Q"; }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // Must move like Rook or Bishop
        boolean straightLine = (fromRow == toRow || fromCol == toCol);
        boolean diagonal = (rowDiff == colDiff);
        if (!straightLine && !diagonal) return false;

        // Can't capture own piece
        if (board[toRow][toCol] != null && board[toRow][toCol].getColor().equals(this.getColor())) return false;

        // Path clearing
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