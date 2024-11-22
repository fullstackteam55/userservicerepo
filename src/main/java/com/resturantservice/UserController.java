/**
 * 
 */
package com.resturantservice;

import java.util.Base64;
import java.util.Date;

/**
 * 
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.resturantservice.entities.Token;
import com.resturantservice.entities.User;
import com.resturantservice.repos.TokenRepository;
import com.resturantservice.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users API", description = "Endpoints for managing Users and User Functionalities")
public class UserController extends ResponseController{
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private TokenRepository tokenRepository;
    
    @GetMapping(value="/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get User by username", description = "Retrieve details of a specific user by its username.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the user details."),
        @ApiResponse(responseCode = "404", description = "Customer not found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> getUser(@RequestHeader("Authorization") String authHeader,@PathVariable String username) {
        try {
        	userService.validateToken(authHeader);
        	User user= userService.getUser(username);
        	return handleResponse(user);
        }
    	catch(Exception ex) {
    		return handleError(ex);
    	}
    }

    // Register
    @PostMapping(value="/register",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a User", description = "Register the User Details.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created the user details."),
        @ApiResponse(responseCode = "400", description = "Invalid user data supplied."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<Object> register(@RequestBody User user) {
    	try {
         User userRegistered = userService.register(user);
         return handleResponse(userRegistered);
    	}
    	catch(Exception ex) {
    		return handleError(ex);
    	}
    }
    
    @PostMapping(value="/login",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Login a User", description = "Authenticate a User by username and password, Returns Bearer Token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully logged in the user."),
        @ApiResponse(responseCode = "400", description = "Invalid Credentials data supplied."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    
    public ResponseEntity<Object> login(@RequestHeader("Authorization") String authHeader) {
        try {
            // Decode Basic Auth header
            String base64Credentials = authHeader.substring("Basic ".length());
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] parts = credentials.split(":", 2);

            String username = parts[0];
            String password = parts[1];
            
            User user = userService.getUser(username);
            if(null==user) {
            	
            }
            else {
            	if(password.equalsIgnoreCase(user.getPassword())) {
                    String token = JwtUtil.generateToken(username);
                    Token tokenEntity = new Token();
                	tokenEntity.setToken(token);
                    tokenEntity.setUsername(username);
                    tokenEntity.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)); // 60 minutes
                    tokenRepository.save(tokenEntity);
                	return handleResponse(token);
            	}
            }
            


        } catch(Exception ex) {
    		return handleError(ex);
    	}
        return handleResponse("Invalid Credentials");
        
    }

  

}

