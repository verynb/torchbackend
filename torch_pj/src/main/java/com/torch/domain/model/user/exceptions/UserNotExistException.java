package com.torch.domain.model.user.exceptions;

@SuppressWarnings("serial")
public class UserNotExistException extends RuntimeException {

	private Long userid;

	public UserNotExistException(Long userid) {
		this.setUserid(userid);
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

}
