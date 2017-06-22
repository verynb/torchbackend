package com.torch.domain.model.group.exception;

@SuppressWarnings("serial")
public class GroupidAlreadyExistException extends RuntimeException{
	
	private String groupid;
	
	public GroupidAlreadyExistException(String groupid){
		this.setGroupid(groupid);
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

}
