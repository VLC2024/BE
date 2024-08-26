package com.vlc.maeummal.domain.member.dto;

import com.vlc.maeummal.domain.feedback.dto.FeedbackResponseDTO;
import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.global.enums.Gender;
import com.vlc.maeummal.global.enums.Iq;
import com.vlc.maeummal.global.enums.Role;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    // 마이페이지 교사 정보 DTO
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTeacherInfo{
        private String profileImage;
        private String name;
        private String email;
        private String phoneNum;
        //        private String password;
//        private List<StudentResponseDTO.GetStudentDTO> studentDTOS;

        public static TeacherDTO.GetTeacherInfo getTeacherInfo(MemberEntity member) {
            return GetTeacherInfo.builder()
                    .profileImage(member.getImage())
                    .name(member.getName())
                    .email(member.getEmail())
                    .phoneNum(member.getPhoneNumber())
                    .build();

        }
    }
}
