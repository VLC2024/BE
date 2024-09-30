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
    public static class GetTemplate5DTO{
        String title;
        Integer level;
        Long wordSetId;
    }
}
