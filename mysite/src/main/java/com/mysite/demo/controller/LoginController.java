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

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/cookie")
//공통되는 URL은 @RequestMapping 사용
public class LoginController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping(value= {"", "/"})
	public String main(@CookieValue(name="memberId", required=false) String memberId, Model model) {
		
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
	public String loginPage(Model model) {
		
		//Model 데이터 추가
		model.addAttribute("loginType", "cookie");
		model.addAttribute("pageTitle", "Cookie Login");
		model.addAttribute("loginRequest", new LoginRequest());
		
		return "login/login";
	}


	//Login(Post)
	@PostMapping("/login")
	@ResponseBody
	public String login(
			@ModelAttribute LoginRequest loginRequest,
			BindingResult bindingResult,
			HttpServletResponse response,
			Model model
			) {
		
		model.addAttribute("loginType", "cookie");
		model.addAttribute("pageTitle", "Cookie Login");
		
		return String.valueOf(loginRequest.getId() + loginRequest.getPw());
	}
}
