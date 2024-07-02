package com.vlc.maeummal.domain.testApi.controller;

import com.vlc.maeummal.domain.testApi.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AiController {
    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/image")
    public ResponseEntity<?> generateImage(@RequestBody String prompt) {
        return new ResponseEntity<>(aiService.generatePicture(prompt), HttpStatus.OK);
    }

    @PostMapping("/hello")
    public ResponseEntity<?> printHello(@RequestBody String prompt) {
        return new ResponseEntity<>(prompt, HttpStatus.OK);
    }
}
