package com.tencoding.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.blog.model.RoleType;
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
	
	// DI. 가지고옴 IOC로 저장해놓았기 때문에
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	//select
	//update
	//insert
	//하나로 묶어서 처리할 때
	@Transactional // 알아서 트렌젝션 처리됨
	public int saveUser(User user) {
		try {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			user.setPassword(encPassword);
			
			user.setRole(RoleType.USER);
			userRepository.save(user);
			
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	@Transactional
	public void updateUser(User user) {
		User userEntity = userRepository.findById(user.getId()).orElseThrow(() -> {
			return new IllegalArgumentException("회원정보가 없습니다.");
		});
		
		// 해시 암호화 처리
		String rawPassword = user.getPassword();
		String hashPassword = encoder.encode(rawPassword);
		userEntity.setPassword(hashPassword);
		userEntity.setEmail(user.getEmail());
		// 더티체킹 - transactional
	}
	
	@Transactional(readOnly = true)
	public User searchUser(String username) {
		User userEnctity = userRepository.findByUsername(username).orElseGet(() -> {
			return new User();
		});
		return userEnctity;
	}
	

//	@Transactional(readOnly = true)
//	public User login(User user) {
//		//repository에게 select 요청
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
	

}
