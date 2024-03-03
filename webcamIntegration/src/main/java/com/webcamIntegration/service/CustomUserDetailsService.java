package com.webcamIntegration.service;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // In a real application, you would load user details from a database
        // For simplicity, a hardcoded user is used in this example
        if ("user".equals(username)) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(username)
                    .password(passwordEncoder().encode("password"))
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

   
    // Replace this method with your actual PasswordEncoder implementation
   
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
