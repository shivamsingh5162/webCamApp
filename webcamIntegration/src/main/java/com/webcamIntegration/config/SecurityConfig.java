package com.webcamIntegration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	  @Bean
	    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
	    	// The builder will ensure the passwords are encoded before saving in memory
	    	UserDetails user = User.builder().username("user")
	    		.password(passwordEncoder.encode("password"))
	    		.roles("USER","ADMIN")
	    		.build();
	    	UserDetails admin = User.withUsername("admin")
	    		.password(passwordEncoder.encode("password"))
	    		.roles("USER", "ADMIN")
	    		.build();
	    	return new InMemoryUserDetailsManager(user, admin);
	    }
	    

	  
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	return http
    			.authorizeRequests()
                .antMatchers("/login", "/error").permitAll()
                .antMatchers("/dashboard/**").hasAnyRole("USER","ADMIN")
                .anyRequest().authenticated()
                .and()
        .formLogin()
        .loginPage("/login") // Specify the custom login page
        .defaultSuccessUrl("/dashboard") // Customize the default success URL
        .permitAll()
        .and()
        .logout()
        .logoutUrl("/logout")
        .permitAll().and().build();

}

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
  
    
   
}