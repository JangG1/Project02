package com.bcpr.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bcpr.backend.security.domain.Role;
import com.bcpr.backend.security.repo.RoleRepo;
import com.bcpr.backend.security.repo.UserRepo;
import com.bcpr.backend.security.service.UserService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class BackendApplication {
	private final RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	// 테스트를 위한 가상 유저 생성
	// application.properties = create 시 run메소드 전체 주석 해제 필요
	// application.properties = update 시 run메소드 전체 주석 필요
	@Bean
    CommandLineRunner run(UserService userService){
        return args -> {
        	// 권한생성
        	//if(roleRepo.findByName("ROLE_USER")==null);
//            	userService.saveRole(new Role(null,"ROLE_USER"));
            // 유저생성
//            userService.saveUser(new User(null,"dahun3013@gmail.com","DMTT","",new ArrayList<>()));
            // 권한부여
//            userService.addRoleToUser("dahun3013@gmail.com","ROLE_USER");

        };
    }
}
