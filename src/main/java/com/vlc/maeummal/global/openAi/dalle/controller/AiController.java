package com.vlc.maeummal.global.openAi.dalle.controller;

import com.vlc.maeummal.global.aws.AmazonS3Manager;
import com.vlc.maeummal.global.aws.Uuid;
import com.vlc.maeummal.global.aws.UuidRepository;
import com.vlc.maeummal.global.openAi.Base64DecodedMultipartFile;
import com.vlc.maeummal.global.openAi.chatGPT.service.ChatGPTService;
import com.vlc.maeummal.global.openAi.dalle.service.AiService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class AiController {
    private final AiService aiService;
    private final AmazonS3Manager s3Manager;
    private final UuidRepository uuidRepository;
    @PostMapping("/image")
    public ResponseEntity<?> generateImage(@RequestBody String prompt) {
        String base64ImageData = aiService.generatePicture(prompt); // base64 data
        String imageUrl = null;
        //  Base64 데이터를 MultipartFile로 변환하여 S3에 업로드
        try {
            imageUrl = aiService.uploadImageToS3(base64ImageData);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println( "Failed to upload image");

        }
        System.out.println( "Image uploaded successfully");

        return ResponseEntity.ok().body(imageUrl);
    }
    private final ChatGPTService chatGPTService;

    @PostMapping("/chat")
    public ResponseEntity<String> sendMessage(@RequestBody String prompt) {
        try {
            // ChatGPTService를 통해 텍스트 생성
            String responseText = chatGPTService.generateText(prompt);
            if (responseText != null) {
                return ResponseEntity.ok(responseText);
            } else {
                return ResponseEntity.status(500).body("Error generating text from GPT");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

}