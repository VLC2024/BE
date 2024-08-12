package com.vlc.maeummal.domain.feedback.dto;

import com.vlc.maeummal.domain.feedback.entity.FeedbackCardEntity;
import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import com.vlc.maeummal.global.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FeedbackResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetFeedbackDTO {

        Long id;
        TemplateType templateType;
        String aiFeedback;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
        Long studentId;
        String title;

        public static FeedbackResponseDTO.GetFeedbackDTO getFeedback(FeedbackEntity feedbackEntity, String title) {
            return GetFeedbackDTO.builder()
                    .id(feedbackEntity.getId())
                    .templateType(feedbackEntity.getTemplateType())
                    .aiFeedback(feedbackEntity.getAiFeedback())
                    .createdAt(feedbackEntity.getCreatedAt())
                    .studentId(feedbackEntity.getStudent().getMemberId())
                    .title(title)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedbackCardDTO {
         Long id;
         String image;
        String adjective;
        String noun;
        String meaning;
        String description;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetFeedbackDetailDTO {

        private Long id;
        private Long templateId;
        private String aiFeedback;
        private Long studentId;
        private Long teacherId;
        private TemplateType templateType;
        private Integer imageNum;
        private List<FeedbackCardDTO> correctFeedbackCards;
        private List<FeedbackCardDTO> studentFeedbackCards;


        public static GetFeedbackDetailDTO convertToFeedbackDetail(FeedbackEntity feedbackEntity) {
            return FeedbackResponseDTO.GetFeedbackDetailDTO.builder()
                    .id(feedbackEntity.getId())
                    .templateId(feedbackEntity.getTemplateId())
                    .aiFeedback(feedbackEntity.getAiFeedback())
                    .studentId(feedbackEntity.getStudent().getMemberId())
                    .teacherId(feedbackEntity.getTeacher().getMemberId())
                    .templateType(feedbackEntity.getTemplateType())
                    .imageNum(feedbackEntity.getImageNum())
                    .correctFeedbackCards(convertToCardDtoList(feedbackEntity.getCorrectFeedbackCards()))
                    .studentFeedbackCards(convertToCardDtoList(feedbackEntity.getStudentFeedbackCards()))
                    .build();
        }
    }

        // FeedbackCardEntity 리스트를 FeedbackCardDTO 리스트로 변환
        private static List<FeedbackResponseDTO.FeedbackCardDTO> convertToCardDtoList(List<FeedbackCardEntity> feedbackCardEntities) {
            return feedbackCardEntities.stream()
                    .map(card -> FeedbackCardDTO.builder()
                            .id(card.getId())
                            .image(card.getImage())
                            .adjective(card.getAdjective())
                            .noun(card.getNoun())
                            .meaning(card.getMeaning())
                            .description(card.getDescription())
                            .build())
                    .collect(Collectors.toList());
        }








        /**
         *
         * 1차 제출 내용
         * template3 학생이 작성한 데이터 리스트 가져오기 & 수업 템플릿 id
         *
         * { 1: "정답1", 2: "정답2", 3: "정답3"}
         * 정담여부 -> 그냥 놔두기
         *
         * 오답 -> 오답 문제 번호 ex 2 / 문제 id
         *
         * */

        /**
         *
         * 2차 제출 내용
         * template3 학생이 작성한 데이터 리스트 가져오기 & 수업 템플릿 id
         * 정담여부 -> 그냥 놔두기
         *
         * 오답 -> 오답 문제 번호 ex 2 / 문제 id
         *
         * */
    }

