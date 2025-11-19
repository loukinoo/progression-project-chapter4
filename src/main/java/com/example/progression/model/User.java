package com.example.progression.model;

public class User {

	private long id;
	private boolean isAdmin;
	private String name;
	
	public User() {
		
	}
	
	public User(long id, boolean isAdmin, String name) {
		super();
		this.id = id;
		this.isAdmin = isAdmin;
		this.name = name;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", isAdmin=" + isAdmin + ", name=" + name + "]";
	}
	
	
	
}
