package com.vlc.maeummal.domain.prep.prep1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlc.maeummal.domain.prep.prep1.dto.CompletionRequestDto;
import com.vlc.maeummal.domain.prep.prep1.dto.CompletionResponseDto;
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

    // 랜덤 질문 -> 나중에 작성할 답안, 보기도 연계되어있도록 생성

    public String generateImage(String prompt) {
        // OpenAI DALL-E API 호출
        // API endpoint와 body 형식에 맞게 호출
        // 반환된 이미지를 처리하여 URL 또는 base64 형태로 반환

        String apiUrl = "https://api.openai.com/v1/images/generations";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model","dall-e-3");
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

        String apiUrl = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // 메시지 포맷 맞추기
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.add(userMessage);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", messages);
        body.put("max_tokens", 50);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            String generatedText = root.path("choices").get(0).path("message").path("content").asText();
            return generatedText.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

//        Map<String, Object> body = new HashMap<>();
//        body.put("model", "gpt-3.5-turbo");
//        body.put("prompt", prompt);
//        body.put("max_tokens", 50);
//
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
//        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);
//
//        try {
//            JsonNode root = objectMapper.readTree(response.getBody());
//            String generatedText = root.path("choices").get(0).path("text").asText();
//            return generatedText.trim();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }

}
