package com.vlc.maeummal.domain.template.template4.service;

import com.vlc.maeummal.domain.template.common.StoryCardEntity;
import com.vlc.maeummal.domain.template.common.StoryCardRepository;
import com.vlc.maeummal.domain.template.template4.dto.Template4RequestDTO;
import com.vlc.maeummal.domain.template.template4.entity.Template4Entity;
import com.vlc.maeummal.domain.template.template4.repository.Template4Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Template4Service {
    private final Template4Repository template4Repository;
    private final StoryCardRepository storyCardRepository;

    public Template4Entity createTemplate4(Template4RequestDTO.GetTemplate4DTO requestDTO, List<Template4RequestDTO.GetStoryCard> storyCardDTOList){
        Template4Entity template4Entity = Template4Entity.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .hint(requestDTO.getHint())
                .storyCardEntityList(new ArrayList<>())
                .type(requestDTO.getType())
                .build();

        Template4Entity savedTemplate4 = template4Repository.save(template4Entity);

        for (Template4RequestDTO.GetStoryCard storyCardDTO : storyCardDTOList) {
            StoryCardEntity storyCardEntity = StoryCardEntity.builder()
                    .image(storyCardDTO.getImage())
                    .answerNumber(storyCardDTO.getAnswerNumber())
                    .template4(savedTemplate4)
                    .build();

            savedTemplate4.getStoryCardEntityList().add(storyCardEntity);
        }

        storyCardRepository.saveAll(savedTemplate4.getStoryCardEntityList());

        return savedTemplate4;
    }

}
