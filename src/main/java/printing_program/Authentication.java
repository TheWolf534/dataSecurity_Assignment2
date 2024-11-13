package printing_program;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Authentication {
    private final String[][] userCredentials;
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();;
    private static final long TOKEN_VALIDITY = 10 * 1000; // 10 seconds

    public Authentication(String path) throws IOException {
        userCredentials = loadCredentials(path);
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

    public String hashPlaintext(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
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

