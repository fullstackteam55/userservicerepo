//package com.resturantservice.services;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.resturantservice.repos.UserRepository;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    	com.resturantservice.entities.User userEntity = userRepository.findByUsername(username);
//                
//
//        return User.builder()
//                .username(userEntity.getUsername())
//                .password(userEntity.getPassword()) // Password should be encoded
//                .roles(userEntity.getRole().name()) // Assign roles
//                .build();
//    }
//}
//
//
//
//
