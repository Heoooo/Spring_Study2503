package com.mysite.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.demo.dto.MemberCreateDTO;
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
	
	
	//Template Layout
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
	
	//Create
	@GetMapping("/member/create")
	public String create() {
		return "member/create";
	}
	
	
	//Create(DB 입력)
	@PostMapping("/member/create")
	public String insert(MemberCreateDTO memberCreateDTO, Model model) {
		
		//ID 중복 체크
		boolean IdCheckRes = memberService.checkIdDuplication(memberCreateDTO.getId());
		if (IdCheckRes) {
			model.addAttribute("msg", "ID 중복 에러 : 입력하신 ID 정보는 이미 가입되어 있습니다.");
			return "member/error";
		}
		
		//MemberService > insert() 호출
		memberService.insert(memberCreateDTO);
		
		//Return
		return "redirect:/member/list";
	}
	
	
	//Delete UI
	@GetMapping("/member/delete/{idx}")
	public String deleteUi(Model model, @PathVariable("idx") Integer idx) {
		
		//Model 데이터 추가
		model.addAttribute("idx", idx);
		
		//Return
		return "member/delete";
	}
	
	
	//Delete
	@PostMapping("/member/delete/{idx}")
	public String delete(@PathVariable("idx") Integer idx, @RequestParam("pw") String userPw) {
		
		//MemberService > delete() 호출
		String res = memberService.delete(idx, userPw);
		
		//Return
		return "redirect:"+res+"/"+idx;
	}
	
	
	//Delete::Fail
	@GetMapping("/member/delete-fail/{idx}")
	public String deleteFail(Model model, @PathVariable("idx") Integer idx) {
		model.addAttribute("idx", idx);
		return "member/delete-fail";
	}
	
	//Delete::Success
	@GetMapping("/member/delete-success/{idx}")
	public String deleteFail(@PathVariable("idx") Integer idx) {
		return "member/delete-success";
	}
	
	
	//Search
	@GetMapping("/member/search")
	public String searchList(Model model, @RequestParam("keyword") String keyword, @RequestParam(value="page", defaultValue="0") Integer page) {
		
		//Model 데이터 추가
		model.addAttribute("keyword", keyword);		
		
		//Return
		return "member/searchList";
	}
}
