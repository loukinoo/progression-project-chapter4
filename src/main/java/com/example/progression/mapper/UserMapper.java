package com.example.progression.mapper;

import org.springframework.stereotype.Component;

import com.example.progression.dto.UserDTO;
import com.example.progression.dto.UserToSaveDTO;
import com.example.progression.model.User;

@Component
public class UserMapper {

	public UserDTO modelToDto(User user) {
		return new UserDTO(user.getUsername(), user.getPassword());
	}
	
	public UserToSaveDTO dtoToToSaveDTO(UserDTO user) {
		// For testing purpose: users with "admin" in the username will be admin by default
		boolean isAdmin = user.getUsername().contains("admin");
		
		return new UserToSaveDTO(isAdmin, user.getUsername(), user.getPassword());
	}
	
}
