package com.vlc.maeummal.domain.template.template2.service;


import com.vlc.maeummal.domain.template.template2.dto.Template2RequestDTO;
import com.vlc.maeummal.domain.template.template2.dto.Template2ResponseDTO;
import com.vlc.maeummal.domain.template.common.entity.StoryCardEntity;
import com.vlc.maeummal.domain.template.template2.entity.Template2Entity;
import com.vlc.maeummal.domain.template.common.repository.StoryCardRepository;
import com.vlc.maeummal.domain.template.template2.repository.Template2Repository;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Template2Service {

    private final Template2Repository template2Repository;
    private final StoryCardRepository storyCardRepository;
    private final UserAuthorizationConverter userAuthorizationConverter;

    public Template2ResponseDTO.GetTemplate2DTO getTemplate2(Long template2Id) {
        Template2Entity template2Entity = template2Repository.findById(template2Id)
                .orElseThrow(() -> new EntityNotFoundException("템플릿을 찾을 수 없습니다."));
        return Template2ResponseDTO.GetTemplate2DTO.convertTemplate2DTO(template2Entity);
    }

    public List<Template2ResponseDTO.GetTemplate2DTO> getAllTemplate2() {
        List<Template2Entity> template2EntityList = template2Repository.findAll();
        return template2EntityList.stream()
                .map(Template2ResponseDTO.GetTemplate2DTO::convertTemplate2DTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Template2Entity saveTemplate2WithStoryCards(Template2RequestDTO.GetTemplate2DTO template2DTO, List<Template2RequestDTO.GetStoryCard> storyCardDTOList) {
        Template2Entity template2Entity = Template2Entity.builder()
                .title(template2DTO.getTitle())
                .description(template2DTO.getDescription())
                .hint(template2DTO.getHint())
                .imageNum(template2DTO.getImageNum())
                .storyCardEntityList(new ArrayList<>())
                .type(template2DTO.getType())
                .build();
        template2Entity.setCreaterId(userAuthorizationConverter.getCurrentUserId());
        template2Entity.setLevel(template2DTO.getLevel());

        Template2Entity savedTemplate2 = template2Repository.save(template2Entity);

        for (Template2RequestDTO.GetStoryCard storyCardDTO : storyCardDTOList) {
            StoryCardEntity storyCardEntity = StoryCardEntity.builder()
                    .image(storyCardDTO.getImage())
                    .answerNumber(storyCardDTO.getAnswerNumber())
                    .template2(savedTemplate2)
                    .build();

            savedTemplate2.getStoryCardEntityList().add(storyCardEntity);
        }

        storyCardRepository.saveAll(savedTemplate2.getStoryCardEntityList());

        return savedTemplate2;
    }
    @Transactional
    public Template2Entity updateTemplate2WithStoryCards(Long template2Id,
                                                         Template2RequestDTO.GetTemplate2DTO template2DTO,
                                                         List<Template2RequestDTO.GetStoryCard> storyCardDTOList) {


        // Find the existing template
        Template2Entity template2Entity = template2Repository.findById(template2Id)
                .orElseThrow(() -> new EntityNotFoundException("템플릿을 찾을 수 없습니다."));
        //exception : not creater
        if(userAuthorizationConverter.getCurrentUserId() != template2Entity.getCreaterId()) {
            throw new AccessDeniedException("자신의 템플릿 수업만 수정할 수 있습니다.");

        }

        // Update the template fields
        template2Entity.setTitle(template2DTO.getTitle());
        template2Entity.setDescription(template2DTO.getDescription());
        template2Entity.setHint(template2DTO.getHint());
        template2Entity.setImageNum(template2DTO.getImageNum());
        template2Entity.setType(template2DTO.getType());
        template2Entity.setLevel(template2DTO.getLevel());

        // Clear the existing story cards and save the new ones
        template2Entity.getStoryCardEntityList().clear();
        for (Template2RequestDTO.GetStoryCard storyCardDTO : storyCardDTOList) {
            StoryCardEntity storyCardEntity = StoryCardEntity.builder()
                    .image(storyCardDTO.getImage())
                    .answerNumber(storyCardDTO.getAnswerNumber())
                    .template2(template2Entity)
                    .build();

            template2Entity.getStoryCardEntityList().add(storyCardEntity);
        }

        // Save updated template and associated story cards
        storyCardRepository.saveAll(template2Entity.getStoryCardEntityList());
        return template2Repository.save(template2Entity);
    }
    @Transactional
    public void deleteTemplate2(Long template2Id) {
        // Find the existing template
        Template2Entity template2Entity = template2Repository.findById(template2Id)
                .orElseThrow(() -> new EntityNotFoundException("템플릿을 찾을 수 없습니다."));

        //Exception: not creator
        if (!userAuthorizationConverter.getCurrentUserId().equals(template2Entity.getCreaterId())) {
            throw new AccessDeniedException("자신의 템플릿만 삭제할 수 있습니다.");
        }

        // Delete the associated story cards
        storyCardRepository.deleteAll(template2Entity.getStoryCardEntityList());

        // Delete the template itself
        template2Repository.delete(template2Entity);
    }
}

