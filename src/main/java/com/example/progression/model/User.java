package com.example.progression.model;

public class User {

	private Long id;
	private boolean admin;
	private String username;
	private String password;
	
	public User() {
		
	}
	
	public User(Long id, boolean admin, String username, String password) {
		super();
		this.id = id;
		this.admin = admin;
		this.username = username;
		this.password = password;
	}

	
	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", admin=" + admin + ", username=" + username + ", password=" + password + "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
