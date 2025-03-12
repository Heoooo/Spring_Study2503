package com.mysite.demo.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer>{
	
	//List
	public Page<Member> findAll(Pageable pageable);
}
