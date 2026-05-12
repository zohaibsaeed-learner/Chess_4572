package model;

public class Bishop extends Piece {
    public Bishop(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        return false; // Abubaker fills this
    }
}