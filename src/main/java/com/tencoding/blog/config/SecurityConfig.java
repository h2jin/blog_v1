package com.tencoding.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // 빈 등록 (IOC)
@EnableWebSecurity // security 필터로 등록이 된다. (필터를 커스텀하기 위해서 재정의하는 것)
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증 처리를 미리 체크하겠다. 
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// 1. 비밀번호 해시 처리
	@Bean // 먼저 메모리에 올려야 한다. IOC가 된다. (필요할 때 가져와서 사용하면 된다.)
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 2. 특정 주소 필터를 설정할 예정
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/auth/**", "/", "/js/**", "/css/**", "/images/**")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and().formLogin().loginPage("/auth/login_form");
		// 위에서 허용해준 주소 이외의 주소에 접근한 경우
		// 로그인이 안되어있는 상황에서(인증된 사용자가 아니면) 주소로 접근하는 경우 무조건 강제로 로그인 페이지로 가게 만드는 것.
	}
	
}
