package com.tencoding.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tencoding.blog.model.Board;
import com.tencoding.blog.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping({"", "/"})
	public String index(@PageableDefault(size = 2, sort = "id", direction = Direction.DESC) Pageable pageable ,Model model) {
		Page<Board> boards = boardService.getBoardList(pageable);
		model.addAttribute("pageable", boards);
		return "index";
	}
	
	@GetMapping("/board/save_form")
	public String saveForm() {
		log.info("saveForm 메서드 호출");
		return "/board/save_form";
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.boardDetail(id));
		return "/board/detail";
	}
	
	
	
}
