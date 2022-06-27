package com.tencoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	

}
