package com.bcpr.backend.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
	@PostMapping(value = "/test")
	public String testUrl(){
		return "test";
	}
}
