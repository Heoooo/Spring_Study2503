package com.mysite.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idx;
	
	@Column(length = 16)
	private String id;
	
	private String pw;
	
	@Column(unique = true)
	private String email;	
	
	@Column
	private MemberRole role;	//타입은 열거형 숫자 타입 => 보통 TINYINT
	
}
