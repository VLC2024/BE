package com.vlc.maeummal.domain.template.template4.dto;

import com.vlc.maeummal.domain.template.common.entity.StoryCardEntity;
import com.vlc.maeummal.domain.template.template4.entity.Template4Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Template4ResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTemplate4DTO {
        Long templateId;
        Integer heartCount;
        String title;
        Integer level;
        String description;
        String hint;
        List<Template4ResponseDTO.GetStoryCardDTO> storyCardEntityList;

        public static Template4ResponseDTO.GetTemplate4DTO convertTemplate4DTO(Template4Entity template4) {
            // 가변 리스트로 변환하여 이야기 카드 리스트 생성
            List<Template4ResponseDTO.GetStoryCardDTO> storyCardDTOList = template4.getStoryCardEntityList().stream()
                    .map(Template4ResponseDTO.GetStoryCardDTO::convertStoryCardDTO)
                    .collect(Collectors.toCollection(ArrayList::new));

            // 이야기 카드 리스트를 랜덤으로 셔플
            Collections.shuffle(storyCardDTOList);

            return Template4ResponseDTO.GetTemplate4DTO.builder()
                    .templateId(template4.getId())
                    .heartCount(2)
                    .title(template4.getTitle())
                    .level(template4.getLevel())
                    .description(template4.getDescription())
                    .hint(template4.getHint())
                    .storyCardEntityList(storyCardDTOList)
                    .build();
        }


    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetStoryCardDTO{

        Long storyCardId;
        String image;
        Integer answerNumber;
        String description;

        public static Template4ResponseDTO.GetStoryCardDTO convertStoryCardDTO (StoryCardEntity storyCard) {
            return GetStoryCardDTO.builder()
                    .storyCardId(storyCard.getId())
                    .image(storyCard.getImage())
                    .answerNumber(storyCard.getAnswerNumber())
                    .description(storyCard.getDescription())
                    .build();
        }
    }
}
