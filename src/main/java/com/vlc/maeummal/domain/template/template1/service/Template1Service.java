package com.vlc.maeummal.domain.template.template1.service;

import com.vlc.maeummal.domain.lesson.dto.WordDTO;
import com.vlc.maeummal.domain.template.template1.dto.Template1DTO;
import com.vlc.maeummal.domain.template.template1.entity.Template1Entity;
import com.vlc.maeummal.domain.template.template1.repository.Template1Repository;
import com.vlc.maeummal.domain.word.dto.WordSetRequestDTO;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Template1Service {

    private final Template1Repository template1Repository;
    private final WordRepository wordRepository;

    @Transactional
    public Template1DTO createTemplate() {
        Template1Entity template = Template1Entity.builder().build();
        Template1Entity savedTemplate = template1Repository.save(template);
        return convertToDTO(savedTemplate);
    }

    @Transactional
    public Template1DTO addRandomWordsToTemplate(Integer templateId) {
        Optional<Template1Entity> optionalTemplate = template1Repository.findById(templateId);
        if (optionalTemplate.isEmpty()) {
            throw new RuntimeException("Template1Entity not found with id: " + templateId);
        }

        Template1Entity template = optionalTemplate.get();

        List<WordEntity> allWords = wordRepository.findAll();
        if (allWords.size() < 3) {
            throw new RuntimeException("Not enough WordEntities to select 3 random words.");
        }

        Collections.shuffle(allWords);
        List<WordEntity> randomWords = allWords.subList(0, 3);

        template.getWordEntities().addAll(randomWords);
        randomWords.forEach(word -> word.setWordSet(null));

        Template1Entity updatedTemplate = template1Repository.save(template);
        return convertToDTO(updatedTemplate);
    }

    private Template1DTO convertToDTO(Template1Entity template) {
        List<WordSetRequestDTO.GetWordDTO> wordDTOs = Optional.ofNullable(template.getWordEntities())
                .orElse(Collections.emptyList())
                .stream()
                .map(word -> WordSetRequestDTO.GetWordDTO.builder()
                        .meaning(word.getMeaning())
                        .image(word.getImage())
                        .prompt(word.getPrompt())
                        .description(word.getDescription())
                        .build())
                .collect(Collectors.toList());

        return Template1DTO.builder()
                .id(template.getId())
                .words(wordDTOs)
                .build();
    }

}
