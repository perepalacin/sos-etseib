package services;

import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtService {

    private static String secretKey;

    static {
        Properties props = new Properties();
        try {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            props.load(new FileInputStream("/Users/perepalacin/Documents/pere-repos/files-system/sos-etseib/env.properties"));
            secretKey = props.getProperty("JWT_SECRET").trim();
        } catch (IOException e) {
            System.out.println("Error while starting the JWT service" + e.getMessage());
            throw new RuntimeException("Failed to read database configuration", e);
        }
    }

    public String generateToken(UUID userId) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userId.toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserId(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    //    powder/luv
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UUID userid) {
        final UUID USER_ID_FROM_TOKEN = UUID.fromString(extractUserId(token));
        return (userid.equals(USER_ID_FROM_TOKEN)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
