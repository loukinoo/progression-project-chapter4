package com.example.progression.controller;

import java.util.List;

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
		return userServices.getAllUsers();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getOneUser(@PathVariable long id) {
		return userServices.getUserById(id);
	}
	
	@GetMapping("/admins")
	public ResponseEntity<List<User>> getAdmins() {
		return userServices.getAdmins();
	}
	
	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody UserDTO user) {
		return userServices.createUser(user);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDTO user){
		return userServices.updateUser(id, user);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteAllUsers() {
		return userServices.deleteAllUsers();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable long id) {
		return userServices.deleteUser(id);
	}

}
