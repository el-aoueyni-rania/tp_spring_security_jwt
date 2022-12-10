package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.UserRepository;
import com.example.demo.entities.User;
import com.example.demo.service.UserService;

import lombok.Data;

@RestController
public class UserController {
		@Autowired
		UserRepository userRepository;

		@Autowired
		UserService userService;
		
		@GetMapping("/user")
		public List <User> getusers(){
			return  userRepository.findAll();
			
		}
		
		@PostMapping("/register")
		public User saveUser(@RequestBody User r) {
			return userService.saveUser(r.getUsername(), r.getPassword(), r.getPassword());	
		}
		
	}


@Data
class UserForm{
	
	 private String username;
	 private String password;
	 private String confirmedPassword;
	 
	 
	 
	public UserForm(String username, String password, String confirmedPassword) {
		super();
		this.username = username;
		this.password = password;
		this.confirmedPassword = confirmedPassword;
	}
	
	
	public UserForm() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmedPassword() {
		return confirmedPassword;
	}
	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}
	

} 
