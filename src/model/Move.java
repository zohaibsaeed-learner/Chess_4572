package model;

public class Move {

    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;

    private Piece movedPiece;
    private Piece capturedPiece;  //depends on zohaib(Piece)

    private boolean isCastlingMove;
    private boolean isEnPassant;
    private Piece promotedTo;

    public Move(int startRow, int startCol, int endRow, int endCol, Piece movedPiece, Piece capturedPiece) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;

        this.isCastlingMove = false;
        this.isEnPassant = false;
        this.promotedTo = null;
    }

    public int getStartRow() { return startRow; }
    public int getStartCol() { return startCol; }
    public int getEndRow() { return endRow; }
    public int getEndCol() { return endCol; }

    public Piece getMovedPiece() { return movedPiece; }
    public Piece getCapturedPiece() { return capturedPiece; }

    public boolean isCastlingMove() { return isCastlingMove; }
    public boolean isEnPassant() { return isEnPassant; }
    public Piece getPromotedTo() { return promotedTo; }

    public void setCastlingMove(boolean castlingMove) { this.isCastlingMove = castlingMove; }
    public void setEnPassant(boolean enPassant) { this.isEnPassant = enPassant; }
    public void setPromotedTo(Piece promotedTo) { this.promotedTo = promotedTo; }
    @Override
    public String toString() {
        return "Move from (" + startRow + "," + startCol + ") to (" + endRow + "," + endCol + ")";
    }
}

