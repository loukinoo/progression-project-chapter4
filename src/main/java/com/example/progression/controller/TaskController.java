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

import com.example.progression.dto.TaskDTO;
import com.example.progression.model.Task;
import com.example.progression.service.TaskServices;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	private final TaskServices taskServices;
	
	public TaskController(TaskServices taskServices) {
		this.taskServices = taskServices;
	}
	
	@GetMapping
	public ResponseEntity<List<Task>> getAllTasks() {
		return taskServices.getAllTasks();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Task> getOneTask(@PathVariable long id) {
		return taskServices.getTaskById(id);
	}
	
	@GetMapping("/user{id}")
	public ResponseEntity<List<Task>> getOfUser(@PathVariable long id) {
		return taskServices.getOfUser(id);
	}
	
	@GetMapping("/completed")
	public ResponseEntity<List<Task>> getCompleted() {
		return taskServices.getCompleted();
	}
	
	@GetMapping("/assigned")
	public ResponseEntity<List<Task>> getAssigned() {
		return taskServices.getAssigned();
	}
	
	@PostMapping
	public ResponseEntity<String> createTask(@RequestBody TaskDTO task) {
		return taskServices.createTask(task);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody TaskDTO task){
		return taskServices.updateTask(id, task);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteAllTasks() {
		return taskServices.deleteAllTasks();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable long id) {
		return taskServices.deleteTask(id);
	}

}
