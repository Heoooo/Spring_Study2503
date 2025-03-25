package com.mysite.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	@Enumerated(EnumType.STRING)
	private MemberRole role;	//타입은 열거형 숫자 타입 => 보통 TINYINT
	//열거형 상수 사용 시 주의점
	//1. 데이터베이스 입력 값을 문자열로 할 경우 -> @Enumerated 에너테이션 사용 -> @Enumerated(EnumType.STRING)
	//2. 이 때 role 컬럼 타입은 CHARACTER Type
	//3. 그런데 기존 DB에 숫자 값으로 들어있다면? -> 500대 에러 발생 -> 공부할 때는 Drop table member; 해주고 사용하는 것을 권장
	//4. @Enumrated 에너테이션 사용하지 않으면 기본값은 EnumType.ORDINAL이므로, 문자열로 입력하고자 한다면 명시적으로 표기
	//5. 숫자로 입력되어 있는 경우 중간에 새로운 값을 추가하게 된다면 기존 값이 바뀌어서 표기되므로 주의
	//6. 이렇게 값이 바뀌는 경우로 인해 "문자열" 사용하는 경우도 많은 편
}
