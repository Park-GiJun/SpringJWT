package com.security.springjwt.repository;

import com.security.springjwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	Boolean existsByUsername(String username);
}
