package com.vlc.maeummal.domain.template.template2.service;


import com.vlc.maeummal.domain.template.template2.dto.Template2RequestDTO;
import com.vlc.maeummal.domain.template.template2.dto.Template2ResponseDTO;
import com.vlc.maeummal.domain.template.common.StoryCardEntity;
import com.vlc.maeummal.domain.template.template2.entity.Template2Entity;
import com.vlc.maeummal.domain.template.common.StoryCardRepository;
import com.vlc.maeummal.domain.template.template2.repository.Template2Repository;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
}
