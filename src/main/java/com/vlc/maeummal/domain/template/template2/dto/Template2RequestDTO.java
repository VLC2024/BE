package com.vlc.maeummal.domain.template.template2.dto;

import com.vlc.maeummal.domain.template.template2.entity.StoryCardEntity;
import com.vlc.maeummal.domain.template.template3.dto.Template3RequestDTO;
import com.vlc.maeummal.global.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class Template2RequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTemplate2DTO {

        String title;
        String description;
        String hint;
        Integer imageNum;
        List<Template2RequestDTO.GetStoryCard> storyCardEntityList;
        TemplateType type;

        public static Template2RequestDTO.GetTemplate2DTO convert2DTO(Template2RequestDTO.GetTemplate2DTO template2DTO) {
            return GetTemplate2DTO.builder()
                    .title(template2DTO.title)
                    .description(template2DTO.description)
                    .hint(template2DTO.hint)
                    .imageNum(template2DTO.imageNum)
                    .storyCardEntityList(template2DTO.storyCardEntityList)
                    .type(template2DTO.type)
                    .build();

        }


    }
    /**
     *
     * @Builder
     *     @Getter
     *     @NoArgsConstructor
     *     @AllArgsConstructor
     *     public static class GetImageCardDTO {
     *
     *         String image;
     *         String adjective;
     *         String noun;
     *        String hint;
     *
     *         public static Template3RequestDTO.GetImageCardDTO getImageCardDTO(Template3RequestDTO.GetImageCardDTO imageCardDTO) {
     *             return GetImageCardDTO.builder()
     *                     .image(imageCardDTO.image)
     *                     .adjective(imageCardDTO.adjective)
     *                     .noun(imageCardDTO.noun)
     *                     .hint(imageCardDTO.hint)
     *                     .build();
     *         }
     *
     *     }
     *
     * */
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetStoryCard {

        String image;
        Integer answerNumber;

        public static Template2RequestDTO.GetStoryCard convertStoryCard(Template2RequestDTO.GetStoryCard storyCard) {
            return Template2RequestDTO.GetStoryCard.builder()
                    .image(storyCard.image)
                    .answerNumber(storyCard.answerNumber)
                    .build();
        }

    }
}
