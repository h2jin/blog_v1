package com.tencoding.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tencoding.blog.model.User;

// DAO 역할을 한다고 할 수 있다.
// IOC / Bean으로 등록될 수 있나요? -> 스프링에서 IOC에서 객체를 가지고 있나요?
public interface UserRepository extends JpaRepository<User, Integer>{
	// 제네릭 int 사용 못함. wrpper class인 Integer 사용.
	
	// spring jpa 네이밍 전략
	// select * from user where username = ?1 and password = ?2;
	// 엄격한 기준 지켜서 함수를 만들면 쿼리 만들어줌. 칼럼명은 대문자로 시작
//	User findByUsernameAndPassword(String username, String password);
	// User-테이블
	
	// 두번째 방법
//	@Query(value = "select * from user where username = ?1 and password = ?2;")
//	User login();
	
	// SELECT * FROM user WHERE username = 1?; (명명규칙 잘 지키기)
	Optional<User> findByUsername(String username);
}
