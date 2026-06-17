package com.audio.audio_transcibe.controllers;

import com.audio.audio_transcibe.services.TranscriptionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/audio")
@CrossOrigin(origins = "*")
public class AudioController {

    // 1. Declare the service variable
    private final TranscriptionService transcriptionService;

    // 2. Inject the service via the constructor
    public AudioController(TranscriptionService transcriptionService) {
        this.transcriptionService = transcriptionService;
    }

    @PostMapping("/transcribe")
    public String transcribe(@RequestParam("file") MultipartFile file) {
        try {
            // 3. Now this variable is recognized!
            return transcriptionService.processFile(file);
        } catch (Exception e) {
            e.printStackTrace(); // Good for debugging
            return "Error processing file: " + e.getMessage();
        }
    }
}