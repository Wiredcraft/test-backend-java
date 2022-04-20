package com.wiredcraft.java.backend.UserManagement.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.wiredcraft.java.backend.UserManagement.entity.Users;
import com.wiredcraft.java.backend.UserManagement.service.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc ;
	
	@MockBean
	private UserService userService;
	
	private Users user;

	@BeforeEach
	void setUp() throws Exception {
		
		user.builder().userName("SwaRa").address("ChinaYh").createdAt(new Date()).dateOfBirth(new Date()).
		description("Excellet").customerId(1L).build();
	}
	
	@Test
	@Disabled
	void testFetchUserbyId() throws Exception {
		Mockito.when(userService.fetchUserbyId(2L)).thenReturn(user);
		mockMvc.perform(get("/user/2").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.userName").value(user.getUserName()));
			
		
	}

	@Test
	void testSaveUser() throws Exception {
		Users userInput = user.builder().userName("SwaRa").address("ChinaYh").createdAt(new Date()).dateOfBirth(new Date()).
				description("Excellet").build();
		
		Mockito.when(userService.saveUser(userInput)).thenReturn(user);
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
			.content("{\n"
					+ "	\"userName\" : \"SwaRa\",\n"
					+ "	\"dateOfBirth\" : \"2022-04-17\" ,\n"
					+ "	\"address\" : \"ChinaYh\",\n"
					+ "	\"description\" : \"Excellet\" ,\n"
					+ "	\"createdAt\" : \"2022-04-17\"\n"
					+ "}"))
		.andExpect(status().isOk());
	}

	
	

}
