package com.wiredcraft.java.backend.UserManagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wiredcraft.java.backend.UserManagement.entity.Users;
import com.wiredcraft.java.backend.UserManagement.error.UserNotFoundException;
import com.wiredcraft.java.backend.UserManagement.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@PostMapping("/user")
	public Users saveUser(@Valid @RequestBody Users user) {
		LOGGER.info("Requestted for Registration of User " + user.toString());
		return userService.saveUser(user);
		
	}
	
	@GetMapping("/user")
	public List<Users> fetchUserList(){
		return userService.fetchUserList();
		
	}
	
	@GetMapping("/user/{id}")
	public Users fetchUserbyId(@PathVariable("id") Long id) throws UserNotFoundException{
		return userService.fetchUserbyId(id);
		
	}
	
	@DeleteMapping("/user/{id}")
	public String deleteUserbyId(@PathVariable("id") Long id) {
		 userService.deleteUserbyId(id);
		 return "User with ID " + id + " deleted succesfully";
	}

	@PutMapping("/user/{id}")
	public Users updateUser(@PathVariable("id") Long id, @RequestBody Users user) throws UserNotFoundException {
		return userService.updateUser(id, user);
		 
	}
	
	@GetMapping("/user/name/{name}")
	public Users fetchUserByName(@PathVariable("name") String userName) {
		LOGGER.info("Requestted for Fetching by user name " + userName);
		return userService.fetchUserByName(userName);
		
	}
	
}
