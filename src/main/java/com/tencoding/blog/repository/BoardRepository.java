package com.tencoding.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tencoding.blog.model.Board;

// 어노테이션 안붙이는 이유 - 상속받은 JPARepository가 어노테이션 붙였기 때문에
public interface BoardRepository extends JpaRepository<Board, Integer>{
	
	// SELECT * FROM Board WHERE TITLE LIKE "%title%";
	Page<Board> findByTitleContaining(String title, Pageable pageable);
	
}
