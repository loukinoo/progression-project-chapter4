package com.example.progression.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progression.dto.UserDTO;
import com.example.progression.dto.UserToSaveDTO;
import com.example.progression.mapper.UserMapper;
import com.example.progression.model.User;
import com.example.progression.repository.JdbcUserRepository;

@Service
public class UserServices {
	
	@Autowired
	private JdbcUserRepository repository;
	@Autowired
	private UserMapper mapper;

	//GET methods
	public List<User> getAllUsers() {
		return repository.findAll();
	}
	
	public User getUserById(long id) {
		return repository.findById(id);
	}
	
	public User getUserByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public List<User> getAdmins() {	
		return repository.findByRole(true);
	}
	
	public boolean existsByUsername(String username) {
		return repository.findByUsername(username) != null;
	}
	
	//POST methods
	public int createUser(UserDTO user){
		try {
			UserToSaveDTO toSave = mapper.dtoToToSaveDTO(user);
			repository.save(toSave);
			return 0;
		} catch (Exception e) {
			return -1;
		}
	}
	
	//PUT methods
	public int updateUser(String pastUsername, UserDTO input) {
		User toUpdate = repository.findByUsername(pastUsername);
		
		if (toUpdate!=null) {
			toUpdate.setUsername(input.getUsername());
			toUpdate.setPassword(input.getPassword());
			repository.update(toUpdate);
			return 0;
		}
		return -1;
	}
	
	public int changeRole(UserDTO user) {
		User toUpdate = repository.findByUsername(user.getUsername());
		
		if (toUpdate!=null) {
			toUpdate.setAdmin(!toUpdate.isAdmin());
			repository.update(toUpdate);
			return 0;
		}
		return -1;
	}
	
	//DELETE methods
	public int deleteAllUsers() {
		try {
			int numRows = repository.deleteAll();
			return numRows;
		} catch (Exception e) {
			return -1;
		}
	}
	
	public int deleteUser(long id) {
		try {
			int result = repository.deleteById(id);
			return result;
		} catch (Exception e) {
			return -1;
		}
	}

}
