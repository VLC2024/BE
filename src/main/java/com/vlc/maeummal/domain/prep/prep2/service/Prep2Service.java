package com.vlc.maeummal.domain.prep.prep2.service;

import com.vlc.maeummal.domain.prep.prep2.dto.Prep2RequestDTO;
import com.vlc.maeummal.domain.prep.prep2.dto.Prep2ResponseDTO;
import com.vlc.maeummal.domain.prep.prep2.entity.Prep2Entity;
import com.vlc.maeummal.domain.prep.prep2.repository.Prep2Repository;
import com.vlc.maeummal.global.enums.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class Prep2Service {
    private final Prep2Repository prep2Repository;

    @Autowired
    public Prep2Service(Prep2Repository prep2Repository) {
        this.prep2Repository = prep2Repository;
    }
//    private final GptApiClient gptApiClient;
//
//    @Autowired
//    public Prep2Service(GptApiClient gptApiClient) {
//        this.gptApiClient = gptApiClient;
//    }

    public Prep2ResponseDTO.getPromptDTO getPrompt(String category) {
        // 임의의 형용사, 부사, 명사를 생성하는 로직 (실제 GPT API와 연계하지 않고 랜덤 값으로 대체)
        String prompt = generatePrompt(category);
        String noun1 = generateRandomWord("noun");
        String noun2 = generateRandomWord("noun");
        String noun3 = generateRandomWord("noun");
        String verb1 = generateRandomWord("verb");
        String verb2 = generateRandomWord("verb");
        String verb3 = generateRandomWord("verb");
        String adv1 = generateRandomWord("adverb");
        String adv2 = generateRandomWord("adverb");
        String adv3 = generateRandomWord("adverb");

        // ResponseDTO 객체 생성 및 반환
        return new Prep2ResponseDTO.getPromptDTO(prompt, noun1, noun2, noun3, verb1, verb2, verb3, adv1, adv2, adv3);
    }

    public void saveCategory(Prep2RequestDTO.CategoryRequestDTO requestDTO) {
        // 새로운 엔티티 생성 및 저장
        Prep2Entity newEntity = new Prep2Entity();
        newEntity.setCategory(Category.valueOf(requestDTO.getCategory().toString()));
        prep2Repository.save(newEntity);
    }

    // 임의의 형용사, 부사, 명사를 생성하는 메서드 (실제 GPT API와 연계하지 않고 랜덤 값으로 대체)
    private String generateRandomWord(String type) {
        // 예시로 랜덤 단어 생성하는 로직
        Random random = new Random();
        String[] words;

        switch (type) {
            case "noun":
                words = new String[]{"apple", "car", "house", "dog", "cat"};
                break;
            case "verb":
                words = new String[]{"run", "eat", "sleep", "jump", "write"};
                break;
            case "adverb":
                words = new String[]{"quickly", "slowly", "carefully", "happily", "quietly"};
                break;
            default:
                return "";
        }

        return words[random.nextInt(words.length)];
    }

    // 카테고리를 기반으로 prompt 생성하는 메서드 (여기서는 단순 예시로 정적 문자열 반환)
    private String generatePrompt(String category) {
        return String.format("%s 주제에 맞는 형용사, 부사, 명사를 각각 3개씩 랜덤으로 뽑아줘", category);
    }
}
