package com.vlc.maeummal.domain.feedback.dto;

import com.vlc.maeummal.domain.feedback.entity.FeedbackCardEntity;
import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.template.template1.repository.Template1Repository;
import com.vlc.maeummal.global.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
//            MemberEntity student = feedbackEntity.getStudent();
//            Long studentId = (student != null) ? student.getMemberId() : null; // Null 체크 후 ID 설정


//            return GetFeedbackDTO.builder()
//                    .id(feedbackEntity.getId())
//                    .templateType(feedbackEntity.getTemplateType())
//                    .aiFeedback(feedbackEntity.getAiFeedback())
//                    .createdAt(feedbackEntity.getCreatedAt())
//                    .studentId(studentId) // Null 체크 후 studentId 설정
//                    .title(title)
//                    .build();
            // Define default values
            Long defaultId = -1L;
            TemplateType defaultTemplateType = TemplateType.UNKNOWN;  // Assuming UNKNOWN is a valid enum value
            String defaultAiFeedback = "No feedback available";
            LocalDateTime defaultCreatedAt = LocalDateTime.now();
            LocalDateTime defaultUpdatedAt = LocalDateTime.now();
            Long defaultStudentId = -1L;
            String defaultTitle = "Untitled";

            // Fetch student and check for null
            MemberEntity student = feedbackEntity.getStudent();
            Long studentId = (student != null) ? student.getMemberId() : defaultStudentId;

            return GetFeedbackDTO.builder()
                    .id(feedbackEntity.getId() != null ? feedbackEntity.getId() : defaultId)
                    .templateType(feedbackEntity.getTemplateType() != null ? feedbackEntity.getTemplateType() : defaultTemplateType)
                    .aiFeedback(feedbackEntity.getAiFeedback() != null ? feedbackEntity.getAiFeedback() : defaultAiFeedback)
                    .createdAt(feedbackEntity.getCreatedAt() != null ? feedbackEntity.getCreatedAt() : defaultCreatedAt)
                    .updatedAt(feedbackEntity.getUpdatedAt() != null ? feedbackEntity.getUpdatedAt() : defaultUpdatedAt)
                    .studentId(studentId)
                    .title(title != null ? title : defaultTitle)
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
        Integer answerNumber;
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
        private String imageNum;
        private String solution;
        private List<FeedbackCardDTO> correctFeedbackCards;
        private List<FeedbackCardDTO> studentFeedbackCards;

        Integer answerNum; // 맞춘 개수
        List<Boolean> correctnessList; // 각 학생 답안의 정답 여부




        public static GetFeedbackDetailDTO convertToFeedbackDetail(FeedbackEntity feedbackEntity) {
            List<Boolean> correctnessList = calculateCorrectness(feedbackEntity.getTemplateType(), feedbackEntity.getCorrectFeedbackCards(), feedbackEntity.getStudentFeedbackCards());
            Integer answerNum = calculateAnswerNum(correctnessList);

            return GetFeedbackDetailDTO.builder()
                    .id(feedbackEntity.getId())
                    .templateId(feedbackEntity.getTemplateId())
                    .aiFeedback(feedbackEntity.getAiFeedback())
                    .studentId(feedbackEntity.getStudent().getMemberId())
                    .teacherId(feedbackEntity.getTeacher().getMemberId())
                    .templateType(feedbackEntity.getTemplateType())
                    .imageNum(feedbackEntity.getImageNum().toString())
                    .solution(feedbackEntity.getSolution())
                    .correctFeedbackCards(convertToCardDtoList(feedbackEntity.getCorrectFeedbackCards()))
                    .studentFeedbackCards(convertToCardDtoList(feedbackEntity.getStudentFeedbackCards()))
                    .correctnessList(correctnessList)
                    .answerNum(answerNum)
                    .build();
        }



        private static List<FeedbackResponseDTO.FeedbackCardDTO> convertToCardDtoList(List<FeedbackCardEntity> feedbackCardEntities) {
            return feedbackCardEntities.stream()
                    .map(card -> FeedbackCardDTO.builder()
                            .id(card.getId())
                            .image(card.getImage())
                            .adjective(card.getAdjective())
                            .noun(card.getNoun())
                            .meaning(card.getMeaning())
                            .description(card.getDescription())
                            .answerNumber(card.getAnswerNumber())

                            .build())
                    .collect(Collectors.toList());
        }

    }
    public static List<Boolean> calculateCorrectness(TemplateType templateType, List<FeedbackCardEntity> correctCards, List<FeedbackCardEntity> studentCards) {
        List<Boolean> correctnessList = new ArrayList<>();

        for (int i = 0; i < studentCards.size(); i++) {
            FeedbackCardEntity studentCard = studentCards.get(i);
            FeedbackCardEntity correctCard = correctCards.get(i);
            boolean isCorrect = false;

            // 템플릿 타입에 따라 비교 로직을 다르게 적용
            switch (templateType) {
                case TEMPLATE1:
                    // Template1: 를 비교

                    break;
                case TEMPLATE2:
                    // Template2: answerNumber를 비교
                    isCorrect = studentCard.getAnswerNumber().equals(correctCard.getAnswerNumber());
                    break;
                case TEMPLATE3:
                    // Template3: adjective를 비교
                    isCorrect = studentCard.getAdjective().equals(correctCard.getAdjective());
                    break;
                case TEMPLATE4:
                    // Template4: answerNumber를 비교
                    isCorrect = studentCard.getAnswerNumber().equals(correctCard.getAnswerNumber());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported template type in FeedbackResponseDTO: " + templateType);
            }

            correctnessList.add(isCorrect);
        }

        return correctnessList;
    }


    public static Integer calculateAnswerNum(List<Boolean> correctnessList) {
        return (int) correctnessList.stream().filter(Boolean::booleanValue).count();
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

