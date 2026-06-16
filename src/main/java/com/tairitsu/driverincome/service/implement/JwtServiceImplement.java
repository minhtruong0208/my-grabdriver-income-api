package com.tairitsu.driverincome.service.implement;

import com.tairitsu.driverincome.security.JwtProperties;
import com.tairitsu.driverincome.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Service responsible for creating and reading JWT tokens.
 *
 * <p>This service does not authenticate username/password directly.
 * It only works with tokens after the user has been authenticated.
 */
@Service
public class JwtServiceImplement implements JwtService {
    private final SecretKey secretKey;
    private final long expirationMs;
    public JwtServiceImplement(JwtProperties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret()));
        this.expirationMs = jwtProperties.expirationMs();
    }
    /**
     * Generates a JWT token for an authenticated user.
     *
     * @param user authenticated user details
     * @return signed JWT token
     */
    @Override
    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim(
                        "role",
                        user.getAuthorities()
                                .stream()
                                .findFirst()
                                .orElseThrow()
                                .getAuthority()
                )
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey)
                .compact();
    }
    /**
     * Extracts username from a JWT token.
     *
     * @param token JWT token
     * @return username stored in token subject
     */
    @Override
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    /**
     * Checks whether token belongs to the given user and is not expired.
     *
     * @param token JWT token
     * @param userDetails user loaded from database
     * @return true if token is valid
     */
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
