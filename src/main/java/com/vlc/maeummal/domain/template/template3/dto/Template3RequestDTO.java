package com.vlc.maeummal.domain.template.template3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class Template3RequestDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTemplate3DTO {

        String description;
//        String hint;
        Integer imageNum;

        public static Template3RequestDTO.GetTemplate3DTO getTemplate3DTO(Template3RequestDTO.GetTemplate3DTO template3DTO) {
            return GetTemplate3DTO.builder()
                    .description(template3DTO.description)
                    .imageNum(template3DTO.imageNum)
                    .build();
        }

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetImageCardDTO {

//        String meaning;
        String image;
//        String prompt;
        String description;
        String adjective;
        String noun;

        public static Template3RequestDTO.GetImageCardDTO getImageCardDTO(Template3RequestDTO.GetImageCardDTO imageCardDTO) {
            return GetImageCardDTO.builder()
//                    .meaning(imageCardDTO.meaning)
                    .image(imageCardDTO.image)
                    .description(imageCardDTO.description)
                    .adjective(imageCardDTO.adjective)
                    .noun(imageCardDTO.noun)
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
