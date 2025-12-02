package com.example.progression.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.progression.dto.TaskDTO;
import com.example.progression.model.Task;

@Repository
public class JdbcTaskRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int save(TaskDTO task) {
		return jdbcTemplate.update("INSERT INTO tasks (user_id, completed, assigned, assignment)"
				+ " VALUES (?, ?, ?, ?)", 
				new Object[] {task.getUserId(), task.isCompleted(), task.isAssigned(), task.getAssignment()});
		}

	public int update(Task task) {
		return jdbcTemplate.update("UPDATE tasks SET user_id=?, completed=?, assigned=?, assignment=? WHERE task_id=?",
				new Object[] {task.getUserId(), task.isCompleted(), task.isAssigned(), task.getAssignment(), task.getTaskId()});
	}

	public Task findById(long taskId) {
		try {
			Task task = jdbcTemplate.queryForObject("SELECT * FROM tasks WHERE task_id=?", 
					BeanPropertyRowMapper.newInstance(Task.class), taskId);
			
			return task;
		} catch (IncorrectResultSizeDataAccessException e) {
			return null;
		}
	}

	public int deleteById(long taskId) {
		return jdbcTemplate.update("DELETE FROM tasks WHERE task_id=?", taskId);
	}

	public List<Task> findAll() {
		return jdbcTemplate.query("SELECT * FROM tasks", BeanPropertyRowMapper.newInstance(Task.class));
	}

	public List<Task> findOfUser(long userId) {
		return jdbcTemplate.query("SELECT * FROM tasks WHERE user_id=?", BeanPropertyRowMapper.newInstance(Task.class), userId);
	}

	public List<Task> findCompleted() {
		return jdbcTemplate.query("SELECT * FROM tasks WHERE completed=true", BeanPropertyRowMapper.newInstance(Task.class));
	}

	public List<Task> findAssigned() {
		return jdbcTemplate.query("SELECT * FROM tasks WHERE assigned=true", BeanPropertyRowMapper.newInstance(Task.class));
	}

	public int deleteAll() {
		return jdbcTemplate.update("DELETE FROM tasks");
	}

}
