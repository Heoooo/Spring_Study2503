package com.mysite.demo.entity;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer>{
	
	//List
	public Page<Member> findAll(Pageable pageable);
	
	//ID Check
	public boolean existsById(String id);
	
	//Search
	public Page<Member> findById(String id, Pageable pageable);
	
	//Login
	public Optional<Member> findById(String id);
}
