package com.example.progression.repository;

import java.util.List;

import com.example.progression.dto.UserDTO;
import com.example.progression.model.User;

public interface IUserRepository {
	
	int save(UserDTO user);
	
	int update(User user);
	
	User findById(long id);
	
	int deleteById(long id);
	
	List<User> findAll();
	
	List<User> findByRole(boolean isAdmin);
	
	int deleteAll();

}
