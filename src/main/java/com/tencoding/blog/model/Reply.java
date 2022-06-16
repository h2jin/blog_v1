package com.tencoding.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 200)
	private String content;

	// 하나의 댓글에 여러 유저가 작성할 수 없음
	// 하나의 유저가 여러 댓글 달 수 있음
	@ManyToOne
	@JoinColumn(name = "userId") // reply 테이블의 컬럼명이 됨.
	private User user;

	// 여러개의 댓글은 하나의 게시글에 존재할 수 있다.
	@ManyToOne
	@JoinColumn(name = "boardId")
	private Board board;

	@CreationTimestamp
	private Timestamp createDate;
}
