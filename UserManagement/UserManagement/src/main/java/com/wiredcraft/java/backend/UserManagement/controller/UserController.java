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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


/***
 * Main Rest Controller class that has all the services 
 * and uri endpoints defined to access the same
 * @author swat
 *
 */
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	// Allows to create new User and save to database
	@PostMapping("/register")
	@Operation(summary= "Allows to create new User and save to database")
	@ApiResponses(value = {
			@ApiResponse(responseCode="200", 
			description="Successfully Regsitered th customer",
			content= {@Content(mediaType="application/json")}),
			@ApiResponse(responseCode="404", 
			description="Service not available",
			content= @Content)})
	public Users saveUser(@Valid @RequestBody Users user) {
		LOGGER.info("Requestted for Registration of User " + user.toString());
		return userService.saveUser(user);
		
	}
	
	// Returns all the user present in database
	@GetMapping("/getusers")
	@Operation(summary= "Returns all the registerred user details")
	@ApiResponses(value = {
			@ApiResponse(responseCode="200", 
			description="Successfully returned all the useer present in database",
			content= {@Content(mediaType="application/json")}),
			@ApiResponse(responseCode="403", 
			description="Unautorized to run this API. Please share valid Authentication Credentials",
			content= @Content)})
	public List<Users> fetchUserList(){
		LOGGER.info("Fetching all the users registsred with us !! ");
		return userService.fetchUserList();
		
	}
	
	//Returns particular user if registered based on id
	@GetMapping("/user/{id}")
	@Operation(summary= "Returns particular user details from database if registered based on id")
	@ApiResponses(value = {
			@ApiResponse(responseCode="200", 
			description="Successfully returned  the given user ID  details present in database",
			content= {@Content(mediaType="application/json")}),
			@ApiResponse(responseCode="403", 
			description="Unautorized to run this API. Please share valid Authentication Credentials",
			content= @Content)})
	
	public Users fetchUserbyId(@PathVariable("id") Long id) throws UserNotFoundException{
		LOGGER.info("Fetching any users with id"+ id);
		return userService.fetchUserbyId(id);
		
	}
	
	//Allows deletion of registered user based on ID
	@DeleteMapping("/user/{id}")
	@Operation(summary= "Allows deletion of registered user based on ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode="200", 
			description="Successfully deletes  the given user ID  details present in database",
			content= {@Content(mediaType="application/json")}),
			@ApiResponse(responseCode="403", 
			description="Unautorized to run this API. Please share valid Authentication Credentials",
			content= @Content)})
	public String deleteUserbyId(@PathVariable("id") Long id) {
		LOGGER.info("Deleting  users with id"+ id);
		 userService.deleteUserbyId(id);
		 return "User with ID " + id + " deleted succesfully";
	}

	//Allows updating of user details based on given id
	@PutMapping("/user/{id}")
	@Operation(summary= "Allows updating of user details based on given id")
	@ApiResponses(value = {
			@ApiResponse(responseCode="200", 
			description="Successfully updates  the given user ID  details present in database",
			content= {@Content(mediaType="application/json")}),
			@ApiResponse(responseCode="403", 
			description="Unautorized to run this API. Please share valid Authentication Credentials",
			content= @Content)})
	public Users updateUser(@PathVariable("id") Long id, @RequestBody Users user) throws UserNotFoundException {
		LOGGER.info("Updating  users with id"+ id);
		return userService.updateUser(id, user);
		 
	}
	
	//Allows to get a particular user based on the user name
	@GetMapping("/user/name/{name}")
	@Operation(summary= "Allows to get a particular user details based on the user name")
	@ApiResponses(value = {
			@ApiResponse(responseCode="200", 
			description="Successfully returns  the given username associated details present in database",
			content= {@Content(mediaType="application/json")}),
			@ApiResponse(responseCode="403", 
			description="Unautorized to run this API. Please share valid Authentication Credentials",
			content= @Content)})
	public Users fetchUserByName(@PathVariable("name") String userName) {
		LOGGER.info("Requestted for Fetching by user name " + userName);
		return userService.fetchUserByName(userName);
		
	}
	
}
