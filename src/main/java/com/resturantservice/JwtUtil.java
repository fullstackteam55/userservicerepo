/**
 * 
 */
package com.resturantservice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

	 public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	    private static final long EXPIRATION_TIME = 60 * 60 * 1000; // 60 minutes

	    public static String generateToken(String username) {
	    System.out.println("Secret Key:"+SECRET_KEY);
	        return Jwts.builder()
	                .setSubject(username)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .signWith(SECRET_KEY)
	                .compact();
	    }

	    public static boolean validateToken(String token) {
	        try {
	            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	    }

	    public static String extractUsername(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(SECRET_KEY)
	                .build()
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject();
	    }
}
