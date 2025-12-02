package com.example.progression.dto;

public class UserDTO {
	private String username;
	private String password;
	
	public UserDTO() {
		
	}

	public UserDTO(String username, String password) {
		super();
		this.password = password;
		this.username = username;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "UserDTO [password=" + password + ", username=" + username + "]";
	}
	
}
