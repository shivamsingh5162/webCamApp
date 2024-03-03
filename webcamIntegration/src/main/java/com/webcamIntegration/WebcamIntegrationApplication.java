package com.webcamIntegration;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebcamIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebcamIntegrationApplication.class, args);
		System.setProperty("java.awt.headless", "false");
		
	}

}
