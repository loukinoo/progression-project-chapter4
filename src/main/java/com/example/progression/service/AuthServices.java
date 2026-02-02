package com.example.progression.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.progression.model.User;
import com.example.progression.repository.UserRepository;

@Service
public class AuthServices {
	
	@Autowired
	private UserRepository userRepository;

	public String getLoggedInUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		
		return authentication.getName();
	}
	
	public User getLoggedInUser() {
		String username = getLoggedInUsername();
		return userRepository.findByUsername(username);
	}
}
