package com.vlc.maeummal.domain.template.template5.service;

import com.vlc.maeummal.domain.template.template3.entity.Template3Entity;
import com.vlc.maeummal.domain.template.template5.dto.Template5RequestDTO;
import com.vlc.maeummal.domain.template.template5.dto.Template5ResponseDTO;
import com.vlc.maeummal.domain.template.template5.entity.Template5Entity;
import com.vlc.maeummal.domain.template.template5.entity.WordCardEntity;
import com.vlc.maeummal.domain.template.template5.repository.Template5Repository;
import com.vlc.maeummal.domain.template.template5.repository.WordCardRepository;
import com.vlc.maeummal.domain.word.dto.WordSetResponseDTO;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.repository.WordRepository;
import com.vlc.maeummal.global.enums.TemplateType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Template5Service {
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private Template5Repository template5Repository;
    @Autowired
    private WordCardRepository wordCardRepository;

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
    public Template5ResponseDTO.GetTemplate5DTO randomWords(List<Long> wordIdList) {
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
                .type(TemplateType.TEMPLATE5)
                .wordListEntities(new ArrayList<>()) // 일단 비어있는 리스트로 초기화
                .build();

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

}
