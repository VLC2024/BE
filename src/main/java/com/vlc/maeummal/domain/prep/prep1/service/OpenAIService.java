package com.vlc.maeummal.domain.prep.prep1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Configuration
@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateImage(String prompt) {
        // OpenAI DALL-E API 호출
        // API endpoint와 body 형식에 맞게 호출
        // 반환된 이미지를 처리하여 URL 또는 base64 형태로 반환

        String apiUrl = "https://api.openai.com/v1/images/generations";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("prompt", prompt);
        body.put("n", 1);
        body.put("size", "512x512");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            String imageUrl = root.path("data").get(0).path("url").asText();
            return imageUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String generateText(String prompt) {
        // OpenAI GPT-4 API 호출
        // API endpoint와 body 형식에 맞게 호출
        // 반환된 텍스트를 처리하여 필요한 형태로 반환

        String apiUrl = "https://api.openai.com/v1/engines/davinci-codex/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("prompt", prompt);
        body.put("max_tokens", 50);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            String generatedText = root.path("choices").get(0).path("text").asText();
            return generatedText.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
