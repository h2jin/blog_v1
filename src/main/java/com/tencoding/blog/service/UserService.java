package com.tencoding.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@Service // (IOC 등록)
public class UserService {
	
	/*
	 * 서비스가 필요한 이유
	 * 현실 모델링
	 * 
	 */
	
	
	// DB 접근해서 가지고 오려면 DI 의존주입 해야 함.
	@Autowired // 자동으로 초기화 까지 됨. 이거 하지 않으면 nullpoint 뜸.
	private UserRepository userRepository;
	//select
	//update
	//insert
	//하나로 묶어서 처리할 때
	@Transactional // 알아서 트렌젝션 처리됨
	public int saveUser(User user) {
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	

	@Transactional(readOnly = true)
	public User login(User user) {
		//repository에게 select 요청
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	

}