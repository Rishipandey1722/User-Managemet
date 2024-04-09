package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.security.entity.MyUser;
import com.security.repository.MyUserRepository;
import com.security.repository.MyUserService;

@Controller
public class MainController {
	
	
	@Autowired
	private MyUserService userService;
	
	@GetMapping("/divyansh")
	public String handleDivyansh() {
		return "welcome";
	}
	
	@GetMapping("/")
	public String handleUserProfile() {
		return "redirect:/user/";
	}
	
	@GetMapping("/home")
	public String handleWelcome() {
		return "home";
	}
	
	@GetMapping("/admin/home")
	public String handleAdminHome() {
		return "home_admin";
	}
	
	
	
	@GetMapping("/user/home")
	public String handleUserHome() {
		return "home_user";
	}
	
	
	@GetMapping("/register/user")
	public String handleRegisterPage() {
		return "custom_register_user";
	}
	
	
	
	@PostMapping("/home/createUser")
	public String handleRegisterUser(@ModelAttribute MyUser myuser) {
		
		System.out.println(myuser);
		
		this.userService.addNewUser(myuser);
		
		return "redirect:/login";
	}
	
	
	@GetMapping("/login")
	public String handlelogin(Model model) {
		model.addAttribute("error"  , "Invalid username or password");
		return "custom_login";
	}
	
	
	

}



/*
 * 
 * 
 */

