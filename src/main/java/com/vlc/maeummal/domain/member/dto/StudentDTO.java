package com.vlc.maeummal.domain.member.dto;

import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import com.vlc.maeummal.global.enums.Iq;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String image;
    private LocalDate birthDay;
    private String gender;
    private String token;
    private Integer score;
    private String pinCode;
    private Iq iq;
    private Long teacherId;  // 교사 ID를 추가적으로 가질 경우
    private List<FeedbackEntity> feedbackEntityListForStudent;

    public static StudentDTO getStudent(StudentDTO studentDTO) {
        return StudentDTO.builder()
                .id(studentDTO.id)
                .email(studentDTO.email)
                .password(studentDTO.password)
                .name(studentDTO.name)
                .phoneNumber(studentDTO.phoneNumber)
                .image(studentDTO.image)
                .birthDay(studentDTO.birthDay)
                .gender(studentDTO.gender)
                .token(studentDTO.token)
                .score(studentDTO.score)
                .pinCode(studentDTO.pinCode)
                .iq(studentDTO.iq)
                .teacherId(studentDTO.teacherId)
                .feedbackEntityListForStudent(studentDTO.feedbackEntityListForStudent)
                .build();
    }
}
