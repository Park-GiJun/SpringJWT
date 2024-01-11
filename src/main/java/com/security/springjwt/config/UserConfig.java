package com.security.springjwt.config;

import com.security.springjwt.repository.BoardRepository;
import com.security.springjwt.service.BoardService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

	private final BoardRepository boardRepository;

	public UserConfig (BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	@Bean
	public BoardService boardService () {
        return new BoardService (boardRepository);
    }
}
