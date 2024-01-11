package com.security.springjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
	private Long id;
	private String title;
	private String content;
	private String writer;
	private String password;
	private LocalDate date;
}
