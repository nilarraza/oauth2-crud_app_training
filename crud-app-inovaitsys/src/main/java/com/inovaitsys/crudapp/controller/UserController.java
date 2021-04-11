package com.inovaitsys.crudapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inovaitsys.crudapp.model.Role;
import com.inovaitsys.crudapp.model.User;
import com.inovaitsys.crudapp.repository.UserRepository;

@RestController
@RequestMapping("/user") //Testing Purpose Controller
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//Testing Purpose
	@RequestMapping(method = RequestMethod.POST)
	public String addUser() {
		User user=new User();
		user.setUsername("Preena");
		user.setPassword(passwordEncoder.encode("123456"));
		user.setActive(true);
		
		List<Role> rolelist=new ArrayList<Role>();
		Role role=new Role();
		Role role1=new Role();
		role.setName("Admin");
		role1.setName("User");
		rolelist.add(role);
		rolelist.add(role1);
		user.setRoles(rolelist);
		
		userRepository.save(user);
		
		return "successfully saved";

		}
}
