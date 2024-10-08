package com.vlc.maeummal.domain.feedback.dto;

import com.vlc.maeummal.global.enums.TemplateType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class FeedbackRequestDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAnswer {

        Long templateId;
        List<String> answerList;
        Long studentId;
        @Enumerated()
        TemplateType templateType;


        public static FeedbackRequestDTO.GetAnswer getStudentAnswerDTO(FeedbackRequestDTO.GetAnswer answerDTO) {
            return GetAnswer.builder()
                    .studentId(answerDTO.studentId)
                    .templateId(answerDTO.templateId)
                    .answerList(answerDTO.answerList)
                    .templateType(answerDTO.templateType)
                    .build();
        }
    }




//        }
        /**
         * {
         *     "TemplateId":
         *
         *     "ImageCardId": [
         *         {
         *             "id" : "각 문제 아이디"
         *             "studentAnswer" : "학생이 작성한 답",
         *             "isSolved" : "맞췄는지 여부" -> 필요없을지도
         *         },
         *         {
         *             "id" : "각 문제 아이디"
         *             "studentAnswer" : "학생이 작성한 답",
         *             "isSolved" : "맞췄는지 여부" -> 필요없을지도
         *         }
         *     ]
         * }
         *
         *
         * */

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
