package com.security.springjwt.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BoardDTO {
	private String title;
	private String content;
	private String writer;
	private String password;
	private LocalDate date;

	@Builder
	public BoardDTO(String title, String content, String writer, String password, LocalDate date) {
		this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
		this.date = date;

	}
}
