package com.example.progression.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.progression.model.User;
import com.example.progression.repository.JdbcUserRepository;
import com.example.progression.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JdbcUserRepository repository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtil jwtUtils;
	
	@PostMapping("/signin")
	public String authenticateUser(@RequestBody User user) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						user.getUsername(), 
						user.getPassword())
				);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return jwtUtils.generateToken(userDetails.getUsername());
	}
}
