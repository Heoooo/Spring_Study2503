package com.mysite.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.demo.entity.Member;
import com.mysite.demo.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	//DB:List
	@GetMapping("/member/list")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") Integer page) {
		
		//MemberService > list() 호출
		Page<Member> paging = memberService.list(page);
		
		//Model에 데이터 추가
		model.addAttribute("paging", paging);
		
		return "member/list";
	}
	
}
