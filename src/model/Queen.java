package model;

public class Queen extends Piece {
    public Queen(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        return false; // Abubaker fills this
    }
}