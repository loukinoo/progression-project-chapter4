package com.example.progression.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progression.dto.UserDTO;
import com.example.progression.model.User;
import com.example.progression.repository.JdbcUserRepository;

@Service
public class UserServices {
	
	@Autowired
	private JdbcUserRepository repository;

	//GET methods
	public List<User> getAllUsers() {
		return repository.findAll();
	}
	
	public User getUserById(long id) {
		return repository.findById(id);
	}
	
	public List<User> getAdmins() {	
		return repository.findByRole(true);
	}
	
	//POST methods
	public int createUser(UserDTO user){
		try {
			repository.save(user);
			return 0;
		} catch (Exception e) {
			return -1;
		}
	}
	
	//PUT methods
	public int updateUser(long id, UserDTO input) {
		User toUpdate = repository.findById(id);
		
		if (toUpdate!=null) {
			toUpdate.setAdmin(input.isAdmin());
			toUpdate.setName(input.getName());
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
