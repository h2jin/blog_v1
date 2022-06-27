package com.tencoding.blog.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tencoding.blog.model.User;

import lombok.Data;

@Data
public class PrincipalDetail implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user;
	

	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	/*
	 *  계정이 만료되지 않았는지 리턴한다.
	 *  true - 계정 만료되지 않음
	 *  false - 계정 만료된 유저
	 *  리턴값 true 하는 것 잊지 않기!
	 */ 
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 *  계정 잠김 여부 확인
	 *  true - 사용 가능
	 *  false - 사용 불가
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/*
	 * 비밀번호 만료 여부를 알려준다
	 * true - 비밀번호 사용 가능
	 * false - 사용 불가
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * 계정 활성화 여부 
	 * true - 사용 가능
	 * false - 사용 불가(로그인 불가)
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	/*
	 * 계정의 권한을 반환한다. 
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<GrantedAuthority>();
//		collectors.add(new GrantedAuthority() {
//			
//			@Override
//			public String getAuthority() {
//				// "ROLE_" 스프링 시큐리티 규칙 (롤 꼭 넣어줘야 한다.)
//				// "ROLE_User", "ROLE_ADMIN" 이러한 규칙을 만들어야 한다.
//				return "ROLE_" + user.getRole();
//			}
//		});
		//위의 표현식 람다표현식으로 바꿔줌
		collectors.add(() -> {
			return "ROLE_" + user.getRole();
		});
		return collectors;
	}

}
