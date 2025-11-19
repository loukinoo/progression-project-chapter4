package com.example.progression.repository;

import java.util.List;

import com.example.progression.model.Task;

public interface ITaskRepository {
	
	int save(Task task);
	
	int update(Task task);
	
	Task findById(long taskId);
	
	int deleteById(long taskId);
	
	List<Task> findAll();
	
	List<Task> findOfUser(long userId); 
	
	List<Task> findCompleted();
	
	List<Task> findAssigned();
	
	int deleteAll();


}
