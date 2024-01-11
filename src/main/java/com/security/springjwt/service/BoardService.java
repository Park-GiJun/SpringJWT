package com.security.springjwt.service;

import com.security.springjwt.dto.BoardDTO;
import com.security.springjwt.entity.BoardEntity;
import com.security.springjwt.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class BoardService {

	private final BoardRepository boardRepository;

	public BoardService (BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public BoardEntity dtoToEntityWrite(BoardDTO boardDTO) {
		return BoardEntity.builder()
				.title(boardDTO.getTitle())
				.content(boardDTO.getContent())
				.username(boardDTO.getWriter())
				.password(boardDTO.getPassword())
				.date(boardDTO.getDate())
				.build();
	}

	public BoardEntity dtoToEntityEdit(BoardDTO boardDTO) {
		return BoardEntity.builder()
				.id (boardDTO.getId ())
				.title(boardDTO.getTitle())
				.content(boardDTO.getContent())
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

	@Transactional
	public void updatePostById (Long id, BoardEntity boardEntity) {
		log.fatal ("updatePostById " + id + " " + boardEntity.getTitle () + " " + boardEntity.getContent ());
		boardRepository.updatePostById (id, boardEntity.getTitle (), boardEntity.getContent ());
	}
}
