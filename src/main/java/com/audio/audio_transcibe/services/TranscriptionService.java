package com.audio.audio_transcibe.services;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

@Service
public class TranscriptionService {

    private final ChatClient chatClient;

    // Constructor for Spring to inject the ChatClient
    public TranscriptionService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * THIS IS THE METHOD THAT WAS MISSING OR MISNAMED
     */
    public String processFile(MultipartFile multipartFile) throws Exception {
        String contentType = multipartFile.getContentType();

        // Create a temporary file to store the upload
        File tempFile = Files.createTempFile("upload-", multipartFile.getOriginalFilename()).toFile();
        multipartFile.transferTo(tempFile);

        File audioFile;

        // 1. Check if it's a video and needs audio extraction
        if (contentType != null && contentType.startsWith("video")) {
            audioFile = extractAudio(tempFile);
        } else {
            audioFile = tempFile;
        }

        try {
            // 2. Wrap the file in a Spring Resource
            FileSystemResource audioResource = new FileSystemResource(audioFile);

            // 3. Send to Gemini
            return chatClient.prompt()
                    .user(u -> u
                            .text("Please transcribe this audio content into text.")
                            .media(MimeTypeUtils.parseMimeType("audio/mpeg"), audioResource)
                    )
                    .call()
                    .content();
        } finally {
            // 4. Clean up the temporary files from your hard drive
            if (tempFile.exists()) tempFile.delete();
            if (audioFile != null && audioFile.exists() && audioFile != tempFile) {
                audioFile.delete();
            }
        }
    }

    // Helper method to rip audio from video
    private File extractAudio(File videoFile) throws Exception {
        File target = Files.createTempFile("extracted-", ".mp3").toFile();

        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(128000);
        audio.setChannels(2);
        audio.setSamplingRate(44100);

        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat("mp3");
        attrs.setAudioAttributes(audio);

        Encoder encoder = new Encoder();
        encoder.encode(new MultimediaObject(videoFile), target, attrs);

        return target;
    }
}