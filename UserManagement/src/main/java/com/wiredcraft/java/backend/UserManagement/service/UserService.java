package com.wiredcraft.java.backend.UserManagement.service;

import java.util.List;

import com.wiredcraft.java.backend.UserManagement.entity.Users;
import com.wiredcraft.java.backend.UserManagement.error.UserNotFoundException;

public interface UserService {

	public Users saveUser(Users user);

	public List<Users> fetchUserList();

	public Users fetchUserbyId(Long id) throws UserNotFoundException;

	public void deleteUserbyId(Long id);

	public Users updateUser(Long id, Users user) throws UserNotFoundException;

	public Users fetchUserByName(String userName);

}
