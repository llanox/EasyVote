package com.llanox.mobile.easyvote.model;

public class UserModel {
	public static final String GOOGLE_PLUS_USER_ROLE = "GooglePlusUser";
    public static final String REGISTERED_USER_ROLE = "RegisteredUser";


    private String id;
	private String username;
	private String password;
	private String fullName;
	private String email;	
	private String role;	
	private String objectId;
	
	

	public UserModel(String id, String username, String password, String fullName,
                     String email, String role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.email = email;
		this.role = role;
	}
	
	public UserModel() {
		
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

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	
	

}
