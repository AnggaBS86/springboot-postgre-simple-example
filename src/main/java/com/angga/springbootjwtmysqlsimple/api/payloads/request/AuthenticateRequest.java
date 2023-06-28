package com.angga.springbootjwtmysqlsimple.api.payloads.request;

public class AuthenticateRequest {
		private String phone;
		private String password;
		
		
		public AuthenticateRequest() {
		}

		public AuthenticateRequest(String phone, String pPassword) {
			this.phone = phone;
			this.password = pPassword;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
}
