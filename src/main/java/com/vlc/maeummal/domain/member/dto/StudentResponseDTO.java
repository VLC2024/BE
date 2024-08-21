package com.vlc.maeummal.domain.member.dto;

import com.vlc.maeummal.domain.feedback.dto.FeedbackResponseDTO;
import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.template.template3.dto.Template3ResponseDTO;
import com.vlc.maeummal.domain.template.template3.entity.Template3Entity;
import com.vlc.maeummal.global.enums.Iq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

public class StudentResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetStudentDTO {
        Long studentId;
        String name;
        String phoneNumber;
        String iq;
        List<FeedbackResponseDTO.GetFeedbackDTO> feedbackTwo;
        Map<String, Integer> templateChart;
        public static StudentResponseDTO.GetStudentDTO convertToMatchedStudent(
                MemberEntity student, List<FeedbackResponseDTO.GetFeedbackDTO> feedbackList, Map<String, Integer> chart) {
            return GetStudentDTO.builder()
                    .studentId(student.getMemberId())
                    .name(student.getName())
                    .phoneNumber(student.getPhoneNumber())
                    .iq(convertIqToString(student.getIq()))
                    .feedbackTwo(feedbackList)
                    .templateChart(chart)
                    .build();

        }
    }

    public static String convertIqToString(Iq iq) {
        switch (iq) {
            case MILD -> {
                return "지능지수:50~70(경도)";
            }
            case SEVERE -> {
                return "지능지수:35~49(중증도)";
            }
        }

        return "해당 정보를 제공하지 않음";
    }
}
