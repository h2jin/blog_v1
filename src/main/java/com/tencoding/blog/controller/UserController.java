package com.tencoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tencoding.blog.dto.ResponseDto;
import com.tencoding.blog.model.User;
import com.tencoding.blog.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/auth/login_form")
	public String loginForm() {
		return "user/login_form";
	}
	
	@GetMapping("/auth/join_form")
	public String joinForm() {
		
		return "user/join_form";
	}
	
	@GetMapping("/logout")
	public String logout() {
		// 세션 정보를 제거, 로그아웃 처리
		httpSession.invalidate();
		return "redirect:/"; // 처음화면으로 돌아감.
	}
	
	@GetMapping("/user/update_form")
	public String updateForm() {
		return "user/update_form";
	}
	
	// application/x-www-form-urlencoded;charset=UTF-8 // key-value
	@PostMapping("/auth/joinProc")
	public String save(User user) {
		int result = userService.saveUser(user);
		return "redirect:/";
	}
	

	
}
