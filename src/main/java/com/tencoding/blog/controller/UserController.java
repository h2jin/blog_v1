package com.tencoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencoding.blog.dto.KakaoProfile;
import com.tencoding.blog.dto.KakaoProfile.KakaoAccount;
import com.tencoding.blog.dto.OAuthToken;
import com.tencoding.blog.model.User;
import com.tencoding.blog.service.UserService;

@Controller
public class UserController {
	
	@Value("${tenco.key}")
	private String tencoKey;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
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
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(@RequestParam String code) {
		// 다시 POST 보내야함
		// 통신을 인증서버로 어떻게 하는가
		// HTTPsURLConnection ..  
		// Retrofi2 
		// OKHttp
		// 스프링에서는 보통 RestTemplate 을 많이 쓴다.
		RestTemplate rt = new RestTemplate();
		// http 메세지 -> POST 방식
		// 시작줄
		// RestTemplate 클래스를 이용하여 http header / body 만들어서 던지면 통신 가능
		HttpHeaders headers = new HttpHeaders();
		// header 생성
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// body 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		// 어떤 값을 넣어야 할 까? 문서에서 필수 값 확인.
		params.add("grant_type", "authorization_code");
		params.add("client_id", "6e5416444d68dedcde4e87e02be81575");
		params.add("redirect_uri", "http://localhost:9090/auth/kakao/callback");
		params.add("code", code);
		
		// 헤더와 바디를 하나의 오브젝트로 담아야 한다.
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
		// Http 요청 - post 방식 - 응답
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class);
		
		// 파싱처리
		// response를 object 타입으로 변환 (gson, json simple, ObjectMapper)
		OAuthToken oAuthToken = null;
		ObjectMapper objectMapper = new ObjectMapper();
		// String --> Object (클래스 생성)
		try {
			oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		
		// 액세스 토큰 사용
		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		// 주의 Bearer 다음에 무조건 한 칸 띄우기
		headers2.add("Authorization", "Bearer " + oAuthToken.getAccessToken());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// 바디
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2); // 오버로딩..
		
		ResponseEntity<KakaoProfile> kakaoProfileResponse = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest, KakaoProfile.class);
		// response2 파싱 (단, 위에 했던 방식으로)
		// 소셜 로그인 처리 -->
		// 사용자가 로그인 처리했을 경우 최초사용자라면 회원가입 처리
		// 한번이라도 가입 진행이 된 사용자면 로그인 처리
		// 만약 회원가입 시 필요한 정보가 더 있어야 한다면 추가로 사용자에게 정보를 받아서 가입진행 처리를 해야한다.
		KakaoAccount account = kakaoProfileResponse.getBody().getKakaoAccount();
		System.out.println("카카오 아이디 : " + kakaoProfileResponse.getBody().getId());
		System.out.println("카카오 아이디 : " + account.getEmail());
		
		System.out.println("블로그에서 사용될 유저네임 : " + account.getEmail() + "_" + kakaoProfileResponse.getBody().getId());
		System.out.println("블로그에서 사용될 이메일 : " + account.getEmail());
		
		User kakaoUser = User.builder()
				.username(account.getEmail() + "_" + kakaoProfileResponse.getBody().getId())
				.password(tencoKey)
				.email(account.getEmail())
				.oauth("kakao")
				.build();
		
		System.out.println(kakaoUser);
		// UserService 호출해서 저장 진행
		// 단, 소셜 로그인 요청자가 이미 가입된 유저라면 저장(X)
		User originUser = userService.searchUser(kakaoUser.getUsername());
		if(originUser.getUsername() == null) {
			System.out.println("신규 회원이 아니기 때문에 회원가입을 진행");
			userService.saveUser(kakaoUser);
		}
		// 자동 로그인 처리 --> Security Session에 강제 저장.
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), tencoKey)); //인증 객체 만들어냄
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}
	

	
	

	
}
