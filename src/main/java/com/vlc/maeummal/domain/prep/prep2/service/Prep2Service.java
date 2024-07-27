package com.vlc.maeummal.domain.prep.prep2.service;

import com.vlc.maeummal.domain.prep.prep2.dto.Prep2RequestDTO;
import com.vlc.maeummal.domain.prep.prep2.dto.Prep2ResponseDTO;
import com.vlc.maeummal.global.aws.service.S3Service;
import com.vlc.maeummal.global.openAi.chatGPT.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Prep2Service {
    @Autowired
    private ChatGPTService chatGPTService;

    public Prep2ResponseDTO.generatedWordsDTO generateWords(String category){
        List<String> nouns = generateNouns(category);
        List<String> verbs = generateVerbs(category);
        List<String> advs = generateAdvs(category);

        return Prep2ResponseDTO.generatedWordsDTO.builder()
                .noun1(nouns.get(0))
                .noun2(nouns.get(1))
                .noun3(nouns.get(2))
                .noun4(nouns.get(3))
                .verb1(verbs.get(0))
                .verb2(verbs.get(1))
                .verb3(verbs.get(2))
                .verb4(verbs.get(3))
                .adv1(advs.get(0))
                .adv2(advs.get(1))
                .adv3(advs.get(2))
                .adv4(advs.get(3))
                .build();
    }
    private List<String> generateNouns(String category) {
        List<String> nouns = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            String prompt = "Generate an noun related to the category: \"" + category + "\"";
            String generatedNouns = chatGPTService.generateText(prompt);
            nouns.add(generatedNouns);
        }

        return nouns;
    }
    private List<String> generateVerbs(String category) {
        List<String> verbs = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            String prompt = "Generate an verb related to the category: \"" + category + "\"";
            String generatedVerbs = chatGPTService.generateText(prompt);
            verbs.add(generatedVerbs);
        }

        return verbs;
    }
    private List<String> generateAdvs(String category) {
        List<String> adverbs = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            String prompt = "Generate an adverb related to the category: \"" + category + "\"";
            String generatedAdvs = chatGPTService.generateText(prompt);
            adverbs.add(generatedAdvs);
        }

        return adverbs;
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
