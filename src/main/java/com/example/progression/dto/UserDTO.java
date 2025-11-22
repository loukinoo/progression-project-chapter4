package com.example.progression.dto;

public class UserDTO {
	private boolean admin;
	private String name;
	
	public UserDTO() {
		
	}

	public UserDTO(boolean admin, String name) {
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

	@Override
	public String toString() {
		return "UserDTO [admin=" + admin + ", name=" + name + "]";
	}
	
}
