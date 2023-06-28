package com.angga.springbootjwtmysqlsimple.api.payloads.response;

public class AuthenticateResponse {
	
	private String jwt;
	private String success;

	public AuthenticateResponse() {
		super();
	}
	
	public AuthenticateResponse(String jwt, String success) {
		this.jwt = jwt;
		this.success = success;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	
}
