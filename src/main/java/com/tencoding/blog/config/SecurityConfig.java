package com.tencoding.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.tencoding.blog.auth.PrincipalDetailService;

@Configuration // 빈 등록 (IOC)
@EnableWebSecurity // security 필터로 등록이 된다. (필터를 커스텀하기 위해서 재정의하는 것)
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증 처리를 미리 체크하겠다. 
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// 1. 비밀번호 해시 처리
	@Bean // 먼저 메모리에 올려야 한다. IOC가 된다. (필요할 때 가져와서 사용하면 된다.)
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	// 2. 특정 주소 필터를 설정할 예정
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		 http.csrf().disable()
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
	.and()
		.authorizeRequests()
		.antMatchers("/auth/**", "/", "/js/**", "/css/**", "/image/**", "/dummy/**", "/test/**")
		.permitAll()
		.anyRequest()
		.authenticated()
	.and()
		.formLogin().loginPage("/auth/login_form")
		.loginProcessingUrl("/auth/loginProc")
		.defaultSuccessUrl("/");
		// 위에서 허용해준 주소 이외의 주소에 접근한 경우
		// 로그인이 안되어있는 상황에서(인증된 사용자가 아니면) 주소로 접근하는 경우 무조건 강제로 로그인 페이지로 가게 만드는 것.
		
		// 스프링 시큐리티가 해당 주소로 요청이 오면 가로채서 대신 로그인 처리를 해준다.
		// 단 이 동작/로직을 완료하기 위해서는 만들어야할 클래스가 있다.
		// UserDetails type의 Object를 만들어야 한다.
	}
	
	// 3. 시큐리티가 대신 로그인을 해주는데, password를 가로채서 해당 password가 무엇으로 
	// 해시처리 되었는지 함수를 알려줘야 한다. 같은 해시로 암호화해서 DB에 있는 해시 값과 비교할 수 있다.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 1. userDetailService에 들어갈 Object를 만들어주어야 한다.
		// 2. PasswordEncoder 에 우리가 사용하는 해시 함수를 알려줘야한다.
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD()); // 서비스가 db에 접근하여 사용자가 입력한 username과 맞는지 확인하는 과정
	}
	
	
}
