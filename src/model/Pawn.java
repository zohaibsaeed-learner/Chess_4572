package model;

public class Pawn extends Piece {
    public Pawn(String color) {
        super(color);
    }

    @Override
    public String getSymbol() { return "P"; }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int direction = getColor().equals("white") ? -1 : 1;
        int startRow = getColor().equals("white") ? 6 : 1;
        int rowDiff = toRow - fromRow;
        int colDiff = Math.abs(toCol - fromCol);

        // One step forward
        if (colDiff == 0 && rowDiff == direction) {
            return board[toRow][toCol] == null;
        }

        // Two steps forward from starting position
        if (colDiff == 0 && rowDiff == 2 * direction && fromRow == startRow) {
            return board[toRow][toCol] == null && board[fromRow + direction][fromCol] == null;
        }

        // Diagonal capture
        if (colDiff == 1 && rowDiff == direction) {
            return board[toRow][toCol] != null && !board[toRow][toCol].getColor().equals(this.getColor());
        }

        return false;
    }
}