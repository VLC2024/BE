package com.vlc.maeummal.domain.template.template5.dto;

import com.vlc.maeummal.domain.template.template5.entity.Template5Entity;
import com.vlc.maeummal.domain.template.template5.entity.WordCardEntity;
import com.vlc.maeummal.domain.word.dto.WordSetResponseDTO;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.global.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class Template5ResponseDTO {
    //단어장 DTO : 나의 (저장한) 단어장 보여주기
    /*@Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetSavedWordSetListDTO{
        List<WordSetResponseDTO.GetWordSetDTO> wordSetIdList;
    }*/

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTemplate5DTO{
        Long temp5Id;
        String title;
        Integer level;
        TemplateType templateType;
        List<GetWordCardDTO> wordCardList;
        public static GetTemplate5DTO getTemplate5DTO(Template5Entity template5){
            List<GetWordCardDTO> wordCardDTOList = template5.getWordListEntities().stream()
                    .map(GetWordCardDTO::getWordCardDTO).toList();
            return GetTemplate5DTO.builder()
                    .temp5Id(template5.getId())
                    .title(template5.getTitle())
                    .level(template5.getLevel())
                    .templateType(TemplateType.TEMPLATE5)
                    .wordCardList(wordCardDTOList)
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetWordCardDTO{
        Long wordId;
        String image;
        String meaning;
        String description;
        Long wordSetId;

        public static Template5ResponseDTO.GetWordCardDTO getWordCardDTO(WordCardEntity wordCard){
            return GetWordCardDTO.builder()
                    .wordId(wordCard.getWordId())
                    .image(wordCard.getImage())
                    .description(wordCard.getDescription())
                    .meaning(wordCard.getMeaning())
                    .wordSetId(wordCard.getWordsetId())
                    .build();
        }
    }
    // Template5 DTO


}
