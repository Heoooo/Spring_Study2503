package com.mysite.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysite.demo.dto.LoginRequest;
import com.mysite.demo.entity.Member;
import com.mysite.demo.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/cookie")
//공통되는 URL은 @RequestMapping 사용
public class LoginController {
	
	@Autowired
	private MemberService memberService;
	
	//Main
	@GetMapping(value= {"", "/"})
	public String main(@CookieValue(name="memberId", required=false) String memberId, Model model) {
		//required
		//1. 쿠키 필수 여부 설정, 기본은 true
		//2. 쿠키 값이 없는 경우는 null 값이 memberId 변수에 들어감
		//3. primitive 원시 타입을 사용하는 경우 500에러 발생 가능성, 가능하면 Reference Type 사용
		
		
		//모델에 데이터 추가
		model.addAttribute("loginType", "cookie");
		model.addAttribute("pageTitle", "Cookie Login");
		
		//getLoginMemberById
		Member loginMember = memberService.getLoginMemberById(memberId);
		
		//loginMember null 값이 아니면
		if(loginMember != null) {
			model.addAttribute("tempId", loginMember.getId());
		}
		
		return "login/main";
	}

	
	//Login(Get)
	@GetMapping("/login")
	public String loginPage(@CookieValue(name="memberId", required=false) String memberId, Model model) {
		
		//Model 데이터 추가
		model.addAttribute("loginType", "cookie");
		model.addAttribute("pageTitle", "Cookie Login");
		//model.addAttribute("loginRequest", new LoginRequest());
		
		//브라우저에 쿠키 값이 존재하면 해당 객체 가져와서 뷰로 전달
		//먼저 해당 메소드 파라미터로 @CookieValue() 사용
		//단, 세션을 같이 사용 시 적용하는 것이 좋고 쿠키만으로 할 때는 비권장
		Member member = memberService.getLoginMemberById(memberId);
		
		//memberId 쿠키 값이 DB에 존재한다면
		if(member != null) {
			model.addAttribute("loginRequest", member);
		}
		else {
			model.addAttribute("loginRequest", new LoginRequest());
		}
		
		//데이터베이스 검색하지 않고 쿠키 여부에 따라 조건 처리하는 경우
		/*
		LoginRequest loginRequest = new LoginRequest();
		if (memberId != null) {
			loginRequest.setId(memberId);
			model.addAttribute("loginRequest", loginRequest);
		}
		else {
			model.addAttribute("loginRequest", loginRequest);
		}		
		*/
		
		return "login/login";
	}


	//Login(Post)
	@PostMapping("/login")
	public String login(
			@ModelAttribute LoginRequest loginRequest,
			BindingResult bindingResult,
			HttpServletResponse response,
			Model model
			) {
		//Model 데이터 추가
		model.addAttribute("loginType", "cookie");
		model.addAttribute("pageTitle", "Cookie Login");
		
		//MemberService > login() 메소드 호출
		Member member = memberService.login(loginRequest);
		
		//로그인 아이디 혹은 비밀번호 틀린 경우
		if (member == null) {
			bindingResult.reject("loginFail", "회원님이 입력하신 ID 정보가 DB에 없거나 또는 Password 정보가 올바르지 않습니다.");
		}
		
		//뷰로 에러 메시지 출력
		if (bindingResult.hasErrors()) {
			return "login/login";
		}
		
		//로그인 성공 => 쿠키 생성
		Cookie cookie = new Cookie("memberId", String.valueOf(member.getId()));
		cookie.setMaxAge(60);	//쿠키 만료 시간 초 단위로 설정
		response.addCookie(cookie);
		
		return "redirect:/cookie";
	
	}
	
	//Logout
	@GetMapping("/logout")
	public String logout(HttpServletResponse res, Model model) {
		
		//Model 데이터 추가
		model.addAttribute("loginType", "cookie");
		model.addAttribute("pageTitle", "Logout Page");
		
		//Cookie 제거
		Cookie cookie = new Cookie("memberId", null);
		cookie.setMaxAge(0);
		res.addCookie(cookie);
		
		return "redirect:/cookie";
	}
	
	
	//MyPage
	@GetMapping("/mypage")
	public String mypage(@CookieValue(name = "memberId", required = false) String memberId, Model model) {
		
		//Model 데이터 추가
		model.addAttribute("loginType", "cookie");
		model.addAttribute("pageTitle", "My Page");
		
		//쿠키 정보가 브라우저에 존재한다면
		Member loginMember = memberService.getLoginMemberById(memberId);
		
		//로그인 정보가 없다면 로그인 페이지로 이동
		if (loginMember == null) {
			return "redirect:/cookie/login";
		}
		
		//로그인 정보가 있다면 해당 뷰로 이동
		model.addAttribute("member", loginMember);
		
		return "login/mypage";
	}
}
