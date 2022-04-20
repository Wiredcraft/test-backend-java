/*package com.wiredcraft.java.backend.UserManagement.securityAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wiredcraft.java.backend.UserManagement.entity.Users;
import com.wiredcraft.java.backend.UserManagement.error.UserNotFoundException;
import com.wiredcraft.java.backend.UserManagement.repository.UserRepository;

@Service
public class CustomUserDetailsService  implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		
		Users user = userRepository.findByUserName(username);
		
		if(user == null)
			throw new UsernameNotFoundException("User Not Found");
		
		return new CustomUserDetails(user);
	}

}*/
