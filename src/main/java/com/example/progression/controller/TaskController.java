package com.example.progression.controller;

import java.util.List;
import java.util.Map;

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
		try {
			List<Task> tasks = taskServices.getAllTasks();
			
			if (tasks.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(tasks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Task> getOneTask(@PathVariable long id) {
		Task task = taskServices.getTaskById(id);
		

		if (task!=null)
			return new ResponseEntity<>(task, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/user{id}")
	public ResponseEntity<List<Task>> getOfUser(@PathVariable long id) {
		try {
			List<Task> tasks = taskServices.getOfUser(id);
			
			if (tasks.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(tasks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/own")
	public ResponseEntity<List<Task>> getOwnTasks() {
		try {
			List<Task> tasks = taskServices.getOwn();
			
			if (tasks.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(tasks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/completed")
	public ResponseEntity<List<Task>> getCompleted() {
		try {
			List<Task> tasks = taskServices.getCompleted();
			
			if (tasks.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(tasks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/assigned")
	public ResponseEntity<List<Task>> getAssigned() {
		try {
			List<Task> tasks = taskServices.getAssigned();
			
			if (tasks.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(tasks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> createTask(@RequestBody TaskDTO task) {
		int res = taskServices.createTask(task);
		if (res == 0)
			return new ResponseEntity<>(Map.of("message", "Task created succesfully"), HttpStatus.CREATED);
		if (res == 401)
			return new ResponseEntity<>(Map.of("error", "Permission denied: not logged in as admin"), HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>(Map.of("error", "Cannot create task"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody TaskDTO task){
		int res = taskServices.updateTask(id, task);
		if (res > -1)
			return new ResponseEntity<>("Task updated succesfully", HttpStatus.OK);
		return new ResponseEntity<>("ERROR: Cannot find task with id="+id, HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/complete/{id}")
	public ResponseEntity<Map<String, Object>> changeCompletionStatus(@PathVariable Long id){
		int res = taskServices.changeCompletionStatus(id);
		if (res == 0)
			return new ResponseEntity<>(Map.of("message", "Task updated succesfully"), HttpStatus.CREATED);
		if (res == 401)
			return new ResponseEntity<>(Map.of("error", "Permission denied: not logged in as admin or user assigned to this task"), HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>(Map.of("error", "Cannot access task"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteAllTasks() {
		int res = taskServices.deleteAllTasks();
		if (res > -1)
			return new ResponseEntity<>("Succesfully deleted "+res+" tasks.", HttpStatus.OK);
		return new ResponseEntity<>("Cannot delete tasks.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable long id) {
		int res = taskServices.deleteTask(id);
		if (res > 0)
			return new ResponseEntity<>("Task succesfully deleted.", HttpStatus.OK);
		if (res == 0)
			return new ResponseEntity<>("Cannot find task with id="+id, HttpStatus.OK);
		return new ResponseEntity<>("Cannot delete task.", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
