package com.mysite.demo.service;

import java.util.NoSuchElementException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.mysite.demo.dto.MemberCreateDTO;
import com.mysite.demo.entity.Member;
import com.mysite.demo.entity.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	//DB::List
	public Page<Member> list(int page) {
		
		//페이지 번호가 없으면
		if(page == 0) {
			page = 0;
		}
		else {
			page -= 1;
		}
		
		Pageable pageable = null;
		pageable = PageRequest.of(page, 3, Sort.by(Order.desc("idx")));
		
		return memberRepository.findAll(pageable);
	}

	
	//DB::Insert
	public Integer insert(MemberCreateDTO memberCreateDTO) {
		//1.객체 생성
		Member member = new Member();
		
		//비밀번호 암호화: BCrypt 알고리즘 사용(랜덤 솔트와 함께 사용, 60자리 암호화 문자열 생성)
		String hashedUserPw = BCrypt.hashpw(memberCreateDTO.getPw(), BCrypt.gensalt());
		
		//2.Setter + DTO Getter
		member.setId(memberCreateDTO.getId());
		member.setPw(hashedUserPw);
		member.setEmail(memberCreateDTO.getEmail());
		
		//3.Save
		memberRepository.save(member);
		
		//4.Return
		return member.getIdx();
	}

	
	//DB::ID Check
	public boolean checkIdDuplication(String id) {
		boolean IdCheckValue = memberRepository.existsById(id);
		return IdCheckValue;
	}
	
	
	//DB::Delete
	public String delete(Integer idx, String userPw) throws NoSuchElementException{
		
		//DB::Find
		Member member = memberRepository.findById(idx).orElseThrow();
		
		if(BCrypt.checkpw(userPw, member.getPw())) {
			memberRepository.delete(member);
			return "/member/delete-success";
		}
		else {
			return "/member/delete-fail";
		}
	}
	
	
	//DB::Search
	public Page<Member> searchList(String keyword, Integer page) {
		if (page == 0) {
			page = 0;
		}
		else {
			page -= 1;
		}
		
		//pageable 변수
		Pageable pageable = null;
		pageable = PageRequest.of(page, 3, Sort.by(Order.desc("idx")));
		
		return memberRepository.findById(keyword, pageable);
	}
}
