package com.springgoals.security;
import java.util.Date;

import com.springgoals.controller.LogController;
import com.springgoals.exception.AuthenticationException;
import com.springgoals.model.User;
import io.jsonwebtoken.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtility {

    private static final Logger logger = LogManager.getLogger(JwtTokenUtility.class);
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour
    private String SECRET_KEY = "abcdefghijklmnOPQRSTUVWXYZ";

    public String generateJWTToken(User user) {
        return Jwts.builder().claim(Claims.ISSUER, user.getEmail())
                .setIssuer( user.getEmail() )
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Claims validateJwtToken(String jwtToken) throws AuthenticationException {
        Claims body;
        try {
            body = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken).getBody();
        } catch (ExpiredJwtException expiredJwtException) {
            logger.error("Error in JwtTokenUtility validateJwtToken, expiredJwtException");
            throw new AuthenticationException(expiredJwtException.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.error("Error in JwtTokenUtility validateJwtToken, illegalArgumentException");
            throw new AuthenticationException(illegalArgumentException.getMessage());
        } catch (MalformedJwtException malformedJwtException) {
            logger.error("Error in JwtTokenUtility validateJwtToken, malformedJwtException");
            throw new AuthenticationException(malformedJwtException.getMessage());
        } catch (UnsupportedJwtException unsupportedJwtException) {
            logger.error("Error in JwtTokenUtility validateJwtToken, unsupportedJwtException");
            throw new AuthenticationException(unsupportedJwtException.getMessage());
        } catch (SignatureException signatureException) {
            logger.error("Error in JwtTokenUtility validateJwtToken, signatureException");
            throw new AuthenticationException(signatureException.getMessage());
        }
        return body;
    }

    public String getSubject(String jwtToken) {
        return parseClaims(jwtToken).getSubject();
    }

    private Claims parseClaims(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwtToken)
                .getBody();
    }
}
