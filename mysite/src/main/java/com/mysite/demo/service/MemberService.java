package com.mysite.demo.service;

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
		//2.Setter + DTO Getter
		member.setId(memberCreateDTO.getId());
		member.setPw(memberCreateDTO.getPw());
		member.setEmail(memberCreateDTO.getEmail());
		//3.Save
		memberRepository.save(member);
		//4.Return
		return member.getIdx();
	}
}
