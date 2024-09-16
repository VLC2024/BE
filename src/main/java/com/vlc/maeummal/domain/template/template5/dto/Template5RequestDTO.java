package com.vlc.maeummal.domain.template.template5.dto;

import com.vlc.maeummal.domain.template.template5.entity.WordCardEntity;
import com.vlc.maeummal.domain.word.dto.WordSetResponseDTO;
import com.vlc.maeummal.global.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class Template5RequestDTO {
    // 나의 단어장 들고오기 -> wordSetEntity userID로 조회
    // 단어장 DTO : 선택한 단어장 조회하고 요청하기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    /*public static class GetSelectedWordSetDTO{
        List<WordSetResponseDTO.GetWordSetDTO> wordSetList;
    }*/
    public static class GetTemplate5DTO{
//        Long temp5Id;
        String title;
        Integer level;
        Long wordSetId;
//        List<Long> wordSetIdList;
    }
//    @Builder
//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class GetWordCardDTO{
//        Long wordCardId;
//
//        Long wordId;
//        String image;
//        String meaning;
//        String description;
//        Long wordSetId;
//
//        public static Template5RequestDTO.GetWordCardDTO getWordCardDTO(Template5RequestDTO.GetWordCardDTO wordCardDTO){
//            return GetWordCardDTO.builder()
//                    .wordCardId(wordCardDTO.wordCardId)
//                    .wordId(wordCardDTO.wordId)
//                    .image(wordCardDTO.image)
//                    .description(wordCardDTO.description)
//                    .meaning(wordCardDTO.meaning)
//                    .wordSetId(wordCardDTO.wordSetId)
//                    .build();
//        }
//    }
}
