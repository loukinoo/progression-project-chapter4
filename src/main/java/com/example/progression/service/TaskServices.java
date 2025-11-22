package com.example.progression.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.progression.dto.TaskDTO;
import com.example.progression.model.Task;
import com.example.progression.repository.JdbcTaskRepository;

public class TaskServices {

	@Autowired
	private JdbcTaskRepository repository;
	
	//GET methods
	public ResponseEntity<List<Task>> getAllTasks() {
		try {
			List<Task> tasks = repository.findAll();
			
			if (tasks.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(tasks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	public ResponseEntity<Task> getTaskById(long id) {
		Task task = repository.findById(id);
		
		if (task!=null)
			return new ResponseEntity<>(task, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<List<Task>> getOfUser(long id) {
		try {
			List<Task> tasks = repository.findOfUser(id);
			
			if (tasks.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(tasks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<List<Task>> getCompleted() {
		try {
			List<Task> tasks = repository.findCompleted();
			
			if (tasks.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(tasks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<List<Task>> getAssigned() {
		try {
			List<Task> tasks = repository.findAssigned();
			
			if (tasks.isEmpty())
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(tasks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//POST methods
	public ResponseEntity<String> createTask(TaskDTO task){
		try {
			repository.save(task);
			return new ResponseEntity<>("Task created succesfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//PUT methods
	public ResponseEntity<String> updateTask(long id, TaskDTO input) {
		Task toUpdate = repository.findById(id);
		
		if (toUpdate!=null) {
			toUpdate.setAssigned(input.isAssigned());
			toUpdate.setAssignment(input.getAssignment());
			toUpdate.setCompleted(input.isCompleted());
			toUpdate.setUserId(input.getUserId());
			repository.update(toUpdate);
			return new ResponseEntity<>("Task updated succesfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("ERROR: Cannot find task with id="+id, HttpStatus.NOT_FOUND);
	}
	
	//DELETE methods
	public ResponseEntity<String>deleteAllTasks() {
		try {
			int numRows = repository.deleteAll();
			return new ResponseEntity<>("Succesfully deleted "+numRows+" tasks.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Cannot delete tasks.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<String> deleteTask(long id) {
		try {
			int result = repository.deleteById(id);
			if (result == 0) {
				return new ResponseEntity<>("Cannot find task with id="+id, HttpStatus.OK);
			}
			return new ResponseEntity<>("Task succesfully deleted.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Cannot delete task.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
