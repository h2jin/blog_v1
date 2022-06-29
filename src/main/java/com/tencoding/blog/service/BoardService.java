package com.tencoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.blog.model.Board;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void write(Board board, User user) { //  화면 딴에서 title, content 넘어오고, userID 도 매개변수로 받아 넘어옴.
		board.setCount(0);
		board.setUserId(user);
		boardRepository.save(board);
	};
	
	@Transactional
	public Page<Board> getBoardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional
	public Board boardDetail(int boardId) {
		return boardRepository.findById(boardId).orElseThrow(() -> {
			return new IllegalArgumentException("해당글은 찾을 수 없습니다.");
		});
	}
	
	@Transactional
	public void deleteById(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void modifyBoard(int id, Board board) { // title/ content 를 바꿨을 것임.
		// 업데이트는 먼저 찾아야 함
		Board boardEntity = boardRepository.findById(id).orElseThrow(() -> { // 영속성 컨텍스트로 가져왔기 때문에 1차캐시에 담겨있을 것임.
			return new IllegalArgumentException("해당글은 찾을 수 없습니다.");
		});
		boardEntity.setTitle(board.getTitle());
		boardEntity.setContent(board.getContent());
		// 더티체킹
		
	}

}