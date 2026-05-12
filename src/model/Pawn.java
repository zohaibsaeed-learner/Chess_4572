package model;

public class Pawn extends Piece {
    public Pawn(String color) {
        super(color);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        return false; // Abubaker fills this
    }
}