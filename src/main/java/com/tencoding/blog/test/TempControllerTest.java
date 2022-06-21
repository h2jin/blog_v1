package com.tencoding.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
	
	// http://localhost:9090/blog/temp/home
	@GetMapping("/temp/home")
	// 파일 리턴 경로 : src/main/resource/static (기본)
	// 리턴명 : /home.html
	// 
	public String temphome() {
		System.out.println("tempHome()");
		return "/home.html"; // 슬러시를 꼭 넣어줘야지 찾을 수 있다.
	}
}
