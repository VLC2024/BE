package com.vlc.maeummal.domain.prep.prep2.service;

import com.vlc.maeummal.domain.prep.prep2.dto.Prep2RequestDTO;
import com.vlc.maeummal.domain.prep.prep2.dto.Prep2ResponseDTO;
import com.vlc.maeummal.domain.prep.prep2.entity.Prep2Entity;
import com.vlc.maeummal.domain.prep.prep2.repository.Prep2Repository;
import com.vlc.maeummal.global.enums.Category;
import com.vlc.maeummal.global.openAi.chatGPT.service.ChatGPTService;
import com.vlc.maeummal.global.openAi.dalle.service.AiService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class Prep2Service {
    @Autowired
    private ChatGPTService chatGPTService;
    public Prep2ResponseDTO.GeneratedWordsDTO saveDTO(String category){
        List<String> nouns = generateNouns(category);
        List<String> verbs = generateVerbs(category);
        List<String> advs = generateAdvs(category);

        Prep2ResponseDTO.GeneratedWordsDTO responseDTO = Prep2ResponseDTO.GeneratedWordsDTO.builder()
                .noun1(nouns.get(0))
                .noun2(nouns.get(1))
                .noun3(nouns.get(2))
                .verb1(verbs.get(0))
                .verb2(verbs.get(1))
                .verb3(verbs.get(2))
                .adv1(advs.get(0))
                .adv2(advs.get(1))
                .adv3(advs.get(2))
                .build();

        return responseDTO;
    }
    public List<String> generateNouns(String category) {
        List<String> nouns = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String prompt = "Generate an noun related to the category: \"" + category + "\"";
            String generatedNouns = chatGPTService.generateText(prompt);
            nouns.add(generatedNouns);
        }

        return nouns;
    }
    public List<String> generateVerbs(String category) {
        List<String> verbs = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String prompt = "Generate an verb related to the category: \"" + category + "\"";
            String generatedVerbs = chatGPTService.generateText(prompt);
            verbs.add(generatedVerbs);
        }

        return verbs;
    }
    public List<String> generateAdvs(String category) {
        List<String> adverbs = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String prompt = "Generate an adverb related to the category: \"" + category + "\"";
            String generatedAdvs = chatGPTService.generateText(prompt);
            adverbs.add(generatedAdvs);
        }

        return adverbs;
    }


    // 카테고리를 기반으로 prompt 생성하는 메서드 (여기서는 단순 예시로 정적 문자열 반환)
    public String generateSentence(Prep2RequestDTO.GetWordDTO requestDTO) {
        // 요청된 데이터에서 명사, 동사 및 부사를 가져옴
        String noun = requestDTO.getNoun();
        String verb = requestDTO.getVerb();
        String adv = requestDTO.getAdv();

        String sentence = String.format("%s이/가 %s %s", noun, adv, verb);

        return sentence;
    }
}
