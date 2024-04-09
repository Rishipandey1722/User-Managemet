package com.security.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.entity.MyUser;

@Service
public class MyUserService {
	
	@Autowired
	private MyUserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public MyUser addNewUser(MyUser user) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("USER");
		this.userRepository.save(user);
		return user;
		
		
	}

}
