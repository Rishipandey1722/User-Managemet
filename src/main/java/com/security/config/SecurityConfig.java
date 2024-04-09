package com.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private MyUserDetailService userDetailService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
		
		return http
			.csrf(Csrf->Csrf.disable())
			.authorizeHttpRequests(registry ->{
			
			registry.requestMatchers("/home/**" , "/register/**").permitAll();
			registry.requestMatchers("/admin/**").hasRole("ADMIN");
			registry.requestMatchers("/user/**").hasRole("USER");
			registry.anyRequest().authenticated();
			
		})
			.formLogin(formLogin -> formLogin
					.loginPage("/login")
					.successHandler(new CustomAuthenticationSuccessHandler())
					.defaultSuccessUrl("/user/profile" ,true)
					.permitAll())
			
				.build();
		
	}

	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return userDetailService;
	}
	
	
	
	
// Inmemory
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		UserDetails normalUser = User.builder()
//				.username("rishi")
//				.password(passwordEncoder().encode("123"))
//				.roles("USER")
//				.build();
//		
//		UserDetails adminUser = User.builder()
//				.username("pandey")
//				.password(passwordEncoder().encode("12345"))
//				.roles("ADMIN" , "USER")
//				.build();
//		
//		return new InMemoryUserDetailsManager(normalUser , adminUser);
//	}
	
	
	

}
