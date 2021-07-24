package com.alethe.opf.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
/**
* Created by Kunal Kumar
*/
@Component
public class JwtUtil {

    private String secret = "Alethe";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String loginid) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, loginid);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String loginid = extractUsername(token);
        return (loginid.equals(userDetails.getUsername()) && !isTokenExpired(token));
  
//            try {
//                Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//                return true;
//            } catch (SignatureException ex) {
//                logger.error("Invalid JWT signature");
//            } catch (MalformedJwtException ex) {
//                logger.error("Invalid JWT token");
//            } catch (ExpiredJwtException ex) {
//                logger.error("Expired JWT token");
//            } catch (UnsupportedJwtException ex) {
//                logger.error("Unsupported JWT token");
//            } catch (IllegalArgumentException ex) {
//                logger.error("JWT claims string is empty.");
//            }
//            return false;
//        }
    
    }
}
