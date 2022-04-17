package com.wiredcraft.java.backend.UserManagement.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.wiredcraft.java.backend.UserManagement.entity.ErrorMessage;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException uex, WebRequest request) {
		
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, uex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		
	}
	
}
