package com.tencoding.blog.controller;

import java.util.ArrayList;

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
	public String index(@PageableDefault(size = 3, sort = "id", direction = Direction.DESC) Pageable pageable ,Model model) {
		Page<Board> pageBoards = boardService.getBoardList(pageable);
		
		// 페이징 처리 2단계 [1 2 3 4 5] 
		// 1. 현재 페이지 앞 뒤로 2 블록씩 보여야 한다.
		// 2. 페이비 버튼을 누르면(블록) 해당 페이지로 화면을 이동하기.
		// 3. 현재 페이지에 'active'하기 (활성화)
//		int startPage = pageBoards.getPageable().getPageNumber() - 2;
//		int endPage = pageBoards.getPageable().getPageNumber() + 2;
		
		// 음수와 총 페이지를 넘어서는 숫자를 보이지 않게 하기 위한 코드
		int nowPage = pageBoards.getPageable().getPageNumber() + 1; // 현재 페이지
		int startPage = Math.max(nowPage - 2, 1); // 두 int 값 중에 큰 값을 반환한다.
		int endPage = Math.min(nowPage + 2, pageBoards.getTotalPages()); // 총 페이지를 넣어줌. 
		
		// 밑의 숫자를 알면 페이징 처리 가능함.
		System.out.println("============================");
		log.info("현재 화면의 블록 숫자 (현재 페이지) : {}" , nowPage);
		log.info("현재 화면의 보여질 블록의 시작번호 : {}" , startPage);
		log.info("현재 화면의 보여질 마지막 블록의 번호 : {}" , endPage);
		log.info("화면에 보여줄 총 게시글 / 한 화면에 보여질 게시글 (총 페이지 숫자) : {}" , pageBoards.getTotalPages());
		System.out.println("============================");

		
		
		// 시작페이지를 설정해야 한다.
		// 페이지 번호를 배열로 만들어서 던져주기
		ArrayList<Integer> pageNumbers = new ArrayList<>();
		// 주의 마지막 번호까지 저장하기!
		for(int i = startPage ; i <= endPage; i++) {
			pageNumbers.add(i);
		}
		
		model.addAttribute("pageable", pageBoards);
		model.addAttribute( "startPage" , startPage);
		model.addAttribute( "endPage" , endPage);
		model.addAttribute("pageNumbers", pageNumbers);
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
	
	@GetMapping("/board/{id}/update_form")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.boardDetail(id));
		return "/board/update_form";
	}
	
	
	
	
	
}
