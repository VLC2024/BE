package com.vlc.maeummal.global.openAi.chatGPT.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlc.maeummal.global.config.ServiceConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class ChatGPTService {
    @Autowired
    private ServiceConfig serviceConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public String generateText(String prompt) {

        // OpenAI GPT-4 API 호출
        // API endpoint와 body 형식에 맞게 호출
        // 반환된 텍스트를 처리하여 필요한 형태로 반환

        String apiUrl = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(serviceConfig.apiKey);

        // 메시지 포맷 맞추기
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.add(userMessage);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", messages);
        body.put("max_tokens", 300);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = serviceConfig.restTemplate().exchange(apiUrl, HttpMethod.POST, request, String.class);

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
