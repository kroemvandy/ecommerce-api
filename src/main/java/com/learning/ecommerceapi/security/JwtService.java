package com.learning.ecommerceapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; //5 hour
    public static final String SECRET = "5465464bcd3967c1859c1c9eeb365dc8ebd62e782dbfa7e094b6e40404dcdb8b15f4bcd3967c1859c1c9eeb365dc8ebd62e782dbfa7e094b6e40404dcdb8b15f";

    private String createToken(Map<String, Object> claim, String subject){
        return Jwts.builder()
                .setClaims(claim)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256, getSignKey()).compact();
    }
    private Key getSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    //2. generate token for user
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
    //3. retrieving any information from token we will need the secret key
    private Claims extractALlClaim(String token){

        return Jwts.parser()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    //4. extract a specific claim from the JWT token’s claims.
    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractALlClaim(token);
        return claimsResolver.apply(claims);
    }
    //5. retrieve username from jwt token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    //6. retrieve expiration date from jwt token
    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    //7. check expired token
    private Boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }
    //8. validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
