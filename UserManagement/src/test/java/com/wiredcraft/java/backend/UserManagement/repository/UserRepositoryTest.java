package com.wiredcraft.java.backend.UserManagement.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.wiredcraft.java.backend.UserManagement.entity.Users;

@DataJpaTest
class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager entityManager;

	@BeforeEach
	void setUp() throws Exception {
		
		Users user = Users.builder().userName("SwaRa").address("ChinaYh").createdAt(new Date()).dateOfBirth(new Date()).
				description("Excellet").build(); 
		entityManager.persist(user);
	}
	
	@Test
public void whenFindById_ThenReturnUser() {
	Users user = userRepository.findById(1L).get();
	assertEquals(user.getUserName(), "SwaRa");
}
	
	@Test
	@Disabled
	void test() {
		fail("Not yet implemented");
	}

}
