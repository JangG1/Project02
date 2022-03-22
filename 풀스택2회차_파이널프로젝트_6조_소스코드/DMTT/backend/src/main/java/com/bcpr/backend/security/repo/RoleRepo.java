package com.bcpr.backend.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcpr.backend.security.domain.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
