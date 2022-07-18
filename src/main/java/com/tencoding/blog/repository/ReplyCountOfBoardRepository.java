package com.tencoding.blog.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import com.tencoding.blog.dto.ReplyCountOfBoardDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ReplyCountOfBoardRepository {
	
	// final을 쓰는게 기본. 변수명을 잘못쓰면 공유될 수 있음. final은 나중에 변경할 수 없는 값이고, 반드시 초기화해주어야 한다.
	// @RequiredArgsConstructor 자동으로 new 해준다.
	private final EntityManager em;
	
	public List<ReplyCountOfBoardDto> getReplyCount() {
		// 오브젝트를 가지고 있는 리스트를 만들 것임.
		List<ReplyCountOfBoardDto> list = new ArrayList<ReplyCountOfBoardDto>();
		// 1. 직접 쿼리문 만들기
		// 주의 ! 항상 맨 마지막에 한 칸 띄어줘야함. \r\n 지우고 한칸 띄어주기. ;도 지우고 한 칸 띄우기
		String queryStr = "SELECT A.id, A.content, (SELECT COUNT(boardId) "
				+ "			FROM reply AS B "
				+ "            WHERE B.boardId = A.id) AS replyCount "
				+ "            FROM board AS A ";
		
		// 질의어 생성함.
		Query nativeQuery = em.createNativeQuery(queryStr); // import 주의하기 javax.persistence.Query
		// 2가지 방식 존재 
		// 1 - 직접 문자열을 컨트롤해서 Object로 맵핑하는 방식 -> 귀찮고 까다로움
		// 2 - QLRM 라이브러리 사용하여 object로 맵핑하는 방식
		
		// 1 - 시간 날 때 맵핑 연습해보기
//		List<Object[]> resultList = nativeQuery.getResultList();
//		System.out.println(resultList.toString());
//		resultList.forEach(t -> {
//			System.out.println(t.toString());
//		});
		
		// 2. QLRM 라이브러리 사용
		JpaResultMapper jpaResultMapper = new JpaResultMapper();
		list = jpaResultMapper.list(nativeQuery, ReplyCountOfBoardDto.class); // 오류나는 이유 - 들어오는 데이터타입과 dto에 명시한 데이터 타입 다름.
		// 또한 어떤 키값에 어떤 데이터를 뽑아야 하는지 모르기 때문. 
		// replyCount가 bigInteger로 들어옴 (maria DB 는 Integer로 들어옴. 마리아 디비는 대소문자 구분있으므로 조심.)
		return list;
	}

}
