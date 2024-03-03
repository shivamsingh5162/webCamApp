package com.webcamIntegration.controller;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.xuggle.xuggler.IPixelFormat;


import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.opencv.videoio.VideoWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDevice;
import com.github.sarxos.webcam.WebcamDriver;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.webcamIntegration.config.JFrameConfig;
import com.webcamIntegration.service.FileService;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;

@Controller
public class WebcamController {
	
    private JButton recordButton;
    @Autowired
    private FileService fileService;
    
    private static final String VIDEOS_FOLDER = "src/main/resources/videos/";

    @GetMapping("/videos")
    public String showVideoList(Model model) {
        List<String> fileNames = getMP4FileNames();
        model.addAttribute("fileNames", fileNames);
        return "history";
    }
    
    @GetMapping("/videos/play/{fileName}")
    public String playVideo(@PathVariable String fileName, Model model) {
        model.addAttribute("fileName", fileName);
        return "play";
    }
    
    
	@RequestMapping("/webcam")
	public String webCam() throws IOException, InterruptedException {
		
		 SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
						new JFrameConfig().setVisible(true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        });
		 
		System.out.println("Video Processing");
        return "redirect:/loading";
    }

   

	    @RequestMapping("/loading")
	    public String loading() {
	        return "loading";  // This should match the name of your Thymeleaf template (loading.html)
	    }
    
	
	
    @GetMapping("/history")
    public String historyPage(Model model) {
        List<String> videoFiles = fileService.getStoredFiles();
        model.addAttribute("videoFiles", videoFiles);
        return "history";
    }
		
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }	
    
    
    @GetMapping("/login")
    public String login() {
        return "login"; // Thymeleaf will resolve this to login.html
    }
    
    @GetMapping("/logout")
    public String logout() {
        // Perform any logout-related logic here
        // For simplicity, let's just redirect to the login page after logout
        return "redirect:/login";
    }
    
    private List<String> getMP4FileNames() {
        List<String> fileNames = new ArrayList<>();
        File folder = new File(VIDEOS_FOLDER);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp4"));

            if (files != null) {
                for (File file : files) {
                    fileNames.add(file.getName());
                }
            }
        }

        return fileNames;
    }

}

		

			
	
