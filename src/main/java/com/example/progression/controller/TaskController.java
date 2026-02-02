package com.example.progression.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.progression.exceptions.UnauthorizedException;
import com.example.progression.exceptions.UserNotFoundException;
import com.example.progression.model.Task;
import com.example.progression.service.TaskServices;

@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	private TaskServices taskServices;
	
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
	public ResponseEntity<Task> getOneTask(@PathVariable Long id) {
		Task task = taskServices.getTaskById(id);
		

		if (task!=null)
			return new ResponseEntity<>(task, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/user{id}")
	public ResponseEntity<List<Task>> getOfUser(@PathVariable Long id) {
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
	
	@GetMapping("/rate/{id}")
	public ResponseEntity<Map<String, Object>> getCompletionRateOf(@PathVariable Long id) {
		try {
			int rate = taskServices.getCompletionRateOf(id);
			return new ResponseEntity<>(Map.of("rate", rate), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Map.of("rate", -1), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/rate/own")
	public ResponseEntity<Map<String, Object>> getOwnCompletionRate() {
		try {
			int rate = taskServices.getOwnCompletionRate();
			return new ResponseEntity<>(Map.of("rate", rate), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Map.of("rate", -1), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/rate/global")
	public ResponseEntity<Map<String, Object>> getGlobalCompletionRate() {
		try {
			int rate = taskServices.getGlobalCompletionRate();	
			return new ResponseEntity<>(Map.of("rate", rate), HttpStatus.OK);
		} catch (UnauthorizedException e) {
			return new ResponseEntity<>(Map.of("rate", -1, "error", "Cannot access data without being admin"), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity<>(Map.of("rate", -1, "error", "Cannot access data"), HttpStatus.INTERNAL_SERVER_ERROR);
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
	
	@GetMapping("/not_assigned")
	public ResponseEntity<List<Task>> getNotAssigned() {
		try {
			List<Task> tasks = taskServices.getNotAssigned();
			
			if (tasks.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(tasks, HttpStatus.OK);
		} catch (UnauthorizedException e) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> createTask(@RequestBody String assignment) {
		int res = taskServices.createTask(assignment);
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
	
	@PutMapping("/assign/{id}")
	public ResponseEntity<Map<String, Object>> assignTaskTo(@PathVariable Long id, @RequestBody String username){
		try {
			int res = taskServices.assignTaskTo(id, username);
			if (res == 0)
				return new ResponseEntity<>(Map.of("message", "Task updated succesfully"), HttpStatus.CREATED);
			return new ResponseEntity<>(Map.of("error", "Cannot access task"), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (UnauthorizedException e) {
			return new ResponseEntity<>(Map.of("error", "Permission denied: not logged in as admin or user assigned to this task"), HttpStatus.UNAUTHORIZED);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteAllTasks() {
		taskServices.deleteAllTasks();
		return new ResponseEntity<>("Succesfully deleted all tasks.", HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable Long id) {
		taskServices.deleteTask(id);
		return new ResponseEntity<>("Task succesfully deleted.", HttpStatus.OK);
	}

}
