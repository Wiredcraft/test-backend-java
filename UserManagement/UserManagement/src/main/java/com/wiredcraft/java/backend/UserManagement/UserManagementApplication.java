package com.wiredcraft.java.backend.UserManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

/***
 * Main Entry class for User Management
 * @author swat
 *
 */

@SpringBootApplication
@OpenAPIDefinition
public class UserManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

}
