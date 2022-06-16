package com.tencoding.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tencoding.blog.model.User;

// DAO 역할을 한다고 할 수 있다.
// IOC / Bean으로 등록될 수 있나요? -> 스프링에서 IOC에서 객체를 가지고 있나요?
public interface UserRepository extends JpaRepository<User, Integer>{
	// 제네릭 int 사용 못함. wrpper class인 Integer 사용.
	
}
