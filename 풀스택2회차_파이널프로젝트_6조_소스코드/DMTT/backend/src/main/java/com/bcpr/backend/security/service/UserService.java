package com.bcpr.backend.security.service;

import java.util.List;

import com.bcpr.backend.security.domain.Role;
import com.bcpr.backend.security.domain.User;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    Role getRole(String name);
    List<User> getUsers();
}
