/**
 * 
 */
package com.resturantservice.services;


import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
/**
 * 
 */
import org.springframework.stereotype.Service;

import com.resturantservice.JwtUtil;
import com.resturantservice.entities.*;
import com.resturantservice.repos.TokenRepository;
import com.resturantservice.repos.UserRepository;


@Service
public class UserService {
	
	@Autowired
    private  UserRepository userRepository;
	
	@Autowired
    private  TokenRepository tokenRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User getUser(String username){
    	User user = userRepository.findByUsername(username);
    	return user;
    }
    
    public User register(User user) {
    	User registsredUser = userRepository.save(user);
    	return registsredUser;
    }
    
    public String login(String username, String password) {
    	return UUID.randomUUID().toString();
    }
    
    public boolean validateToken(String authHeader) throws Exception{
    	String jwtToken = readToken(authHeader);
    	//String username = JwtUtil.extractUsername(jwtToken);
    	//User user = userRepository.findByUsername(username);
    	Optional<Token> tokenFoundAndNotExpired = tokenRepository.findByToken(jwtToken);
    	if(null!=tokenFoundAndNotExpired) {
    		return true;
    	}
    	throw new Exception("UNAUTHORIZED");
    }
    
    private String readToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extract token from the header
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            return "Token: " + token;
        } else {
            return "Authorization header is missing or invalid.";
        }
    }


}

