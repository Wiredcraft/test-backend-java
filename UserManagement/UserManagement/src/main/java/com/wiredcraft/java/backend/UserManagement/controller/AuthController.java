package com.wiredcraft.java.backend.UserManagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {
	
	@GetMapping("/home")
	private String home() {
		return "This is Home Page";
	}
	
	@GetMapping("/admin")
	private String admin() {
		return "This is Admin Page";
	}

}
