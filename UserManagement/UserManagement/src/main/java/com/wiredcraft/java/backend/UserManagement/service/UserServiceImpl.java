package com.wiredcraft.java.backend.UserManagement.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wiredcraft.java.backend.UserManagement.controller.UserController;
import com.wiredcraft.java.backend.UserManagement.entity.Users;
import com.wiredcraft.java.backend.UserManagement.error.UserNotFoundException;
import com.wiredcraft.java.backend.UserManagement.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Users saveUser(Users user) {
		
		String encodedPwd=null;
		
		if(user.getPassword()!=null) {
			encodedPwd = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPwd);
		}
		
		return userRepository.save(user);
	}

	@Override
	public List<Users> fetchUserList() {
		return userRepository.findAll();
	}


	@Override
	public Users fetchUserbyId(Long id) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Optional<Users> userFromDB = userRepository.findById(id);
		if(!userFromDB.isPresent()) {
			LOGGER.error("User is not avaiable. Request user to register first");
			throw new UserNotFoundException("Sorry !! User not Avaiable !!");
			
		
			
		}
		return userFromDB.get();
		
			
	}

	@Override
	public void deleteUserbyId(Long id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public Users updateUser(Long id, Users user) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Users userFromDB = fetchUserbyId(id);
		
		
		if(Objects.nonNull(user.getUserName()) && !"".equalsIgnoreCase(user.getUserName())) {
			userFromDB.setUserName(user.getUserName());
		}
		
		if(Objects.nonNull(user.getDateOfBirth()) && null != (user.getDateOfBirth())) {
			userFromDB.setDateOfBirth(user.getDateOfBirth());
		}
		
		if(Objects.nonNull(user.getCreatedAt()) && null != (user.getCreatedAt())) {
			userFromDB.setCreatedAt(user.getCreatedAt());
		}
		
		if(Objects.nonNull(user.getAddress()) &&  !"".equalsIgnoreCase(user.getAddress())) {
			userFromDB.setAddress(user.getAddress());
		}
		
		if(Objects.nonNull(user.getDescription()) &&  !"".equalsIgnoreCase(user.getDescription())) {
			userFromDB.setDescription(user.getDescription());
		}
		
		return userRepository.save(userFromDB);
	}

	@Override
	public Users fetchUserByName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findByUserNameIgnoreCase(userName);
	}

	

}
