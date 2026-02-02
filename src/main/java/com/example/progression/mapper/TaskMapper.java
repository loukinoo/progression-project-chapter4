package com.example.progression.mapper;

import org.springframework.stereotype.Component;

import com.example.progression.dto.TaskDTO;
import com.example.progression.model.Task;

@Component
public class TaskMapper {

	public TaskDTO toDto(Task task) {
		return new TaskDTO(task.getUserId(), task.isCompleted(), task.isAssigned(), task.getAssignment());
	}
	
	public Task dtoToModel(TaskDTO task) {
		return new Task(task.getUserId(), task.isCompleted(), task.isAssigned(), task.getAssignment());
	}
}
