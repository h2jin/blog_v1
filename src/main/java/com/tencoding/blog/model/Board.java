package com.tencoding.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

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
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 100)
	private String title;

	@Lob // 대용량 데이터
	private String content;

	@ColumnDefault("0") // int // String - "'안녕'"
	private int count;

	// 여러개의 게시글은 하나의 유저를 가진다. (하나의 게시글을 여러 유저가 가질 수 없음)
	// Many = board , One = User
	@ManyToOne(fetch = FetchType.EAGER) // board select 할 때 한번에 데이터를 가져와 lazy -> 한번에 가져오는게 아닌, 프록시 객체 잠시 만들어서 나중에 가져올 수 있도록 
	@JoinColumn(name = "userId")
	private User userId;
	
	

	@CreationTimestamp
	private Timestamp createDate;

}
