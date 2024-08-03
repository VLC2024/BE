package com.vlc.maeummal.domain.template.template3.dto;

import com.vlc.maeummal.domain.template.template3.entity.ImageCardEntity;
import com.vlc.maeummal.domain.template.template3.entity.Template3Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class Template3ResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTemplate3DTO {
        List<GetImageCardDTO> imageCardList;
        String description;
        Integer imageNum;

        public static GetTemplate3DTO getTemplate3DTO(Template3Entity template3) {
            List<GetImageCardDTO> imageCardDTOList = template3.getImageCardEntityList().stream()
                    .map(GetImageCardDTO::getImageCardDTO).toList();
            return GetTemplate3DTO.builder()
                    .imageCardList(imageCardDTOList)
                    .description(template3.getDescription())
                    .imageNum(template3.getImageNum())
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetImageCardDTO{

        Long imageCardId;
        String image;
        String adjective;
        String noun;

        public static Template3ResponseDTO.GetImageCardDTO getImageCardDTO (ImageCardEntity imageCard) {
            return GetImageCardDTO.builder()
                    .imageCardId(imageCard.getId())
                    .image(imageCard.getImage())
                    .adjective(imageCard.getAdjective())
                    .noun(imageCard.getNoun())
                    .build();
        }
    }

}
