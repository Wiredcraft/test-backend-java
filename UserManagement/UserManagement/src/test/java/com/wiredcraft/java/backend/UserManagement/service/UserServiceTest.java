/**
 * 
 */
package com.wiredcraft.java.backend.UserManagement.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.wiredcraft.java.backend.UserManagement.entity.Users;
import com.wiredcraft.java.backend.UserManagement.repository.UserRepository;

/**
 * @author swat
 *
 */
@SpringBootTest
class UserServiceTest {
	
	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		Users user = Users.builder().userName("Swathi").address("India").createdAt(new Date()).dateOfBirth(new Date()).
		description("good").customerId(1L).build(); 
		
		Mockito.when(userRepository.findByUserNameIgnoreCase("Swathi")).thenReturn(user);
	}
	
	@Test
	@DisplayName("Get Data based on Valid User Name")
	public void whenValidUser_UserIsFound() {
		String userName="Swathi";
		Users found = 
				userService.fetchUserByName(userName);
		assertEquals(userName, found.getUserName());
	
	}
	
	/*@Test
	public void whenInValidUser_UserIsNotFound() {
		String userName="SwaSra";
		Users found = 
				userService.fetchUserByName(userName);
		assertNotEquals(userName, found.getUserName());
	
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}*/

}
