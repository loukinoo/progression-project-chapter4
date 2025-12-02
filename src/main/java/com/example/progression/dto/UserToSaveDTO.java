package com.example.progression.dto;

public class UserToSaveDTO {

	private boolean admin;
	private String username;
	private String password;
	
	public UserToSaveDTO() {}
	
	public UserToSaveDTO(boolean admin, String username, String password) {
		super();
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

	@Override
	public String toString() {
		return "UserToSave [admin=" + admin + ", username=" + username + ", password=" + password + "]";
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
