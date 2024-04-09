package com.security.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.security.entity.MyUser;
import com.security.entity.ResetUserPassword;
import com.security.repository.MyUserRepository;

@Controller
@RequestMapping("/user")
public class MyUserController {
    
    @Autowired
    private MyUserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ModelAttribute("user") // Specify the model attribute name explicitly
    private MyUser userDetails(Principal principal) {
        String username = principal.getName();
        Optional<MyUser> userOptional = userRepository.findByUsername(username);
        return userOptional.orElse(null); // Return null if user not found
    }

    @GetMapping("/profile")
    public String handleUserProfile(Model model) {
    	model.addAttribute("msg" , "Logged in succesfully");

        return "user/user_profile";
    }
    

    @GetMapping("/edit/profile")
    public String handleUserEditProfile() {
    	
        return "user/profile_edit";
    }
    
    
    @PostMapping("/edit/profile")
    public String handleUserProfileUpdate(@ModelAttribute("user") MyUser myUser , Model model) {
    	System.out.println(myUser);
    	
    	Optional<MyUser> existingUser = this.userRepository.findById(myUser.getId());
    	
    	if(myUser.getUsername() != null)
    		existingUser.get().setUsername(myUser.getUsername());
    	
    	if(myUser.getAddress() != null)
    		existingUser.get().setAddress(myUser.getAddress());
    	
    	if(myUser.getQualification() != null)
    		existingUser.get().setQualification(myUser.getQualification());
    	
    	this.userRepository.save(existingUser.get());
    	model.addAttribute("successMessage" , "Profile succesfully changed");
    	
//    	System.out.println(existingUser.get());
        return "/user/user_profile";
    }
    
    
    @GetMapping("/resetPassword")
    public String handleResetPassword(Model model) {
		model.addAttribute("error" ,"Both paswword must be same");

    	return "user/user_reset_password";
    }
    
    
    
    @PostMapping("/processPassword")
    public String checkAndResetPassword(Principal principal , @ModelAttribute("currentPassword") String currentPassword,
            @ModelAttribute("newPassword") String newPassword,
            @ModelAttribute("confirmPassword") String confirmPassword ,
            Model model) {
    	
    	
    	
    	
    	System.out.println(currentPassword +" " + newPassword + " " + confirmPassword);
    	String username = principal.getName();
        Optional<MyUser> userOptional = userRepository.findByUsername(username);
        
//        String userPasswordString = 
        
        
//        if(!newPassword.equals(passwordEncoder.encode(currentPassword)))
//        currentPassword = passwordEncoder.encode(currentPassword);
        
        System.out.println(userOptional.get().getPassword());
        System.out.println(currentPassword);
        
        if(passwordEncoder.matches(currentPassword,userOptional.get().getPassword())  ) {
        	
        	userOptional.get().setPassword(passwordEncoder.encode(newPassword));
        	this.userRepository.save(userOptional.get());
        	
        	
        	if((newPassword.equals(confirmPassword)))
        		return "redirect:/user/profile";
        	else {
        		return "redirect:/user/resetPassword";
        		
        	}
        	
        	
        }
        	
        
//        if(userOptional.get().getPassword().equals(currentPassword)) {
//        	userOptional.get().setPassword(confirmPassword);
//        	this.userRepository.save(userOptional.get());
//        	
//
//    

    	
    	return "redirect:/user/resetPassword";
    	
    }
    
    
    
}