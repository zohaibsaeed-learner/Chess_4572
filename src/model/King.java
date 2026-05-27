package model;

public class King extends Piece {
    public King(String color) {
        super(color);
    }

    @Override
    public String getSymbol() { return "K"; }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // One square any direction
        if (rowDiff > 1 || colDiff > 1) return false;

        // Can't capture own piece
        if (board[toRow][toCol] != null && board[toRow][toCol].getColor().equals(this.getColor())) return false;

        return true;
    }
}