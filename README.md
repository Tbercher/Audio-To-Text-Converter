Gemini Media Transcriber
A full-stack application built with Spring Boot 3.x, Java 25, and Google Gemini AI that transcribes both audio and video files into text.
The application automatically detects video uploads, "rips" the audio stream using FFmpeg (via JAVE2), and sends the processed audio to Gemini for high-accuracy transcription.

Features
Audio Transcription: Supports .mp3, .wav, .m4a, etc.
Video-to-Text: Supports .mp4, .mov, .avi, etc. (Auto-audio extraction).
Multimodal AI: Powered by Google Gemini (1.5/2.5/3.5 Flash).
Clean UI: Simple, interactive web interface with progress feedback.
Automated Cleanup: Temporary files are deleted immediately after processing.

Tech Stack
Backend: Java 25, Spring Boot 3.4+, Spring AI
Processing: JAVE2 (FFmpeg wrapper for Java)
AI Model: Google Gemini API (AI Studio)
Frontend: HTML5, CSS3, JavaScript (Fetch API)

src/main/
├── java/com/audio/audio_transcibe/
│   ├── controllers/
│   │   └── AudioController.java      # REST Endpoints
│   ├── services/
│   │   └── TranscriptionService.java # Logic for audio ripping & AI calls
│   └── AudioTranscibeApplication.java
└── resources/
    ├── static/
    │   └── index.html               # Frontend UI
    └── application.properties       # App Config

Getting Started
1. Prerequisites
JDK 25 (Download from Oracle or Adoptium).
Maven 3.9+.
Google AI Studio API Key: Get one for free at aistudio.google.com.

2. Configuration
Open src/main/resources/application.properties and configure your API key and file limits:

# Gemini API Key
spring.ai.google.genai.api-key=YOUR_GEMINI_API_KEY
# Model Selection (gemini-1.5-flash or gemini-3.5-flash)
spring.ai.google.genai.chat.options.model=gemini-1.5-flash
# File Upload Limits (Essential for Video)
spring.servlet.multipart.max-file-size=100MB
spring.servlet.multipart.max-request-size=100MB

3. Build and run
Simply run through IDE and access the basic UI at http://localhost:8080/index.html

Notes:
Due to Gemeni free usage requirements during peak hours you might want to switch to the pro model if flash is overloaded. Any file larger than 1 mb make sure spring.servlet.multipart.max-file-size is set to 50 or 100 MB.

This was a cool little project I ran using Gemenis AI studio, its was pretty limited though due to the limitations of the free api and high demand.
    
