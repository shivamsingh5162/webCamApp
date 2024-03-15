
package com.webcamIntegration.config;

import javax.swing.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;

public class JFrameConfig extends JFrame {

    private JButton recordButton;
    private Webcam webcam;
    private MediaWriter writer;
    private File saveFile;
    private JLabel responseLabel;  // New JLabel to display the response message
// youtube

    public JFrameConfig() throws IOException {
        initializeUI();
    }

    private void initializeUI() throws IOException {
        setTitle("Video Recorder App");
        setSize(800, 600);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        Dimension size = WebcamResolution.VGA.getSize();
        webcam = Webcam.getDefault();
        webcam.setViewSize(size);
        webcam.open();
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setImageSizeDisplayed(true);
        webcamPanel.setFPSDisplayed(true);
        add(webcamPanel);
        
        
        String videoFolderPath = "D:\\STS 18MAY\\webcamIntegration\\src\\main\\resources\\videos\\" ;
        File videoFolder = new File(videoFolderPath);
        if (!videoFolder.exists()) {
            boolean created = videoFolder.mkdirs();
            if (!created) {
                throw new IOException("Failed to create video directory: " + videoFolderPath);
            }
        }



        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = videoFolderPath + timestamp + ".mp4";
        saveFile = new File(fileName);
        
        recordButton = new JButton("Record");
        recordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable the record button during recording
                recordButton.setEnabled(false);

                // Start the video recording in a separate thread
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        startVideoRecording(size);
                        return null;
                    }

                    @Override
                    protected void done() {
                        // Enable the record button after recording is complete
                        recordButton.setEnabled(true);
                    }
                };

                worker.execute();
            }
        });


        setLayout(new FlowLayout());
        add(recordButton);
    }
        

    private void startVideoRecording(Dimension size) throws InterruptedException {
        writer = ToolFactory.makeWriter(saveFile.getAbsolutePath());
        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            BufferedImage image = ConverterFactory.convertToType(webcam.getImage(), BufferedImage.TYPE_3BYTE_BGR);
            IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);

            IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 100);
            frame.setKeyFrame(i == 0);
            frame.setQuality(100);

            writer.encodeVideo(0, frame);

            Thread.sleep(20);
        }

        writer.close();
        System.out.println("Video recorded to the file: " + saveFile.getAbsolutePath());

        // Close the JFrame after recording is complete
        displayResponseMessage("Video recording completed successfully");
    }

    private void displayResponseMessage(String message) {
        responseLabel.setText(message);
    }

}
