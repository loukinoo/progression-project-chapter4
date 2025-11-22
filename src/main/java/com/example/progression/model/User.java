package com.example.progression.model;

public class User {

	private long id;
	private boolean is_admin;
	private String name;
	
	public User() {
		
	}
	
	public User(long id, boolean is_admin, String name) {
		super();
		this.id = id;
		this.is_admin = is_admin;
		this.name = name;
	}

	public User(boolean is_admin, String name) {
		super();
		this.is_admin = is_admin;
		this.name = name;
	}

	
	public boolean isAdmin() {
		return is_admin;
	}

	public void setAdmin(boolean is_admin) {
		this.is_admin = is_admin;
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
		return "User [id=" + id + ", is_admin=" + is_admin + ", name=" + name + "]";
	}
	
	
	
}
