package com.mysite.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberCreateDTO {

	//Field
	private String id;
	private String pw;
	private String email;	
	
	
}
