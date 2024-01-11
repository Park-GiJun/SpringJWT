package com.security.springjwt.controller;

import com.security.springjwt.dto.BoardDTO;
import com.security.springjwt.entity.BoardEntity;
import com.security.springjwt.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user")
@Log4j2
public class UserPageController {

	private final String USER_PAGE = "/UserPage/UserPage";
	private final String WRITE_PAGE = "/UserPage/WritePage";
	private final String DETAIL_PAGE = "/UserPage/ViewPage";
	private final BoardService boardService;

	public UserPageController (BoardService boardService) {
		this.boardService = boardService;
	}

	@GetMapping("")
	public ModelAndView userPage () {
		if (log.isInfoEnabled ()) {
			log.info ("userPage Get");
		}
		ModelAndView modelAndView = new ModelAndView (USER_PAGE);
		List<BoardEntity> boardList = boardService.BoardFindAll ();
		modelAndView.addObject ("boardList", boardList);
		return modelAndView;
	}

	@GetMapping("/write")
	public String writePage (Model model) {
		if (log.isInfoEnabled ()) {
			log.info ("writePage Get");
		}
		model.addAttribute ("boardDTO", new BoardDTO ());
		return WRITE_PAGE;
	}

	@PostMapping("/writing")
	public ModelAndView savePost (BoardDTO boardDTO) {
		if (log.isInfoEnabled ()) {
			log.info ("savePost Post");
		}
		Authentication authentication = SecurityContextHolder.getContext ().getAuthentication ();
		String currentPrincipalName = authentication.getName ();
		LocalDate currentDate = LocalDate.now ();
		boardDTO.setWriter (currentPrincipalName);
		boardDTO.setDate (currentDate);
		log.warn (boardDTO.toString ());
		boardService.savePost (boardService.dtoToEntity (boardDTO));
		return userPage ();
	}

	@GetMapping("/delete/{id}")
	public ModelAndView deletePost (@PathVariable Long id) {
		if (log.isInfoEnabled ()) {
            log.info ("deletePost Get");
        }
        boardService.deletePost (id);
        return userPage ();
	}

	@GetMapping("/view/{id}")
	public ModelAndView viewPost (@PathVariable Long id) {
		if (log.isInfoEnabled ()) {
            log.info ("editPost Get");
        }
        ModelAndView modelAndView = new ModelAndView (DETAIL_PAGE);
        BoardEntity boardEntity = boardService.BoardFindById (id);
        modelAndView.addObject ("board", boardEntity);
        return modelAndView;
	}
}
