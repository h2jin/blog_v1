package com.tencoding.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	
	// 댓글 정보
	//하나의 게시글에 여러개의 댓글이 있을 수 있다.
	// one - board many - reply
	// mappedBy -> "board" board는 reply 테이블의 필드 이름이다.
	// 하지만 mappedBy는 연관관계의 주인이 아니다. (FK 아님. DB에 컬럼을 만들지 마시오!)
	// 데이터를 가지고 올때는 board 이름으로 가지고 오지만 board에 필드가 생기지는 않는다. = 연관관계의 주인이 아니다.
	// EAGER - 데이터 한번에 다 들고옴. LAZY - 나중에 필요할 때 요청하여 데이터 들고올 수 있음.
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
	@JsonIgnoreProperties({"board", "user"}) // Reply 안에 있는 board getter를 무시해라 (getter 호출되지 않음)
	private List<Reply> replys;
	
	
	@CreationTimestamp
	private Timestamp createDate;

}
