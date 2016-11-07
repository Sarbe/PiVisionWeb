package com.pivision.user.formbean;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginBean {

	
	private String userId;
	@NotEmpty
	private String emailId;
	@NotEmpty
	private String password;
	private String cnfrmPassword;
	private int roleId;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCnfrmPassword() {
		return cnfrmPassword;
	}

	public void setCnfrmPassword(String cnfrmPassword) {
		this.cnfrmPassword = cnfrmPassword;
	}

}
