package com.tencoding.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@Configuration // IOC에 이녀석이 메모리에 뜸. 메서드임. 리턴타입 오브젝트로 존재. 메서드를 수행시킬 때 힙영역에 올리려 할 때 Bean 사용. 
public class WebConfig implements WebMvcConfigurer{
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		// file:///C:/springimageDir/tencoblog/upload/9aa79a99-0178-47e6-bc30-20bdf4b5c76d_image1.jpg
		
		registry.addResourceHandler("/upload/**") // URL 패턴 - /upload/ --> 낚아 챔
		.addResourceLocations("file:///" + uploadFolder) // 실제 물리적인 경로
		.setCachePeriod(60 * 10 * 6) // 캐시의 지속시간 (초) - 1시간
		.resourceChain(true) // 리소스 찾는 것을 최적화하기 위해서 
		.addResolver(new PathResourceResolver()); 
		
	}
	
	// lucy 라이브러리 한계 있음. json 못막음. form 태그에서 잘 사용할 수 있음. 
	// ajax를 통해 데이터를 전송하게되면 먹히지 않음. 한계 존재
	
	@Bean // 이녀석을 사용할 때 Bean 등록하라, 
	public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean () {
		FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new XssEscapeServletFilter());
		filterRegistrationBean.setOrder(1);
		filterRegistrationBean.addUrlPatterns("/*"); // 어떤 도메인 url에 적용할 건지 설정
		return filterRegistrationBean;
	} // 필터를 자바코드로 추가함.
	
	
	
	
}
