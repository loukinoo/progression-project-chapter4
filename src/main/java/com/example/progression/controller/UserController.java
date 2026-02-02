package com.example.progression.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.progression.dto.UserDTO;
import com.example.progression.exceptions.UnauthorizedException;
import com.example.progression.model.User;
import com.example.progression.service.UserServices;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserServices userServices;

	public UserController(UserServices userServices) {
        this.userServices = userServices;
    }
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> users = userServices.getAllUsers();
			
			if (users.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (UnauthorizedException e) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getOneUser(@PathVariable Long id) {
		User user = userServices.getUserById(id);
		
		if (user!=null)
			return new ResponseEntity<>(user, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/admins")
	public ResponseEntity<List<User>> getAdmins() {
		try {
			List<User> users = userServices.getAdmins();
			
			if (users.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody UserDTO user) {
		int res =  userServices.createUser(user);
		if (res > -1) 
			return new ResponseEntity<>("User created succesfully", HttpStatus.CREATED);
		return new ResponseEntity<>("ERROR: Cannot create user", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping
	public ResponseEntity<String> updateUser(@RequestBody String pastUsername, @RequestBody UserDTO user){
		int res = userServices.updateUser(pastUsername, user);
		if (res > -1)
			return new ResponseEntity<>("User updated succesfully", HttpStatus.OK);
		return new ResponseEntity<>("ERROR: Cannot find user with id=\"+id", HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteAllUsers() {
		userServices.deleteAllUsers();	
		return new ResponseEntity<>("Succesfully deleted all users.", HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		userServices.deleteUser(id);
		return new ResponseEntity<>("User succesfully deleted.", HttpStatus.OK);
	}

}
