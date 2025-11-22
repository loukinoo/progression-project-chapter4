package com.example.progression.model;

public class Task {
	
	private long task_id;
	private long user_id;
	private boolean completed;
	private boolean assigned;
	private String assignment;
	
	public Task() {
		
	}

	public Task(long task_id, long user_id, boolean completed, boolean assigned, String assignment) {
		super();
		this.task_id = task_id;
		this.user_id = user_id;
		this.completed = completed;
		this.assigned = assigned;
		this.assignment = assignment;
	}

	public Task(long user_id, boolean completed, boolean assigned, String assignment) {
		super();
		this.user_id = user_id;
		this.completed = completed;
		this.assigned = assigned;
		this.assignment = assignment;
	}
	
	public long getUserId() {
		return user_id;
	}

	public void setUserId(long user_id) {
		this.user_id = user_id;
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
		return task_id;
	}

	@Override
	public String toString() {
		return "Task [task_id=" + task_id + ", user_id=" + user_id + ", completed=" + completed + ", assigned=" + assigned
				+ ", assignment=" + assignment + "]";
	}
	
	

}
