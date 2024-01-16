package com.cda.winit.auth.domain.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtTokenService {

    private static String secretKey="Juw34WOQg0Rn397En9Ek+LSH4fDEl4QSBGeN1izonY6xA2/sJLVQs2I5vT5ydJxclQUaiLNc2xqlpcodiEnQ5nGiKCXtBbmO6jkpsxBV/h9HgpzmtkSiahnqolPzE0pPEsEQBa2Sow4pLM1yRahGhKoHUBHEykKL8ADJPyJ4n578th4s5vYAaErhBnJ9rVua42RiQLa8avCo6yiKskfAdKegJvdUv/jkZNrXzeIwvjmVQvoUWvtYDgsKP/8RSBkQ5c0snaDQ/Bl7XaPsp/rk1Cy6FW6pb4p6RMyBwVsFxtMEGkM0rxjpUkinIwRxidkk5aeMU8xjx+IH9D5CIAPZzM9GgzbI7WNHMKQKp8iUkC4=";
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();

        return Jwts.
                builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(now.getTime() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact()
                ;
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.
                parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                ;
    }

    public Date extractExpiration(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

