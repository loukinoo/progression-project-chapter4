package com.example.progression.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.progression.model.User;
import com.example.progression.repository.JdbcUserRepository;

@Service
public class UserServices {
	
	@Autowired
	private JdbcUserRepository repository;

	//GET methods
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> users = repository.findAll();
			
			if (users.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	public ResponseEntity<User> getUserById(long id) {
		User user = repository.findById(id);
		
		if (user!=null)
			return new ResponseEntity<>(user, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<List<User>> getAdmins() {
		try {
			List<User> users = repository.findByRole(true);
			
			if (users.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//POST methods
	public ResponseEntity<String> createUser(User user){
		try {
			repository.save(new User(user.isAdmin(), user.getName()));
			return new ResponseEntity<>("User created succesfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//PUT methods
	public ResponseEntity<String> updateUser(long id, User input) {
		User toUpdate = repository.findById(id);
		
		if (toUpdate!=null) {
			toUpdate.setAdmin(input.isAdmin());
			toUpdate.setName(input.getName());
			repository.update(toUpdate);
			return new ResponseEntity<>("User updated succesfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("ERROR: Cannot find user with id="+id, HttpStatus.NOT_FOUND);
	}
	
	//DELETE methods
	public ResponseEntity<String>deleteAllUsers() {
		try {
			int numRows = repository.deleteAll();
			return new ResponseEntity<>("Succesfully deleted "+numRows+" users.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Cannot delete users.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<String> deleteUser(long id) {
		try {
			int result = repository.deleteById(id);
			if (result == 0) {
				return new ResponseEntity<>("Cannot find user with id="+id, HttpStatus.OK);
			}
			return new ResponseEntity<>("User succesfully deleted.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Cannot delete user.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
