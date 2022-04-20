package com.wiredcraft.java.backend.UserManagement.securityAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wiredcraft.java.backend.UserManagement.controller.UserController;
import com.wiredcraft.java.backend.UserManagement.entity.Users;
import com.wiredcraft.java.backend.UserManagement.error.UserNotFoundException;
import com.wiredcraft.java.backend.UserManagement.repository.UserRepository;

@Service
public class CustomUserDetailsService  implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	private final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		
		Users user = userRepository.findByUserName(username);
		
		LOGGER.debug("User Procured"+user);
		
		if(user == null)
			throw new UsernameNotFoundException("User Not Found");
		
		return new CustomUserDetails(user);
	}

}
