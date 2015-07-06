package com.llanox.mobile.easyvote.model;

import java.util.Date;

public class QuestionModel {
	
	private String objectId;
	private String key;
	private UserModel creator;
    private String content;
    private String[] answers;
    private Date created;
    private Date updated;
	
	public boolean active;
	
	

	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public String[] getAnswers() {
		return answers;
	}
	public void setAnswers(String[] answers) {
		this.answers = answers;
	}
	public UserModel getCreator() {
		return creator;
	}
	public void setCreator(UserModel creator) {
		this.creator = creator;
	}

}
