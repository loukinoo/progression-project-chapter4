package com.example.progression.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.progression.dto.UserDTO;
import com.example.progression.model.User;
import com.example.progression.repository.UserRepository;

@Component
public class UserMapper {
	
	@Autowired
	private UserRepository repository;

	public UserDTO modelToDto(User user) {
		return new UserDTO(user.getUsername(), user.getPassword());
	}
	
	public User dtoToModel(UserDTO user) {
		User out = repository.findByUsername(user.getUsername());
		if (out != null)
			return out;
		
		boolean isAdmin = user.getUsername().contains("admin");
		return new User(isAdmin, user.getUsername(), user.getPassword());
	}
	
}
