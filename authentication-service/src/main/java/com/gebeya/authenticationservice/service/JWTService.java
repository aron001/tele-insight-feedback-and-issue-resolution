package com.gebeya.authenticationservice.service;

import com.gebeya.authenticationservice.exception.JwtExpiredException;
import com.gebeya.authenticationservice.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    public String generateToken(Users user) {
        return generateToken(Map.of(
                "authority", user.getAuthority(),
                "roleId", user.getRoleId()), user);
    }

    private String generateToken(Map<String, Object> extraClaims, Users user) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + DateUtils.MILLIS_PER_DAY))
                .setIssuer("com.gebeya.teleinsght")
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, Users user) {
        final String userName = extractUsername(token);
        final Date expiration = extractExpiration(token);
        if (userName.equals(user.getUsername()) && !expiration.before(new Date())) {
            return true;
        } else {
            throw new JwtExpiredException("The token is expired");
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
}
