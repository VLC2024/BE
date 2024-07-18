package com.vlc.maeummal.domain.prep.prep1.service;

import com.vlc.maeummal.domain.prep.prep1.dto.Prep1DTO;
import com.vlc.maeummal.domain.prep.prep1.entity.Prep1Entity;
import com.vlc.maeummal.domain.prep.prep1.repository.Prep1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private Prep1Repository prep1Repository;

    @Autowired
    private Prep1Mapper prep1Mapper;

    public List<String> generateOptions(String correctAnswer) {
        List<String> options = new ArrayList<>();
        options.add(correctAnswer);

        // OpenAI GPT-4 API를 사용하여 임의의 보기를 생성
        for (int i = 0; i < 3; i++) {  // 세 개의 임의의 보기를 생성
            String prompt = "Generate a similar phrase to: \"" + correctAnswer + "\"";
            String generatedOption = openAIService.generateText(prompt);
            options.add(generatedOption);
        }

        Collections.shuffle(options);
        return options;
    }

    public Prep1DTO savePrep1(Prep1DTO prep1DTO) {
        Prep1Entity prep1 = prep1Mapper.toEntity(prep1DTO);
        Prep1Entity savedPrep1 = prep1Repository.save(prep1);
        return prep1Mapper.toDTO(savedPrep1);
    }

    public boolean checkAnswer(int questionId, String selectedOption) {
        Optional<Prep1Entity> optionalPrep1 = prep1Repository.findById(questionId);
        if (optionalPrep1.isPresent()) {
            Prep1Entity prep1 = optionalPrep1.get();
            return prep1.getAnswer().equals(selectedOption);
        }
        return false;
    }
}
