package com.wiredcraft.java.backend.UserManagement.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	private Long customerId;
	
	@NotBlank(message = "Please  input the Name of the User")
	@Length(max=50, min=3)
	
	private String userName;
	
	@Past
	private Date dateOfBirth;
	private String address;
	private String description;
	
	@PastOrPresent
	private Date createdAt;
	
	private String password;
	
	
	/*public Users(Long id, String userName, Date dateOfBirth, String address, String description , Date createdAt ) {
		this.customerId=id;
		this.userName=userName;
		this.dateOfBirth=dateOfBirth;
		this.address=address;
		this.description=description;
		this.createdAt=createdAt;
	}
	
	public Users() {
		
	}
	
	@Override
	public String toString() {
		return "Users{ id = '"+ customerId +'\''+
				", userName = '"+ userName +'\''+
				",  dateOfBirth ='"+ dateOfBirth +'\''+
				", address = '"+ address +'\''+
				", description = '" + description + '\''+
				", createddate = '" + createdAt + '\''+ '}';
	}
	public long getId() {
		return customerId;
	}
	public void setId(Long id) {
		this.customerId = id;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	} */

}
