package com.example.progression.model;

public class User {

	private long id;
	private boolean admin;
	private String name;
	
	public User() {
		
	}
	
	public User(long id, boolean admin, String name) {
		super();
		this.id = id;
		this.admin = admin;
		this.name = name;
	}

	public User(boolean admin, String name) {
		super();
		this.admin = admin;
		this.name = name;
	}

	
	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
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
	
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", admin=" + admin + ", name=" + name + "]";
	}
	
	
	
}
