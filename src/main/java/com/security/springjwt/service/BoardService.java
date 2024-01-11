package com.security.springjwt.service;

import com.security.springjwt.dto.BoardDTO;
import com.security.springjwt.entity.BoardEntity;
import com.security.springjwt.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

	private final BoardRepository boardRepository;

	public BoardService (BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public BoardEntity dtoToEntity(BoardDTO boardDTO) {
		return BoardEntity.builder()
				.title(boardDTO.getTitle())
				.content(boardDTO.getContent())
				.username(boardDTO.getWriter())
				.password(boardDTO.getPassword())
				.date(boardDTO.getDate())
				.build();
	}


	public List<BoardEntity> BoardFindAll () {
		return boardRepository.findAll ();
	}

	public void savePost (BoardEntity boardEntity) {
		boardRepository.save (boardEntity);
	}

	public void deletePost (Long id) {
		boardRepository.deleteById (id);
	}

	public BoardEntity BoardFindById (Long id) {
		return boardRepository.findById (id).orElse (null);
	}
}
