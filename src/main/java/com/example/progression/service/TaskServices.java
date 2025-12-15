package com.example.progression.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.progression.dto.TaskDTO;
import com.example.progression.model.Task;
import com.example.progression.model.User;
import com.example.progression.repository.JdbcTaskRepository;

@Service
public class TaskServices {

	@Autowired
	private JdbcTaskRepository repository;
	@Autowired
	private AuthServices authServices;
	
	//GET methods
	public List<Task> getAllTasks() {
		return repository.findAll();
	}
	
	public Task getTaskById(long id) {
		 return repository.findById(id);
	}
	
	public List<Task> getOfUser(long id) {
		return repository.findOfUser(id);
	}
	
	public List<Task> getOwn() {
		User currentUser = authServices.getLoggedInUser();
		return repository.findOfUser(currentUser.getId());
	}
	
	public List<Task> getCompleted() {
		return repository.findCompleted();
	}
	
	public List<Task> getAssigned() {
		return repository.findAssigned();
	}

	//POST methods
	public int createTask(TaskDTO task){
		User currentUser = authServices.getLoggedInUser();
		if (!currentUser.isAdmin())
			return 401;
		try {
			repository.save(task);
			return 0;
		} catch (Exception e) {
			return -1;
		}
	}
	
	//PUT methods
	public int updateTask(long id, TaskDTO input) {
		Task toUpdate = repository.findById(id);
		
		if (toUpdate!=null) {
			toUpdate.setAssigned(input.isAssigned());
			toUpdate.setAssignment(input.getAssignment());
			toUpdate.setCompleted(input.isCompleted());
			toUpdate.setUserId(input.getUserId());
			repository.update(toUpdate);
			return 0;
		}
		return -1;
	}
	
	public int changeCompletionStatus(Long id) {
		Task toUpdate = repository.findById(id);
		if (toUpdate == null)
			return -1;
		User currentUser = authServices.getLoggedInUser();
		if (!currentUser.isAdmin() && currentUser.getId()!=toUpdate.getUserId())
			return 401;
		toUpdate.setCompleted(!toUpdate.isCompleted());
		repository.update(toUpdate);
		return 0;
	}
	
	//DELETE methods
	public int deleteAllTasks() {
		try {
			int numRows = repository.deleteAll();
			return numRows;
		} catch (Exception e) {
			return -1;
		}
	}
	
	public int deleteTask(long id) {
		try {
			int result = repository.deleteById(id);
			return result;			
		} catch (Exception e) {
			return -1;
		}
	}

}
