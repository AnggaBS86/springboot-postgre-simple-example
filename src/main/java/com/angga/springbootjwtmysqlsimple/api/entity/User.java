package com.angga.springbootjwtmysqlsimple.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import org.jetbrains.annotations.Nullable;

/**
 * This entities used for table `users` purposed
 * 
 * This is the SQL query syntax generator of this table :
 * 
 * CREATE TABLE public."user" (
 * id serial PRIMARY KEY,
 * name VARCHAR ( 50 ) UNIQUE NOT NULL,
 * phone VARCHAR ( 25) not null,
 * password VARCHAR ( 50 ) NOT NULL,
 * jwt_code VARCHAR (255) not null
 * );
 * 
 * @author Angga Bayu Sejati<anggabs86@gmail.com>
 */
@Entity
@Table(name = "user_data")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "Username is mandatory")
	private String username;

	@NotBlank(message = "Password is mandatory")
	private String password;


	private String jwtCode;

	private String Name;
	

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getJwtCode() {
		return jwtCode;
	}

	public void setJwtCode(String jwtCode) {
		this.jwtCode = jwtCode;
	}

	private String roles;

	public User() {
}

	public User(String username, String password, String phone) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Get ID
	 * 
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set ID
	 * 
	 * @param id int
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get `username`
	 * 
	 * @return String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set username value
	 * 
	 * @param username String
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get Password
	 * 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set password value
	 * 
	 * @param password String
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get roles
	 * 
	 * @return String
	 */
	public String getRoles() {
		return roles;
	}

	/**
	 * Set roles value
	 * 
	 * @param roles String
	 */
	public void setRoles(String roles) {
		this.roles = roles;
	}
}
