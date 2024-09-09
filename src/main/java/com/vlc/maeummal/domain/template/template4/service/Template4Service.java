package com.vlc.maeummal.domain.template.template4.service;

import com.vlc.maeummal.domain.template.common.StoryCardEntity;
import com.vlc.maeummal.domain.template.common.StoryCardRepository;
import com.vlc.maeummal.domain.template.template4.dto.Template4RequestDTO;
import com.vlc.maeummal.domain.template.template4.dto.Template4ResponseDTO;
import com.vlc.maeummal.domain.template.template4.entity.Template4Entity;
import com.vlc.maeummal.domain.template.template4.repository.Template4Repository;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Template4Service {
    private final Template4Repository template4Repository;
    private final StoryCardRepository storyCardRepository;
    private final UserAuthorizationConverter userAuthorizationConverter;

    public Template4Entity createTemplate4(Template4RequestDTO.GetTemplate4DTO requestDTO, List<Template4RequestDTO.GetStoryCard> storyCardDTOList){
        Template4Entity template4Entity = Template4Entity.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .hint(requestDTO.getHint())
                .imageNum(requestDTO.getStoryCardEntityList().size())
                .storyCardEntityList(new ArrayList<>())
                .type(requestDTO.getType())
                .build();
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

}
