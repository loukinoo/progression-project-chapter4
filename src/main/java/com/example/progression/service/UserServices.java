package com.example.progression.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.progression.dto.UserDTO;
import com.example.progression.dto.UserToSaveDTO;
import com.example.progression.exceptions.UnauthorizedException;
import com.example.progression.mapper.UserMapper;
import com.example.progression.model.User;
import com.example.progression.repository.UserRepository;

@Service
public class UserServices {
	
	@Autowired
	private UserRepository repository;
	@Autowired
	private UserMapper mapper;
	@Autowired
	private AuthServices authServices;

	//GET methods
	public List<User> getAllUsers() throws UnauthorizedException {
		User currentUser = authServices.getLoggedInUser();
		if (currentUser.isAdmin())
			return repository.findAll();
		throw new UnauthorizedException("Cannot access users' data without being logged as admin");
	}
	
	public User getUserById(Long id) {
		Optional<User> out = repository.findById(id);
		if (out.isEmpty())
			return null;
		return out.get();
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
	public void deleteAllUsers() {
		repository.deleteAll();		
	}
	
	public void deleteUser(Long id) {
		repository.deleteById(id);
	}
}
