package com.mysite.demo.dto;

public class LoginRequest {
	
	//Field
	private String id;
	private String pw;
	
	//Constructor
	public LoginRequest() {}	//@NoArgsConstructor
	
	//Setter and Getter
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
}
