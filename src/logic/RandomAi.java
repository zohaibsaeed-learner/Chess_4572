import java.util.ArrayList;
import java. util.List;
import java.util.Random;


public class RandomAi {
    
    // Determines if the AI plays as White (true) or Black (false)
    private boolean isWhite; 
    private Random randomGenerator;

    public RandomAi(boolean isWhite) {
        this.isWhite = isWhite;
        this.randomGenerator = new Random();
    }

   
    public Move calculateMove(Board board, MoveValidator validator) {
        List<Move> allValidMoves = new ArrayList<>();

        // 1. Iterate through every square on the 8x8 grid
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                
                // Get whatever is sitting on square (x, y)
                Piece currentPiece = board.getPiece(x, y); 
                
                // 2. Check if there is a piece, and if it belongs to the Ai
                if (currentPiece != null && currentPiece.isWhite() == this.isWhite) {
                    
                    // 3. Ask Zohaib's validator for all legal moves for this specific piece
                    // Note: You must ensure Zohaib's method signature matches this!
                    List<Move> pieceMoves = validator.getLegalMoves(currentPiece, x, y, board);
                    
                    // Add this piece's moves to our master bucket
                    if (pieceMoves != null && !pieceMoves.isEmpty()) {
                        allValidMoves.addAll(pieceMoves);
                    }
                }
            }
        }

        // 4. End-Game Detection
        // If the bucket is completely empty, the AI has no legal moves.
        if (allValidMoves.isEmpty()) {
            System.out.println("AI has no legal moves. Game Over state reached.");
            return null; // Talha's GameController will handle checkmate/stalemate logic
        }

        // 5. Select and return a random move
        int randomIndex = randomGenerator.nextInt(allValidMoves.size());
        Move chosenMove = allValidMoves.get(randomIndex);
        
        System.out.println("AI selects: " + chosenMove.toString());
        return chosenMove;
    }
}
    

