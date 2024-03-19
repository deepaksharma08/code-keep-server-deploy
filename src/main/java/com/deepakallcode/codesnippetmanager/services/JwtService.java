package com.deepakallcode.codesnippetmanager.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.juli.logging.Log;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class JwtService {
    final String SIGN_STRING = "MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCD3TxF78XGy5MCHSk3cQO/LSE1/fYV1jmSmIorpE0BlcQ==";

    private final static Logger logger = Logger.getLogger(JwtService.class.getName());

    public String getEmailFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    public String generateJwtToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean verifyToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.info("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.info("JWT token is expired: "+ e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.info("JWT token is unsupported: "+ e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.info("JWT claims string is empty: "+ e.getMessage());
        }catch (Exception e) {
            logger.info("Unknown Exception :"+ e.getMessage());
        }
        return false;
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] signInByte = Decoders.BASE64.decode(SIGN_STRING);
        return Keys.hmacShaKeyFor(signInByte);
    }
}
