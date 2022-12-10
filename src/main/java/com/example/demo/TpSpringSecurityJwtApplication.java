package com.example.demo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.service.UserService;



@SpringBootApplication
public class TpSpringSecurityJwtApplication implements CommandLineRunner{

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(TpSpringSecurityJwtApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		 //User u1 =userRepository.save(new User(1, "rania","0000" ));
        Role r1 = roleRepository.save(new Role(1 , "ADMIN"));
		
		Set<Role> roles_admin = new HashSet<Role>();
		roles_admin.add(r1);
		
		Role r2 = roleRepository.save(new Role(2 , "USER"));
		
		Set<Role> roles_user = new HashSet<Role>();
		roles_user.add(r2);
		
	  User u1 =userRepository.save(new User(1, "rania","0000"  , roles_admin));
	  User u2 =userRepository.save(new User(2, "montahe","0000"  , roles_user));
		
	}

}
