package com.tencoding.blog.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncTest {
	
	@Test
	public void hashEncode() {
		String encPassword = new BCryptPasswordEncoder().encode("123");
		System.out.println( "해시 값 : " + encPassword);
		
		// 원래 해시는 값이 똑같아야 하는데 스프링에서는 강력한 해시암호화를 사용하는지 다르다.
		// $2a$10$ejVgwbYRzjuGhH0j11Y7rujEBiD8MHR5Fqk4ML0zfWRcHdIkbmqoi
		// $2a$10$7U6dMoueGdAYd2WtLA3J6eZNJc4erXKQ0/XYeD1y8qmYy1jpaKH6S
	}

}
