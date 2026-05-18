package persistence;

import model.Board;
import model.Piece;
import java.io.*;

public class BoardSerializer {

    public static void saveGame(Board board, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(board.getGrid());
            System.out.println("Game saved to " + filename);
        } catch (IOException e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    public static Piece[][] loadGame(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Piece[][] grid = (Piece[][]) in.readObject();
            System.out.println("Game loaded from " + filename);
            return grid;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Load failed: " + e.getMessage());
            return null;
        }
    }
}