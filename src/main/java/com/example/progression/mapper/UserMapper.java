package com.example.progression.mapper;

import org.springframework.stereotype.Component;

import com.example.progression.dto.UserDTO;
import com.example.progression.model.User;

@Component
public class UserMapper {

	public UserDTO toDto(User user) {
		return new UserDTO(user.isAdmin(), user.getName());
	}
	
	public User toModelUser(UserDTO user) {
		return new User(user.isAdmin(), user.getName());
	}
}
