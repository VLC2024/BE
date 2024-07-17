package com.vlc.maeummal.domain.prep.prep1.controller;

import com.vlc.maeummal.domain.prep.prep1.dto.Prep1DTO;
import com.vlc.maeummal.domain.prep.prep1.service.OpenAIService;
import com.vlc.maeummal.domain.prep.prep1.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@Controller
@RestController("prep1")
public class ImageTextController {

    private OpenAIService openAIService;
    private QuizService quizService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateContent(@RequestParam String category) {
        String imageUrl = openAIService.generateImage(category);
        String generatedText = openAIService.generateText("Describe the image: " + imageUrl);

        // 문장 나누기
        String[] parts = generatedText.split(" ", 2);
        String firstPart = parts[0];
        String secondPart = parts[1];

        // 보기 생성
        List<String> options = quizService.generateOptions(secondPart);

        // Prep1DTO 생성
        Prep1DTO prep1DTO = new Prep1DTO();
        prep1DTO.setSituation(category);
        prep1DTO.setDetailedSituation(generatedText);
        prep1DTO.setAnswer(secondPart);
        prep1DTO.setExplanation("");  // 설명이 필요하다면 생성하거나 빈 값으로 설정
        prep1DTO.setUid(prep1DTO.getUid());  // UID는 실제 사용자의 식별자로 설정
        prep1DTO.setOption1(options.get(0));
        prep1DTO.setOption2(options.get(1));
        prep1DTO.setOption3(options.get(2));
        prep1DTO.setOption4(options.get(3));
        prep1DTO.setImageUrl(imageUrl);

        // 데이터 저장
        Prep1DTO savedPrep1DTO = quizService.savePrep1(prep1DTO);

        // 결과 반환
        Map<String, Object> response = new HashMap<>();
        response.put("imageUrl", imageUrl);
        response.put("firstPart", firstPart);
        response.put("options", options);
        response.put("savedData", savedPrep1DTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-answer")
    public ResponseEntity<?> checkAnswer(@RequestBody Map<String, Object> payload) {
        int questionId = (Integer) payload.get("questionId");
        String selectedOption = (String) payload.get("selectedOption");

        boolean isCorrect = quizService.checkAnswer(questionId, selectedOption);

        Map<String, Object> response = new HashMap<>();
        response.put("isCorrect", isCorrect);
        return ResponseEntity.ok(response);
    }
}
