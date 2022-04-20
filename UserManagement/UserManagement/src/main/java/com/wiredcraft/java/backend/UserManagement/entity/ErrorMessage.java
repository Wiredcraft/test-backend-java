package com.wiredcraft.java.backend.UserManagement.entity;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * Entity class to hold  variables for user defined Exceptions 
 *  
 * @author swat
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
	
	private HttpStatus status;
	private String message;
	

}
