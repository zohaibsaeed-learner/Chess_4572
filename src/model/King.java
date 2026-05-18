package model;

public class King extends Piece {
    public King(String color) {
        super(color);
    }

    @Override
    public String getSymbol() { return "K"; }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        return false; // Abubaker fills this
    }
}