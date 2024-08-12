package com.vlc.maeummal.domain.template.template2.dto;

import com.vlc.maeummal.domain.template.template2.entity.StoryCardEntity;
import com.vlc.maeummal.domain.template.template2.entity.Template2Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class Template2ResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTemplate2DTO {
        Long templateId;
        String title;
        String description;
        String hint;
        List<GetStoryCardDTO> storyCardEntityList;

        public static Template2ResponseDTO.GetTemplate2DTO convertTemplate2DTO(Template2Entity template2) {
            List<Template2ResponseDTO.GetStoryCardDTO> storyCardDTOList = template2.getStoryCardEntityList().stream()
                    .map(Template2ResponseDTO.GetStoryCardDTO::convertStoryCardDTO).toList();
            return GetTemplate2DTO.builder()
                    .templateId(template2.getId())
                    .title(template2.getTitle())
                    .description(template2.getDescription())
                    .hint(template2.getHint())
                    .storyCardEntityList(storyCardDTOList)
                    .build();
        }


    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetStoryCardDTO{
        /**
         *  private Long id;
         *
         *     @Column(nullable = true)
         *     private String image;
         *
         *     @Column(nullable=true)
         *     private Integer answerNumber;
         *
         *     @ManyToOne(fetch = FetchType.LAZY)
         *     @JoinColumn(name = "template2Id")
         *     private Template2Entity template2;
         *
         * */

        Long storyCardId;
        String image;
        Integer answerNumber;

        public static Template2ResponseDTO.GetStoryCardDTO convertStoryCardDTO (StoryCardEntity storyCard) {
            return GetStoryCardDTO.builder()
                    .storyCardId(storyCard.getId())
                    .image(storyCard.getImage())
                    .answerNumber(storyCard.getAnswerNumber())
                    .build();
        }
    }
}
