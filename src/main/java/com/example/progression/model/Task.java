package com.example.progression.model;

public class Task {
	
	private long taskId;
	private long userId;
	private boolean completed;
	private boolean assigned;
	private String assignment;
	
	public Task() {
		
	}

	public Task(long taskId, long userId, boolean completed, boolean assigned, String assignment) {
		super();
		this.taskId = taskId;
		this.userId = userId;
		this.completed = completed;
		this.assigned = assigned;
		this.assignment = assignment;
	}

	public Task(long userId, boolean completed, boolean assigned, String assignment) {
		super();
		this.userId = userId;
		this.completed = completed;
		this.assigned = assigned;
		this.assignment = assignment;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}

	public String getAssignment() {
		return assignment;
	}

	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	public long getTaskId() {
		return taskId;
	}
	
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", userId=" + userId + ", completed=" + completed + ", assigned=" + assigned
				+ ", assignment=" + assignment + "]";
	}
	
	

}
