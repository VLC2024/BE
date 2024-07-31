package com.vlc.maeummal.domain.template.template5.dto;

import com.vlc.maeummal.domain.template.template5.entity.Template5Entity;
import com.vlc.maeummal.domain.word.dto.WordSetResponseDTO;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import com.vlc.maeummal.global.enums.Category;
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
    public static class GetWordIdListDTO{
        Long temp5_id;

        List<Long> wordIdList;
    }

    // 단어 DTO : 랜덤으로 골라진 단어 카드 3개 보여주기
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetWordListDTO{
        Long temp5_id;

        List<WordSetResponseDTO.GetWordDTO> wordList;
    }
}
