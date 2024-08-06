package com.vlc.maeummal.domain.member.dto;

import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.global.enums.Gender;
import com.vlc.maeummal.global.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String image;
    private LocalDate birthDay;
    private Gender gender;
    private Role role = Role.TEACHER;
    private String organization;
    private List<FeedbackEntity> feedbackEntityListForTeacher;
    private List<MemberEntity> matchingStudent;

    public static TeacherDTO getTeacher(TeacherDTO teacherDTO){
        return TeacherDTO.builder()
                .id(teacherDTO.id)
                .email(teacherDTO.email)
                .password(teacherDTO.password)
                .name(teacherDTO.name)
                .phoneNumber(teacherDTO.phoneNumber)
                .image(teacherDTO.image)
                .birthDay(teacherDTO.birthDay)
                .gender(teacherDTO.gender)
                .role(teacherDTO.role)
                .organization(teacherDTO.organization)
                .feedbackEntityListForTeacher(teacherDTO.feedbackEntityListForTeacher)
                .matchingStudent(teacherDTO.matchingStudent)
                .build();
    }
}
