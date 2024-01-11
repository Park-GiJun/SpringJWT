package com.security.springjwt.repository;

import com.security.springjwt.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

	@Modifying
	@Query("update BoardEntity b set b.title = :title, b.content = :content where b.id = :id")
	void updatePostById(@Param("id") Long id, @Param("title") String title, @Param("content") String content);
}
