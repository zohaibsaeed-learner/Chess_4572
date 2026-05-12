package logic;

import model.Board;
import model.Piece;

public class MoveValidator {

    public boolean isValidMove(int fR, int fC, int tR, int tC, Board board) {
        Piece piece = board.getPiece(fR, fC);

        // No piece on source square
        if (piece == null) return false;

        // Piece's own movement logic
        if (!piece.isValidMove(fR, fC, tR, tC, board.getGrid())) return false;

        // Check if move exposes own King
        Board tempBoard = copyBoard(board);
        tempBoard.movePiece(fR, fC, tR, tC);
        if (isInCheck(piece.getColor(), tempBoard)) return false;

        return true;
    }

    public boolean isInCheck(String color, Board board) {
        // Find the King
        int kingRow = -1, kingCol = -1;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board.getPiece(r, c);
                if (p != null && p.getColor().equals(color) && p.getSymbol().equals("K")) {
                    kingRow = r;
                    kingCol = c;
                }
            }
        }

        // Loop every opponent piece, see if it can reach the King
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board.getPiece(r, c);
                if (p != null && !p.getColor().equals(color)) {
                    if (p.isValidMove(r, c, kingRow, kingCol, board.getGrid())) return true;
                }
            }
        }
        return false;
    }

    private Board copyBoard(Board original) {
        Board copy = new Board();
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                copy.getGrid()[r][c] = original.getPiece(r, c);
        return copy;
    }
}