package com.webcamIntegration.config;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class YourCustomFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Custom logic before the request is processed by the next filter in the chain
        System.out.println("Custom Filter: Processing the request...");

        // Continue with the filter chain
        super.doFilter(request, response, chain);

        // Custom logic after the request is processed by the next filter in the chain
        System.out.println("Custom Filter: Request processed.");
    }
}
