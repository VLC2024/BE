package com.vlc.maeummal.domain.template.template5.service;

import com.vlc.maeummal.domain.template.common.TemplateEntity;
import com.vlc.maeummal.domain.template.template1.dto.Template1DTO;
import com.vlc.maeummal.domain.template.template1.entity.Template1Entity;
import com.vlc.maeummal.domain.template.template3.entity.Template3Entity;
import com.vlc.maeummal.domain.template.template5.dto.Template5RequestDTO;
import com.vlc.maeummal.domain.template.template5.dto.Template5ResponseDTO;
import com.vlc.maeummal.domain.template.template5.entity.Template5Entity;
import com.vlc.maeummal.domain.template.template5.entity.WordCardEntity;
import com.vlc.maeummal.domain.template.template5.repository.Template5Repository;
import com.vlc.maeummal.domain.template.template5.repository.WordCardRepository;
import com.vlc.maeummal.domain.word.dto.WordSetResponseDTO;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import com.vlc.maeummal.domain.word.repository.WordRepository;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import com.vlc.maeummal.global.enums.TemplateType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Template5Service {
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private Template5Repository template5Repository;
    @Autowired
    private WordCardRepository wordCardRepository;
    @Autowired
    private  UserAuthorizationConverter userAuthorizationConverter;
    public Template5ResponseDTO.GetTemplate5DTO getTemplate5(Long template5Id) {
        // DB에서 가져와서 DTO로 변환
        Template5ResponseDTO.GetTemplate5DTO template5DTO = Template5ResponseDTO.GetTemplate5DTO.getTemplate5DTO(template5Repository.findById(template5Id).orElseThrow(() -> new EntityNotFoundException("Template not found")));

        // 반환
        return template5DTO;
    }

    public List<Template5ResponseDTO.GetTemplate5DTO> getAllTemplate5() {
        // DB에서 가져와서 DTO로 변환
        List<Template5Entity> template5EntityList = template5Repository.findAll();
        return template5EntityList.stream()
                .map(template5 -> Template5ResponseDTO.GetTemplate5DTO.getTemplate5DTO(template5))
                .collect(Collectors.toList());
    }

    // 1. Long wordSetId -> 단어장에서 단어 Id 추출 List<Long> wordIdList
    public List<Long> getSelectedWordSetList(Long wordSetId) {
        return wordRepository.findByWordSetId(wordSetId)
                .stream()
                .map(WordEntity::getId)  // 각 WordEntity의 ID를 추출
                .collect(Collectors.toList());
    }

    @Transactional
    public Template5ResponseDTO.GetTemplate5DTO randomWords(Template5RequestDTO.GetTemplate5DTO dto, List<Long> wordIdList) {
        // 랜덤으로 3개의 단어 ID를 추출
        List<Long> randomWordIdList = wordIdList.stream()
                .sorted((a, b) -> Double.compare(Math.random(), Math.random()))
                .limit(3)
                .collect(Collectors.toList());

        // 랜덤으로 선택된 단어들로 WordCardEntity 리스트 생성
        List<WordCardEntity> wordCardEntities = randomWordIdList.stream()
                .map(randomWordId -> wordRepository.findById(randomWordId)
                        .map(wordEntity -> WordCardEntity.builder()
                                .wordId(wordEntity.getId())
                                .image(wordEntity.getImage())
                                .meaning(wordEntity.getMeaning())
                                .description(wordEntity.getDescription())
                                .wordsetId(wordEntity.getWordSet().getId())
                                .build())
                        .orElseThrow(() -> new RuntimeException("WordEntity not found for id: " + randomWordId))
                )
                .collect(Collectors.toList());

        // Template5Entity를 생성하고 먼저 저장
        Template5Entity template5Entity = Template5Entity.builder()
                .imageNum(wordCardEntities.size())
                .type(TemplateType.TEMPLATE5)
                .wordListEntities(new ArrayList<>()) // 일단 비어있는 리스트로 초기화
                .build();
        template5Entity.setTitle(dto.getTitle());
        template5Entity.setLevel(dto.getLevel());
        template5Entity.setCreaterId(userAuthorizationConverter.getCurrentUserId());
        
        template5Entity = template5Repository.save(template5Entity);

        // 저장된 Template5Entity의 ID를 가져와서 WordCardEntity에 설정
        for (WordCardEntity wordCardEntity : wordCardEntities) {
            wordCardEntity.setTemp5(template5Entity);
        }

        // WordCardEntity를 저장
        wordCardRepository.saveAll(wordCardEntities);

        // Template5Entity에 WordCardEntity 리스트를 설정
        template5Entity.setWordListEntities(wordCardEntities);

        // 다시 Template5Entity를 저장하여 연관된 WordCardEntity가 저장된 상태로 업데이트
        template5Entity = template5Repository.save(template5Entity);

        // Template5ResponseDTO.GetTemplate5DTO를 반환, ID 포함
        return Template5ResponseDTO.GetTemplate5DTO.getTemplate5DTO(template5Entity);
    }

    @Transactional
    public Template5ResponseDTO.GetTemplate5DTO getRelatedTemplateByTemplateId(Long templateId) {
        // 주어진 템플릿을 가져옴
        WordCardEntity wordCard = wordCardRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("WordCardEntity not found with id: " + templateId));

        // 템플릿에 사용된 낱말 카드 세트 ID를 가져옴
        Long wordSetIdUsedInTemplate = wordCard.getWordsetId();

        // 동일한 낱말 카드 세트를 사용하는 모든 템플릿5ID를 찾음
        List<Long> relatedTemplate5Ids = wordCardRepository.findTemplate5IdByWordsetId(wordSetIdUsedInTemplate);

        // 현재 템플릿 ID를 제외하고 필터링
        relatedTemplate5Ids = relatedTemplate5Ids.stream()
                .filter(id -> !id.equals(templateId))  // 현재 템플릿 ID 제외
                .collect(Collectors.toList());

        // 관련된 템플릿이 없으면 예외 발생
        if (relatedTemplate5Ids.isEmpty()) {
            throw new RuntimeException("No related templates found.");
        }

        // 리스트에서 랜덤으로 하나의 템플릿5ID를 선택
        Long randomTemplate5Id = relatedTemplate5Ids.get(new Random().nextInt(relatedTemplate5Ids.size()));

        // 랜덤으로 선택된 템플릿5ID에 해당하는 템플릿을 조회
        Template5Entity selectedTemplate = template5Repository.findById(randomTemplate5Id)
                .orElseThrow(() -> new RuntimeException("Template5Entity not found with id: " + randomTemplate5Id));

        // DTO로 변환하여 반환
        return Template5ResponseDTO.GetTemplate5DTO.getTemplate5DTO(selectedTemplate);
    }
}
