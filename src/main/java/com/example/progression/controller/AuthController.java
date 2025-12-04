package com.example.progression.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.progression.dto.UserDTO;
import com.example.progression.security.JwtUtil;
import com.example.progression.service.UserServices;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserServices userServices;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtil jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody UserDTO user) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							user.getUsername(), 
							user.getPassword())
					);
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String token = jwtUtils.generateToken(userDetails.getUsername());
			return new ResponseEntity<>(Map.of("token", token, "message", "Logged in succesfully!"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Map.of("error", "Invalid username or password"), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping("/signup")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserDTO user) {
        if (userServices.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>(Map.of("error", "Username already in use"), HttpStatus.BAD_REQUEST);
        }
        // Create new user's account
        UserDTO newUser = new UserDTO(
                user.getUsername(),
                encoder.encode(user.getPassword())
        );
        userServices.createUser(newUser);
        return new ResponseEntity<>(Map.of("message", "User registered successfully!"), HttpStatus.OK);
    }
}
