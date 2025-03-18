package com.mysite.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.demo.service.MemberService;

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
		
		
		return "login/main";
	}
}
