package com.vlc.maeummal.domain.prep.prep2.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlc.maeummal.domain.prep.prep2.dto.Prep2RequestDTO;
import com.vlc.maeummal.domain.prep.prep2.dto.Prep2ResponseDTO;
import com.vlc.maeummal.global.enums.Category;
import com.vlc.maeummal.global.openAi.chatGPT.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Prep2Service {
    @Autowired
    private ChatGPTService chatGPTService;

    public Prep2ResponseDTO.generatedWordsDTO generateWordsByCategory(Category category) {
        // 카테고리에 맞는 단어들을 생성
        Map<String, String> wordsMap = generateWordsMapByCategoryWithRetry(category);

        // DTO로 변환하여 반환
        Prep2ResponseDTO.generatedWordsDTO responseDTO = new Prep2ResponseDTO.generatedWordsDTO();
        responseDTO.setNouns(Arrays.asList(wordsMap.get("noun1"), wordsMap.get("noun2"), wordsMap.get("noun3"), wordsMap.get("noun4")));
        responseDTO.setVerbs(Arrays.asList(wordsMap.get("verb1"), wordsMap.get("verb2"), wordsMap.get("verb3"), wordsMap.get("verb4")));
        responseDTO.setAdverbs(Arrays.asList(wordsMap.get("adv1"), wordsMap.get("adv2"), wordsMap.get("adv3"), wordsMap.get("adv4")));

        return responseDTO;
    }

    // 카테고리에 맞는 단어들을 생성하고 null 값이나 빈 값이 있으면 재시도
    // 카테고리에 맞는 단어들을 생성하고 null 값이나 빈 값이 있으면 재시도
    private Map<String, String> generateWordsMapByCategoryWithRetry(Category category) {
        Map<String, String> wordsMap;

        // 최대 3번 재시도하도록 설정
        int attempts = 3;
        do {
            wordsMap = generateWordsMapByCategory(category);

            // null 또는 빈 값이 있는지 확인하고, 있으면 재시도
            if (isValidWordsMap(wordsMap)) {
                return wordsMap; // 유효한 값이면 반환
            }

            attempts--;
        } while (attempts > 0);

        // 유효하지 않은 경우 null 반환
        return null;
    }


    private boolean isValidWordsMap(Map<String, String> wordsMap) {
        // Map에 포함된 단어 중 null이거나 빈 값이 있는지 확인
        for (int i = 1; i <= 4; i++) {
            if (wordsMap.get("noun" + i) == null || wordsMap.get("noun" + i).isEmpty()) {
                return false;
            }
            if (wordsMap.get("verb" + i) == null || wordsMap.get("verb" + i).isEmpty()) {
                return false;
            }
            if (wordsMap.get("adv" + i) == null || wordsMap.get("adv" + i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private Map<String, String> generateWordsMapByCategory(Category category) {
        // 시스템 메시지
        String systemMessage = "You are a private tutor who specializes in teaching language to children with intellectual disabilities. " +
                "Your student is learning Korean. If the student uses inappropriate or offensive language, you must correct them by pointing out " +
                "that they have used unsuitable words. Additionally, your goal is to expand the student's vocabulary as much as possible. " +
                "For any given category, provide a wide range of Korean words to help the student learn and understand different vocabulary. " +
                "(한국어로 답변을 작성해야해.)";

        // 사용자 프롬프트 개선
        String prompt = String.format(
                "Generate 4 unique nouns, 4 unique verbs, and 4 unique adverbs related to the category \"%s\". " +
                        "Try to include a variety of less common words and avoid very simple or frequently used words. " +
                        "Your goal is to introduce new vocabulary in each attempt. Respond in the following JSON format: " +
                        "{ \"noun1\": \"\", \"noun2\": \"\", \"noun3\": \"\", \"noun4\": \"\", \"verb1\": \"\", \"verb2\": \"\", \"verb3\": \"\", \"verb4\": \"\", \"adv1\": \"\", \"adv2\": \"\", \"adv3\": \"\", \"adv4\": \"\" }",
                category
        );

        // ChatGPT API 호출
        String response = chatGPTService.generateText(systemMessage + "\n\n" + prompt);

        return parseWordsFromJson(response);
    }


    // JSON 파싱 함수
    private Map<String, String> parseWordsFromJson(String jsonResponse) {
        Map<String, String> wordMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // ```json 코드 블록을 제거하여 JSON 파싱이 가능하도록 수정
            if (jsonResponse.startsWith("```")) {
                jsonResponse = jsonResponse.substring(jsonResponse.indexOf("{"), jsonResponse.lastIndexOf("}") + 1);
            }

            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            for (int i = 1; i <= 4; i++) {
                wordMap.put("noun" + i, jsonNode.get("noun" + i).asText());
                wordMap.put("verb" + i, jsonNode.get("verb" + i).asText());
                wordMap.put("adv" + i, jsonNode.get("adv" + i).asText());
            }
        } catch (Exception e) {
            // 오류 처리
            System.err.println("Error parsing JSON response: " + e.getMessage());
        }

        return wordMap;
    }


    // 이미지 생성 prompt 만드는 메서드
    public String makeSentence(Prep2RequestDTO.GetWordDTO requestDTO) {
        // 요청된 데이터에서 명사, 동사 및 부사를 가져옴
        String noun = requestDTO.getNoun();
        String verb = requestDTO.getVerb();
        String adv = requestDTO.getAdv();

        return String.format("%s %s %s", noun, verb, adv);
    }

}
