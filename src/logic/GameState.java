package logic;

public class GameState {
    private GameStatus currentStatus;

    public GameState() { this.currentStatus = GameStatus.WHITE_TURN; }
    public GameStatus getCurrentStatus() { return this.currentStatus; }
    public void setCurrentStatus(GameStatus status) { this.currentStatus = status; }
    public boolean isGameOver() {
        return currentStatus != GameStatus.WHITE_TURN && currentStatus != GameStatus.BLACK_TURN;
    }
    public void switchTurn() {
        if (currentStatus == GameStatus.WHITE_TURN) currentStatus = GameStatus.BLACK_TURN;
        else if (currentStatus == GameStatus.BLACK_TURN) currentStatus = GameStatus.WHITE_TURN;
    }
    public void displayStatus() {
        switch (currentStatus) {
            case WHITE_TURN -> System.out.println("White's turn.");
            case BLACK_TURN -> System.out.println("Black's turn.");
            case CHECKMATE -> System.out.println("Game Over! Checkmate.");
            case STALEMATE -> System.out.println("Game Over! Stalemate.");
            case RESIGNED -> System.out.println("Game Over! Resigned.");
            case DRAW -> System.out.println("Game Over! Draw.");
        }
    }
}