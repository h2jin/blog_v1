package com.tencoding.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.model.RoleType;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	// UserRepository 메모리에 올라가 있는 상태
	// 그럼 어떻게 가져올수 있는가? DI - 의존성 주임
	@Autowired // 자동으로 new 때려지는 것임. 래퍼런스 변수가 담김. 
	private UserRepository userRepository;
	
	// RestAPI 중 GET (X), POST 방식 써야함.
	@PostMapping("/dummy/join")
	public String join(@RequestBody User user) {
		System.out.println("==============================");
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
		System.out.println("==============================");
		
		System.out.println(user.getId());
		System.out.println(user.getCreateDate());
		System.out.println(user.getRole()); // null 이 들어가서 default값 불가
		
		user.setRole(RoleType.USER);
		
		userRepository.save(user);
		return "회원가입이 완료되었습니다";
	}
	
	

}
