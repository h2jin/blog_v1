package com.tencoding.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // User 클래스가 자동으로 mysql에 테이블을 생성한다.
public class User {
	@Id // Primary key 지정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB의 넘버링 전략을 따라가겠다.
	private int id;
	@Column(nullable = false, length = 30)
	private String username;
	@Column(nullable = false, length = 100)
	private String password;
	@Column(nullable = false, length = 50)
	private String email;
	@CreationTimestamp // 시간이 자동으로 입력된다.
	private Timestamp createDate;

	// domain - 데이터의 범주화 (string 으로 하는 경우에, 어떠한 문자열 다 가능. 이넘으로 만들어서 설정하면 범주가 결정되어
	// 오류를 감소시킬 수 있음.)
	@ColumnDefault("'user'")
	private String role; // enum 타입 사용을 권장함. : admin, user, manager
}
