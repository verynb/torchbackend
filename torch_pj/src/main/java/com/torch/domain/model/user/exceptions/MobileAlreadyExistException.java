package com.torch.domain.model.user.exceptions;

/**
 * 
 * 
 * @author service#yangle.org.cn
 * @date 2017年1月16日 下午2:13:28
 *
 */
@SuppressWarnings("serial")
public class MobileAlreadyExistException extends RuntimeException {

	private String mobile;

	public MobileAlreadyExistException(String mobile) {
		this.setMobile(mobile);
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
