package com.tencoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@GetMapping("/login_form")
	public String loginForm() {
		
		return "user/login_form";
	}
	
	@GetMapping("/join_form")
	public String joinForm() {
		
		return "user/join_form";
	}
	
}
