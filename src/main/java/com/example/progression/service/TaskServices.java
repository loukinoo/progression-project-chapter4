package com.example.progression.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.progression.dto.TaskDTO;
import com.example.progression.exceptions.UnauthorizedException;
import com.example.progression.exceptions.UserNotFoundException;
import com.example.progression.model.Task;
import com.example.progression.model.User;
import com.example.progression.repository.JdbcTaskRepository;

@Service
public class TaskServices {

	@Autowired
	private JdbcTaskRepository repository;
	@Autowired
	private AuthServices authServices;
	@Autowired
	private UserServices userServices;
	
	//GET methods
	public List<Task> getAllTasks() {
		return repository.findAll();
	}
	
	public Task getTaskById(Long id) {
		 return repository.findById(id);
	}
	
	public List<Task> getOfUser(Long id) {
		return repository.findOfUser(id);
	}
	
	public List<Task> getOwn() {
		User currentUser = authServices.getLoggedInUser();
		return repository.findOfUser(currentUser.getId());
	}
	
	public List<Task> getCompleted() {
		return repository.findCompleted();
	}
	
	public List<Task> getAssigned() throws UnauthorizedException {
		User currentUser = authServices.getLoggedInUser();
		if (currentUser.isAdmin())
			return repository.findAssigned(true);
		throw new UnauthorizedException("Cannot access other users' data without being logged as admin");
	}
	
	public List<Task> getNotAssigned() throws UnauthorizedException {
		User currentUser = authServices.getLoggedInUser();
		try { 
			if (currentUser.isAdmin())
				return repository.findAssigned(false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		throw new UnauthorizedException("Cannot access other users' data without being logged as admin");
	}
	
	public int getCompletionRateOf(Long id, User currentUser) throws UnauthorizedException {
		if (!currentUser.isAdmin() && currentUser.getId() != id)
			throw new UnauthorizedException("Cannot access other users' data without being logged as admin");
		List<Task> assigned = getOfUser(id);
		if (assigned.isEmpty())
			return -1;
		int completed = 0;
		for (Task task : assigned) {
			if (task.isCompleted())
				completed++;
		}
		return 100*completed/assigned.size();
	}
	
	public int getCompletionRateOf(Long id) throws UnauthorizedException {
		User currentUser = authServices.getLoggedInUser();
		return getCompletionRateOf(id, currentUser);
	}
	
	public int getOwnCompletionRate() throws UnauthorizedException {
		User currentUser = authServices.getLoggedInUser();
		return getCompletionRateOf(currentUser.getId(), currentUser);
	}

	public int getGlobalCompletionRate() throws UnauthorizedException {
		User currentUser = authServices.getLoggedInUser();
		if (!currentUser.isAdmin())
			throw new UnauthorizedException("Cannot access global users' data without being logged as admin");
		List<Task> assigned = getAssigned();
		if (assigned.isEmpty())
			return -1;
		return 100*getCompleted().size()/assigned.size();
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
			System.out.println(e.getMessage());
			return -1;
		}
	}
	
	public int createTask(String assignment) {
		TaskDTO toCreate = new TaskDTO(null, false, false, assignment);
		return createTask(toCreate);
	}
	
	//PUT methods
	public int updateTask(Long id, TaskDTO input) {
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
	
	public int assignTaskTo(Long id, String username) throws UnauthorizedException, UserNotFoundException {
		User currentUser = authServices.getLoggedInUser();
		if (!currentUser.isAdmin())
			throw new UnauthorizedException("Cannot access global users' data without being logged as admin");
		Task toAssign = repository.findById(id);
		User assignee = userServices.getUserByUsername(username);
		if (assignee == null)
			throw new UserNotFoundException("Cannot find user with username: " + username);
		if (toAssign == null)
			return -1;
		toAssign.setAssigned(true);
		toAssign.setUserId(assignee.getId());
		repository.update(toAssign);
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
	
	public int deleteTask(Long id) {
		try {
			int result = repository.deleteById(id);
			return result;			
		} catch (Exception e) {
			return -1;
		}
	}


}
