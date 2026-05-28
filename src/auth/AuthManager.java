package auth;

import java.util.HashMap;
import java.util.HashSet;

public class AuthManager {

    private HashMap<String, String> userDatabase;
    private HashSet<String> activeSessions;

    public AuthManager() {
        this.userDatabase = new HashMap<>();
        this.activeSessions = new HashSet<>();
    }

    public boolean signup(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("ERROR: Username and password cannot be empty.");
            return false;
        }

        if (userDatabase.containsKey(username)) {
            System.out.println("ERROR: Username [" + username + "] is already taken.");
            return false;
        }

        userDatabase.put(username, password);
        System.out.println("SUCCESS: Account created for [" + username + "].");
        return true;
    }

    public boolean login(String username, String password) {
        if (!userDatabase.containsKey(username)) {
            System.out.println("ERROR: User [" + username + "] not found.");
            return false;
        }

        if (userDatabase.get(username).equals(password)) {
            activeSessions.add(username);
            System.out.println("SUCCESS: User [" + username + "] logged in.");
            return true;
        }

        System.out.println("ERROR: Incorrect password for [" + username + "].");
        return false;
    }

    public void logout(String username) {
        if (activeSessions.contains(username)) {
            activeSessions.remove(username);
            System.out.println("SUCCESS: User [" + username + "] logged out.");
        } else {
            System.out.println("WARNING: User [" + username + "] is not currently logged in.");
        }
    }
}