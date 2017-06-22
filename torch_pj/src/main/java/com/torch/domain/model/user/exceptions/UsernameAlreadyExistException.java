package com.torch.domain.model.user.exceptions;

@SuppressWarnings("serial")
public class UsernameAlreadyExistException extends RuntimeException{
	
	private String username;
	
	public UsernameAlreadyExistException(String username){
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
