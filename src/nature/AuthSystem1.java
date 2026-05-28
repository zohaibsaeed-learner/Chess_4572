package nature;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// =========================================================================
// 1. DATA MODELS
// =========================================================================
class User {
    private final String username;
    private final String passwordHash;
    private final String salt;

    public User(String username, String passwordHash, String salt) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }
}

class UserSession {
    private final String sessionId;
    private final String username;
    private final long expiryTime;

    public UserSession(String username, long validityDurationMillis) {
        this.sessionId = UUID.randomUUID().toString();
        this.username = username;
        this.expiryTime = System.currentTimeMillis() + validityDurationMillis;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUsername() {
        return username;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

// =========================================================================
// 2. AUTHENTICATION & SESSION MANAGER
// =========================================================================
class AuthManager {
    private final Map<String, User> userDatabase = new HashMap<>();
    private final Map<String, UserSession> activeSessions = new HashMap<>();
    private static final long SESSION_TIMEOUT = 30 * 60 * 1000; // 30 Minutes

    // --- Sign Up Logic ---
    public boolean signUp(String username, String password) {
        if (userDatabase.containsKey(username.toLowerCase())) {
            System.out.println("Registration Failed: Username already exists.");
            return false;
        }

        // Generate a cryptographically secure random salt unique to this user
        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);

        User newUser = new User(username, hashedPassword, salt);
        userDatabase.put(username.toLowerCase(), newUser);
        System.out.println("Registration Successful for user: " + username);
        return true;
    }

    // --- Login Logic ---
    public String login(String username, String password) {
        User user = userDatabase.get(username.toLowerCase());
        if (user == null) {
            System.out.println("Authentication Failed: Invalid username or password.");
            return null;
        }

        // Re-hash the incoming password using the user's stored salt
        String computedHash = hashPassword(password, user.getSalt());

        // Secure verification checks
        if (computedHash.equals(user.getPasswordHash())) {
            // Create a brand new active session context
            UserSession session = new UserSession(user.getUsername(), SESSION_TIMEOUT);
            activeSessions.put(session.getSessionId(), session);
            System.out.println("Login Successful! Session initiated for: " + user.getUsername());
            return session.getSessionId();
        }

        System.out.println("Authentication Failed: Invalid username or password.");
        return null;
    }

    // --- Session Verification Context ---
    public String getLoggedInUserContext(String sessionId) {
        if (sessionId == null) return null;

        UserSession session = activeSessions.get(sessionId);
        if (session == null) {
            return null;
        }

        if (session.isExpired()) {
            System.out.println("Session has expired. Re-login required.");
            activeSessions.remove(sessionId);
            return null;
        }

        return session.getUsername(); // Safe to return active user details
    }

    // --- Logout Logic ---
    public void logout(String sessionId) {
        if (activeSessions.containsKey(sessionId)) {
            activeSessions.remove(sessionId);
            System.out.println("Session terminated successfully.");
        }
    }

    // --- Cryptographic Helper Methods ---
    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    private String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Combine password and salt string to protect against precomputed dictionary lists
            String combinedInput = password + salt;
            byte[] encodedHash = digest.digest(combinedInput.getBytes(StandardCharsets.UTF_8));

            // Convert byte array to hexadecimal format string
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Critical security failure: SHA-256 algorithm not found.", e);
        }
    }
}

// =========================================================================
// 3. MAIN RUNNABLE DRIVER CLASS
// =========================================================================
public class AuthSystem {
    public static void main(String[] args) {
        AuthManager authManager = new AuthManager();

        System.out.println("=== Starting Authentication System Testing ===\n");

        // 1. Test Signup Flow
        System.out.println("--- Registering Users ---");
        authManager.signUp("Alice", "SecurePass123");
        authManager.signUp("Bob", "MySecretPassword!");
        authManager.signUp("alice", "duplicateUserCheck"); // Should gracefully fail
        System.out.println();

        // 2. Test Login Flow
        System.out.println("--- Attempting Logins ---");
        String wrongSessionToken = authManager.login("Alice", "WrongPassword"); // Should fail
        String aliceSessionToken = authManager.login("Alice", "SecurePass123"); // Should succeed
        System.out.println("Generated Session ID: " + aliceSessionToken);
        System.out.println();

        // 3. Test Context Monitoring (Checking current user profile information)
        System.out.println("--- Fetching Logged-in Context ---");
        String activeUser = authManager.getLoggedInUserContext(aliceSessionToken);
        System.out.println("Active User in Current Context: " + (activeUser != null ? activeUser : "Guest"));

        String dummyUser = authManager.getLoggedInUserContext("fake-token-xyz");
        System.out.println("Unverified Token Context: " + (dummyUser != null ? dummyUser : "Access Denied"));
        System.out.println();

        // 4. Test Termination Flow (Logout)
        System.out.println("--- Terminating Session ---");
        authManager.logout(aliceSessionToken);

        // Re-verifying context state after deletion
        activeUser = authManager.getLoggedInUserContext(aliceSessionToken);
        System.out.println("Context Status After Logout: " + (activeUser != null ? activeUser : "Logged Out Safely"));
        System.out.println("\n=== Testing Complete ===");
    }
}