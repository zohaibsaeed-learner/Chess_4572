package nature;
import java.util.Scanner;

public class ChessGame1 {
    private GameState gameState;
    private Scanner inputScanner;
    private boolean isRunning;

    public ChessGame() {
        this.gameState = new GameState();
        this.inputScanner = new Scanner(System.in);
        this.isRunning = true;
    }

    /**
     * The core turn-based game loop.
     */
    public void start() {
        System.out.println("========================================");
        System.out.println("      Welcome to Java OOP Chess!        ");
        System.out.println("========================================");

        // This loop keeps running turn by turn until a game-over condition occurs
        while (isRunning && !gameState.isGameOver()) {

            // 1. Show who's turn it currently is
            gameState.displayStatus();

            // 2. Get the active player's input
            System.out.print("Enter your move (e.g., 'e2 e4' or type 'resign' / 'draw'): ");
            String playerInput = inputScanner.nextLine().trim().toLowerCase();

            // 3. Process the input
            handlePlayerInput(playerInput);

            System.out.println("----------------------------------------");
        }

        // 4. The loop has exited, meaning the game is officially over
        cleanUpAndExit();
    }

    /**
     * Processes input commands, validates action types, and updates states.
     */
    private void handlePlayerInput(String input) {
        if (input.isEmpty()) {
            System.out.println("Invalid input! Please try again.");
            return;
        }

        // Check for special command states first
        if (input.equals("resign")) {
            gameState.setCurrentStatus(GameStatus.RESIGNED);
            return;
        }

        if (input.equals("draw")) {
            gameState.setCurrentStatus(GameStatus.DRAW);
            return;
        }

        // --- Simulated Move Validation Logic ---
        // In your final game, this is where you call your Board/Piece logic:
        // boolean isValid = board.movePiece(startSquare, endSquare, currentTurnColor);

        boolean isMoveValid = mockCheckMoveSyntax(input);

        if (isMoveValid) {
            System.out.println("Move executed successfully.");

            // Check for endgame scenarios AFTER a move is made, but BEFORE switching turns
            boolean checkmateDetected = checkForCheckmateMock(input);

            if (checkmateDetected) {
                gameState.setCurrentStatus(GameStatus.CHECKMATE);
            } else {
                // If the game continues safely, switch to the next player's turn
                gameState.switchTurn();
            }
        } else {
            System.out.println("Invalid move format! Use standard coordinates like 'e2 e4'.");
        }
    }

    /**
     * A simple helper simulating basic text syntax checks for testing.
     */
    private boolean mockCheckMoveSyntax(String input) {
        // Expecting something like "e2 e4" (length 5)
        return input.length() == 5 && input.contains(" ");
    }

    /**
     * A dummy simulation tracking if a specific move ends the match for demo purposes.
     * (e.g., Typing 'f7 q8' triggers a checkmate simulation).
     */
    private boolean checkForCheckmateMock(String input) {
        return input.equalsIgnoreCase("f7 q8");
    }

    /**
     * Final summary printed once the game loop breaks.
     */
    private void cleanUpAndExit() {
        System.out.println("\n========================================");
        System.out.println("               GAME OVER                ");
        gameState.displayStatus();
        System.out.println("Thank you for playing!");
        System.out.println("========================================");

        // Close resources
        inputScanner.close();
        isRunning = false;
    }

    /**
     * Main executable method to test out the game loop instantly.
     */
    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        game.start();
    }
}