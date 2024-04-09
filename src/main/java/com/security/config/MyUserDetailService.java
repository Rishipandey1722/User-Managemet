package com.security.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.entity.MyUser;
import com.security.repository.MyUserRepository;


@Service

public class MyUserDetailService implements UserDetailsService{

	
	@Autowired
	private MyUserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<MyUser> user = userRepository.findByUsername(username);
		
		if(user.isPresent()) {
			var userObj = user.get();
			return User.builder()
					.username(userObj.getUsername())
					.password(userObj.getPassword())
					.roles(getRoles(userObj))
					.build();
			
			
		}
		else {
			throw new UsernameNotFoundException(username);
		}
		
//		return null;
	}
	private String[] getRoles(MyUser userObj) {
		
		if(userObj.getRole() == null)
			return new String[] {"USER"};
		return userObj.getRole().split(",");
	}

}
