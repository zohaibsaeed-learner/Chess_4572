package model;

public class King extends Piece {
    public King(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        return false; // Abubaker fills this
    }
}