package com.vlc.maeummal.domain.template.template4.dto;

import com.vlc.maeummal.global.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class Template4RequestDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTemplate4DTO {

        String title;
        String description; // 해설
        String hint; // 힌트
        Integer imageNum; // 이야기 카드 갯수
        List<Template4RequestDTO.GetStoryCard> storyCardEntityList;
        TemplateType type = TemplateType.TEMPLATE4;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetStoryCard {

        String image;
        Integer answerNumber;
        String description;

    }
}
