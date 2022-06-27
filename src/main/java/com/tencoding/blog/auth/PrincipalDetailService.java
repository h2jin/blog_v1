package com.tencoding.blog.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.UserRepository;

@Service
public class PrincipalDetailService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;

	// db에서 user가 있는지 없는지 확인하는 부분
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username).orElseThrow( () -> {
			return new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
		});
		// 리턴타입이 UserDetails 그래서 principalDetail 클래스 만들어줌. 생성자에 매개변수로 user 객체 담음. 
		return new PrincipalDetail(principal); // 시큐리티 세션에 유저 정보가 저장이 된다.
	}

}
