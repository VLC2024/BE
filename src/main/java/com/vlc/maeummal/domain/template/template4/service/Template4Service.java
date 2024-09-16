package com.vlc.maeummal.domain.template.template4.service;

import com.vlc.maeummal.domain.template.common.StoryCardEntity;
import com.vlc.maeummal.domain.template.common.StoryCardRepository;
import com.vlc.maeummal.domain.template.template4.dto.Template4RequestDTO;
import com.vlc.maeummal.domain.template.template4.dto.Template4ResponseDTO;
import com.vlc.maeummal.domain.template.template4.entity.Template4Entity;
import com.vlc.maeummal.domain.template.template4.repository.Template4Repository;
import com.vlc.maeummal.domain.template.template5.dto.Template5ResponseDTO;
import com.vlc.maeummal.domain.template.template5.entity.Template5Entity;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Template4Service {
    private final Template4Repository template4Repository;
    private final StoryCardRepository storyCardRepository;
    private final UserAuthorizationConverter userAuthorizationConverter;

    public Template4Entity createTemplate4(Template4RequestDTO.GetTemplate4DTO requestDTO, List<Template4RequestDTO.GetStoryCard> storyCardDTOList) {
        Template4Entity template4Entity = Template4Entity.builder()
                .description(requestDTO.getDescription())
                .hint(requestDTO.getHint())
                .imageNum(requestDTO.getStoryCardEntityList().size())
                .storyCardEntityList(new ArrayList<>())
                .type(requestDTO.getType())
                .build();
        template4Entity.setTitle(requestDTO.getTitle());
        template4Entity.setCreaterId(userAuthorizationConverter.getCurrentUserId());
        template4Entity.setLevel(requestDTO.getLevel());

        Template4Entity savedTemplate4 = template4Repository.save(template4Entity);

        for (Template4RequestDTO.GetStoryCard storyCardDTO : storyCardDTOList) {
            StoryCardEntity storyCardEntity = StoryCardEntity.builder()
                    .image(storyCardDTO.getImage())
                    .answerNumber(storyCardDTO.getAnswerNumber())
                    .description(storyCardDTO.getDescription())
                    .template4(savedTemplate4)
                    .build();

            savedTemplate4.getStoryCardEntityList().add(storyCardEntity);
        }

        storyCardRepository.saveAll(savedTemplate4.getStoryCardEntityList());

        return savedTemplate4;
    }

    public Template4ResponseDTO.GetTemplate4DTO getTemplate4(Long template4Id) {
        Template4Entity template4Entity = template4Repository.findById(template4Id)
                .orElseThrow(() -> new EntityNotFoundException("템플릿을 찾을 수 없습니다."));
        return Template4ResponseDTO.GetTemplate4DTO.convertTemplate4DTO(template4Entity);
    }
    public List<Template4ResponseDTO.GetTemplate4DTO> getAllTemplate4() {
        List<Template4Entity> template4EntityList = template4Repository.findAll();
        return template4EntityList.stream()
                .map(template4 -> Template4ResponseDTO.GetTemplate4DTO.convertTemplate4DTO(template4))
                .collect(Collectors.toList());
    }
    // 템플릿 삭제 메소드
    public void deleteTemplate4(Long template4Id) {
        Template4Entity template4Entity = template4Repository.findById(template4Id)
                .orElseThrow(() -> new EntityNotFoundException("템플릿을 찾을 수 없습니다."));

        // 관련된 스토리 카드 삭제
        storyCardRepository.deleteAll(template4Entity.getStoryCardEntityList());

        // 템플릿 삭제
        template4Repository.delete(template4Entity);
    }

    // 템플릿 수정 메소드
    public Template4Entity updateTemplate4(Long template4Id, Template4RequestDTO.GetTemplate4DTO requestDTO, List<Template4RequestDTO.GetStoryCard> storyCardDTOList) {
        Template4Entity template4Entity = template4Repository.findById(template4Id)
                .orElseThrow(() -> new EntityNotFoundException("템플릿을 찾을 수 없습니다."));

        // 템플릿 정보 수정
        template4Entity.setTitle(requestDTO.getTitle());
        template4Entity.setDescription(requestDTO.getDescription());
        template4Entity.setHint(requestDTO.getHint());
        template4Entity.setLevel(requestDTO.getLevel());
        template4Entity.setImageNum(storyCardDTOList.size());
        template4Entity.setType(requestDTO.getType());

        // 기존 스토리 카드 삭제 및 새로운 스토리 카드 추가
        storyCardRepository.deleteAll(template4Entity.getStoryCardEntityList());
        template4Entity.getStoryCardEntityList().clear();

        for (Template4RequestDTO.GetStoryCard storyCardDTO : storyCardDTOList) {
            StoryCardEntity storyCardEntity = StoryCardEntity.builder()
                    .image(storyCardDTO.getImage())
                    .answerNumber(storyCardDTO.getAnswerNumber())
                    .description(storyCardDTO.getDescription())
                    .template4(template4Entity)
                    .build();

            template4Entity.getStoryCardEntityList().add(storyCardEntity);
        }

        storyCardRepository.saveAll(template4Entity.getStoryCardEntityList());

        // 수정된 템플릿 저장
        return template4Repository.save(template4Entity);
    }


}
