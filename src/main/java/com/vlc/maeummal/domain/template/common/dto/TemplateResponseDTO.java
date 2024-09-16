package com.vlc.maeummal.domain.template.common.dto;

import com.vlc.maeummal.domain.template.common.entity.TemplateEntity;
import com.vlc.maeummal.global.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.vlc.maeummal.global.converter.DateTimeConverter.*;

public class TemplateResponseDTO {


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTemplates {
        Long templateId;
        String title;
        String templateName;
        String createdAt;
        Integer level;
        Long createrId;

        public static TemplateResponseDTO.GetTemplates convertTemplateResponseDTO(TemplateEntity template) {
            return GetTemplates.builder()
                    .templateId(template.getId()) // TOdo temp1의 경우 integer
                    .title(template.getTitle() != null ? template.getTitle() : "기본 제목") // 디폴트 값 설정
                    .templateName(convertTypeToString(template.getType()))
                    .createdAt(convertStringFromLocalDateTime(template.getCreatedAt()))
                    .level(template.getLevel() != null ? template.getLevel() : 0) // 디폴트 값 설정
                    .createrId(template.getCreaterId())
                    .build();
        }
        public static String convertTypeToString(TemplateType type) {
            switch (type) {
                case TEMPLATE1:
                    return "카테고리 분류하기";
                // 다른 case 문 추가
                case TEMPLATE2:
                    return "이미지 순서 배열하기";
                case TEMPLATE3:
                    return "감정 표현";
                case TEMPLATE4:
                    return "이야기 순서 배열하기";
                case TEMPLATE5:
                    return "어휘 카드 매칭 게임";
                default:
                    return "알 수 없는 템플릿";
            }

        }



    }
}
