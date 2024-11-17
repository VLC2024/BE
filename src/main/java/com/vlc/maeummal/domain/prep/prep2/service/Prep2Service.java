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
                "Do not include any English translations, pronunciations, or romanizations. Return only pure Korean words without any additional explanations." +
                "The words should be selected in a way that they can form a natural sentence where a noun performs a verb described by an adverb. " +
                "For example, '[명사] 이/가 [부사] [동사]' should form a natural sentence. Return only pure Korean words without any additional explanations.";


        // 중복 단어를 피하도록 요청하는 프롬프트 작성
        String prompt = String.format(
                "Generate 4 unique Korean nouns, 4 unique Korean verbs, and 4 unique Korean adverbs related to the category \"%s\". " +
                        "Try to include a variety of less common words and avoid very simple or frequently used words. " +
                        "Your goal is to introduce new vocabulary in each attempt." +
                        "Do not include any English pronunciation or translation. Respond in the following JSON format: " +
                        "{ \"noun1\": \"\", \"noun2\": \"\", \"noun3\": \"\", \"noun4\": \"\", \"verb1\": \"\", \"verb2\": \"\", \"verb3\": \"\", \"verb4\": \"\", \"adv1\": \"\", \"adv2\": \"\", \"adv3\": \"\", \"adv4\": \"\" }",
                category
        );

        // ChatGPT API 호출
        String response = chatGPTService.generateText(systemMessage + "\n\n" + prompt);

        // JSON 파싱하여 단어 맵 반환
        return parseWordsFromJson(response);
    }



    // JSON 파싱 함수
    private Map<String, String> parseWordsFromJson(String jsonResponse) {
        Map<String, String> wordMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            for (int i = 1; i <= 4; i++) {
                // 괄호 안의 영어 발음 부분을 제거 (정규식 사용)
                String noun = jsonNode.get("noun" + i).asText().replaceAll("\\s*\\([^)]*\\)", "");
                String verb = jsonNode.get("verb" + i).asText().replaceAll("\\s*\\([^)]*\\)", "");
                String adv = jsonNode.get("adv" + i).asText().replaceAll("\\s*\\([^)]*\\)", "");

                // 발음 제거 후 결과를 맵에 저장
                wordMap.put("noun" + i, noun.trim());
                wordMap.put("verb" + i, verb.trim());
                wordMap.put("adv" + i, adv.trim());
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

        return String.format("%s 이/가 %s %s", noun, adv, verb);
    }

    // GPT를 사용해 한국어 문장을 자연스러운 영어로 번역하는 메서드
    public String translateToEnglish(String koreanText) {
        // GPT에게 번역 요청 프롬프트
        String prompt = String.format("Please translate the following sentence into natural English: \"%s\"", koreanText);

        // GPT에게 번역 요청
        String translatedText = chatGPTService.generateText(prompt);

        // 번역된 문장 반환
        return translatedText;
    }

}
