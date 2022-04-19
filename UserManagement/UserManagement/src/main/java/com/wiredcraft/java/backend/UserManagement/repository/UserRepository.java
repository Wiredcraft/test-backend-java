package com.wiredcraft.java.backend.UserManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wiredcraft.java.backend.UserManagement.entity.Users;

/**
 * JPA Repository using Interface 
 * reponsible to connecting and interacting with Database using spring-jpa
 * @author swat
 *
 */

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
	
	public Users findByUserName(String userName);
	
	public Users findByUserNameIgnoreCase(String userName);

}
