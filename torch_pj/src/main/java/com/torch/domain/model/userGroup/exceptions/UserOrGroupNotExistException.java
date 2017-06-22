package com.torch.domain.model.userGroup.exceptions;

@SuppressWarnings("serial")
public class UserOrGroupNotExistException extends RuntimeException {

	private String username;
	
	private String groupid;
	
	public UserOrGroupNotExistException(String username, String groupid) {
		this.username = username;
		this.groupid = groupid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}


}
