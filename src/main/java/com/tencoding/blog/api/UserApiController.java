package com.tencoding.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tencoding.blog.dto.ResponseDto;
import com.tencoding.blog.model.User;
import com.tencoding.blog.service.UserService;

@RestController
public class UserApiController {
	
//	@Autowired
//	HttpSession httpSession; 이렇게 사용해도 됨.
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/auth/joinProc")
	// application/x-www-form-urlencoded;charset=UTF-8 // key-value
	public ResponseDto<Integer> save(User user) {
		
		int result = userService.saveUser(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) {
		userService.updateUser(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
		
	}
	
	
//	@PostMapping("/api/user")
//	public ResponseDto<Integer> save(@RequestBody User user) {
//		// DB (벨리데이션 체크) 
//		System.out.println("userApiController 호출됨");
//		user.setRole(RoleType.USER);
//		int result = userService.saveUser(user);
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), result);
//	}
//	
//	// /blog/api/user/login
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
//		System.out.println("login 호출됨.");
//		// 서비스한테 확인처리 해달라 요청
//		// 접근 주체
//		User principal = userService.login(user);
//		// 접근 주체가 정상적으로 username, password 확인 (세션이라는 거대한 메모리에 저장)
//		if(principal != null) {
//			session.setAttribute("principal", principal); // Jsession아이디 밑에 principal 키값으로 값이 저장됨.
//			System.out.println("세션 정보가 저장되었습니다.");
//		}
//		
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//	}
	
	
}
