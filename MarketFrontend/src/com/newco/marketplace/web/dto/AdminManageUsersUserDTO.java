package com.newco.marketplace.web.dto;

public class AdminManageUsersUserDTO extends SerializedBaseDTO
{
	private String name;
	private String role;
	private String title;
	private String username;
	private int resourceId;
	
	public int getResourceId() {
		return resourceId;
	}


	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}


	// For testing
	public void init(String username, String name, String role, String title)
	{
		this.username = username;
		this.name = name;
		this.role = role;
		this.title = title;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
	
}
