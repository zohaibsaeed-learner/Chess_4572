package model;

public class Rook extends Piece {
    public Rook(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        return false; // Abubaker fills this
    }
}