package com.vlc.maeummal.domain.member.dto;

import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.global.enums.Gender;
import com.vlc.maeummal.global.enums.Iq;
import com.vlc.maeummal.global.enums.Role;
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
    private Gender gender;
    private Role role = Role.STUDENT;
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

    // 마이페이지 학생 정보 DTO
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetStudentInfo{
        private String profileImage;
        private String name;
        private String email;
        private String phoneNum;
//        private String password;
        private Iq iq;
        private String pinCode;

        public static StudentDTO.GetStudentInfo getStudentInfo(MemberEntity member) {
            return GetStudentInfo.builder()
                    .profileImage(member.getImage())
                    .name(member.getName())
                    .email(member.getEmail())
                    .phoneNum(member.getPhoneNumber())
                    .iq(member.getIq())
                    .pinCode(member.getPinCode())
                    .build();

        }
    }
}
