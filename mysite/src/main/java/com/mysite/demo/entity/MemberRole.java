package com.mysite.demo.entity;

public enum MemberRole {
	
	//열거 상수
	MEMBER,
	ADMIN;
	
	//데이터베이스 입력
	// -insert() 메소드에서 MemberRole.MEMBER로 사용하면 데이터베이스에 숫자 0으로 저장
	// -기본적으로 열거형이 순서 기반으로 작동 => 열거형 상수가 선언된 순서대로 숫자 값이 할당
	// -그런데 타입리프 뷰 페이지에서 출력하면 MEMBER 이런식으로 문자열로 출력
	// -뷰 페이지에서 DB에 입력된 열거형 숫자로 출력하려면 => <td th:text="${member.role.ordinal}"></td>
}
