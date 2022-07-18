package com.tencoding.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tencoding.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	
	// 기본 데이터까지는 그냥 가능
	// 그러나 오브젝트로 받으려면 RS.getint / RS.getString 등으로 하나씩 받아야 한다.
	// 굉장히 반복적인 코드. 이를 쉽게하려면 QLRM 이용할 수 있다.
		
}
