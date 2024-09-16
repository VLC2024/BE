package com.vlc.maeummal.domain.template.template3.dto;

import com.vlc.maeummal.global.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class Template3RequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTemplate3DTO {

        String title;
        Integer level;
        String description;
        Integer imageNum;
        TemplateType templateType;

        List<String> options;

        public static Template3RequestDTO.GetTemplate3DTO getTemplate3DTO(Template3RequestDTO.GetTemplate3DTO template3DTO) {
            return GetTemplate3DTO.builder()
                    .title(template3DTO.title)
                    .level(template3DTO.level)
                    .description(template3DTO.description)
                    .imageNum(template3DTO.imageNum)
                    .templateType(template3DTO.templateType)
                    .options(template3DTO.options)
                    .build();
        }

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetImageCardDTO {

        String image;
        String adjective;
        String noun;
        String hint;

        public static Template3RequestDTO.GetImageCardDTO getImageCardDTO(Template3RequestDTO.GetImageCardDTO imageCardDTO) {
            return GetImageCardDTO.builder()
                    .image(imageCardDTO.image)
                    .adjective(imageCardDTO.adjective)
                    .noun(imageCardDTO.noun)
                    .hint(imageCardDTO.hint)
                    .build();
        }

    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class  Template3CreationRequestDTO{
        private Template3RequestDTO.GetTemplate3DTO template3DTO;
        private List<Template3RequestDTO.GetImageCardDTO> imageCardDTOList;
    }
}
