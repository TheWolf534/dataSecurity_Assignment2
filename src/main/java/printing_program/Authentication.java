package printing_program;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Authentication {
    private String[][] userCredentials;
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();;
    private static final long TOKEN_VALIDITY = 10 * 1000; // 10 seconds
    private final String credentialsPath;

    public Authentication(String path) throws IOException {
        credentialsPath = path;
        userCredentials = loadCredentials(credentialsPath);
    }

    public String[][] loadCredentials(String filePath) throws IOException {
        List<String[]> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                lines.add(row);
            }
        }

        return lines.toArray(new String[0][]);
    }
    // Write credentials to file
    public void setCredentials() {
        try {
            FileWriter writer = new FileWriter(credentialsPath);
            for (String[] row : userCredentials) {
                writer.write(String.join(",", row) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String hashPlaintext(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public void addUser(String user, String password) {
        if (findUserPassword(user) != null) {
            return;
        }

        String hashedPassword = hashPlaintext(password);
        String[] newUser = {user, hashedPassword};
        String[][] newCredentials = new String[userCredentials.length + 1][2];
        System.arraycopy(userCredentials, 0, newCredentials, 0, userCredentials.length);
        newCredentials[userCredentials.length] = newUser;
        userCredentials = newCredentials;
        setCredentials();
    }

    public String findUserPassword(String user) {
        for (String[] row : userCredentials) {
            if (row[0].equals(user)) {
                return row[1];
            }
        }
        return null;
    }

    public boolean verifyPassword(String user, String password) {
        String hashedPassword = findUserPassword(user);
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }

    // Generate JWT token after successful authentication
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Get username from token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    // Combined login method that returns JWT token
    public String loginSession(String username, String password) {
        if (verifyPassword(username, password)) {
            return generateToken(username);
        }
        return null;
    }
}

