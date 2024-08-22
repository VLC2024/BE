package com.vlc.maeummal.domain.template.template1.service;

import com.vlc.maeummal.domain.lesson.dto.WordDTO;
import com.vlc.maeummal.domain.template.template1.dto.Template1DTO;
import com.vlc.maeummal.domain.template.template1.entity.Template1Entity;
import com.vlc.maeummal.domain.template.template1.repository.Template1Repository;
import com.vlc.maeummal.domain.word.dto.WordSetRequestDTO;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.repository.WordRepository;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import com.vlc.maeummal.global.enums.TemplateType;
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
    private final UserAuthorizationConverter userAuthorizationConverter;

    @Transactional
    public Template1DTO createTemplate() {
        Template1Entity template = Template1Entity.builder().build();
        Template1Entity savedTemplate = template1Repository.save(template);
        return convertToDTO(savedTemplate);
    }

    @Transactional
    public List<Template1DTO> getAllTemplates() {
        List<Template1Entity> templates = template1Repository.findAll();
        return templates.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Template1DTO getTemplateById(Long templateId) {
        Optional<Template1Entity> optionalTemplate = template1Repository.findById(templateId);
        if (optionalTemplate.isEmpty()) {
            throw new RuntimeException("Template1Entity not found with id: " + templateId);
        }
        return convertToDTO(optionalTemplate.get());
    }

    @Transactional
    public Template1DTO addRandomWordsToTemplate(Long templateId) {
        Template1Entity template = template1Repository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template1Entity not found with id: " + templateId));

        List<WordEntity> allWords = wordRepository.findAll();
        if (allWords.size() < 3) {
            throw new RuntimeException("Not enough WordEntities to select 3 random words.");
        }

        Collections.shuffle(allWords);
        List<WordEntity> randomWords = allWords.subList(0, 3);

        for (WordEntity word : randomWords) {
            // 단어가 이미 다른 템플릿에 연결되어 있는지 확인
            if (word.getTemplate1Entity() == null) {
                // 양방향 연관 관계 설정
                word.setTemplate1Entity(template); // WordEntity에 template1Entity 설정
                template.getWordEntities().add(word); // Template1Entity에 WordEntity 추가
            } else {
                // 이미 다른 템플릿에 연결된 경우 처리 로직 (예: 무시하거나, 로그를 남김)
                System.out.println("WordEntity already associated with another template: " + word.getId());
            }
        }

        // template과 word 엔티티들을 데이터베이스에 저장
        template1Repository.save(template);
        wordRepository.saveAll(randomWords);

        return convertToDTO(template);
    }

    // 내가 만든 템플릿만 보기
    @Transactional
    public List<Template1DTO> getTemplatesByCreaterId(Long createrId) {
        List<Template1Entity> templates = template1Repository.findByCreaterId(createrId);
        return templates.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
                .imageNum(3)
                .templateType(TemplateType.TEMPLATE1)
                .createrId(userAuthorizationConverter.getCurrentUserId())
                .build();
    }

}
