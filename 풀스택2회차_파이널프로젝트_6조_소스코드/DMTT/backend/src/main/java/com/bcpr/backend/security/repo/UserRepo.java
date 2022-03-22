package com.bcpr.backend.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcpr.backend.security.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
	User findByEmail(String email);

}