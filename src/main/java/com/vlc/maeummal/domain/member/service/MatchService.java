package com.vlc.maeummal.domain.member.service;

import com.vlc.maeummal.domain.feedback.dto.FeedbackResponseDTO;
import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import com.vlc.maeummal.domain.feedback.repository.FeedbackRepository;
import com.vlc.maeummal.domain.feedback.service.FeedbackService;
import com.vlc.maeummal.domain.member.dto.StudentDTO;
import com.vlc.maeummal.domain.member.dto.StudentResponseDTO;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.repository.MemberReposirotyUsingId;
import com.vlc.maeummal.domain.member.repository.MemberRepository;
import com.vlc.maeummal.domain.template.common.entity.TemplateEntity;
import com.vlc.maeummal.domain.template.common.service.TemplateService;
import com.vlc.maeummal.global.enums.TemplateType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MemberRepository memberRepository;
    private final MemberReposirotyUsingId memberReposirotyUsingId;
    private final FeedbackService feedbackService;
    private final FeedbackRepository feedbackRepository;
    private final TemplateService templateService;

    @Transactional
    public StudentDTO matchStudentWithTeacher(String pinCode, Long teacherId) {
        // 핀코드로 학생을 조회
        Optional<MemberEntity> studentEntityOptional = memberRepository.findByPinCode(pinCode);

        if (studentEntityOptional.isPresent()) {
            MemberEntity studentEntity = studentEntityOptional.get();

            // 선생님 조회
            Optional<MemberEntity> teacherEntityOptional = memberRepository.findById(String.valueOf(teacherId));

            if (teacherEntityOptional.isPresent()) {
                MemberEntity teacherEntity = teacherEntityOptional.get();

                // 학생의 teacher 필드와 teacherId 필드를 업데이트
                studentEntity.setTeacher(teacherEntity);
//                studentEntity.setTeacherId(teacherId);

                // 업데이트된 학생 엔티티를 저장
                memberRepository.save(studentEntity);

                // DTO로 변환하여 반환
                return convertToStudentDTO(studentEntity);
            } else {
                throw new IllegalArgumentException("해당 ID의 선생님을 찾을 수 없습니다.");
            }
        } else {
            throw new IllegalArgumentException("해당 핀코드의 학생을 찾을 수 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO.GetStudentAprroximateDTO> getStudentsByTeacherId(Long teacherId) {
        // 주어진 teacherId와 매칭된 학생들을 조회
        List<MemberEntity> students = memberRepository.findByTeacher_MemberId(teacherId);

        // 조회된 학생 엔티티들을 DTO로 변환하여 리스트로 반환
        return students.stream()
                .map(StudentResponseDTO.GetStudentAprroximateDTO::convertToMatchedStudent)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO.GetStudentAprroximateDTO> getFiveStudentsByTeacherId(Long teacherId) {
        // 주어진 teacherId와 매칭된 학생들을 조회
        List<MemberEntity> students = memberRepository.findByTeacher_MemberId(teacherId);

        // 조회된 학생 엔티티들을 DTO로 변환하여 리스트로 반환
        return students.stream()
                .limit(5)
                .map(StudentResponseDTO.GetStudentAprroximateDTO::convertToMatchedStudent)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO.GetStudentDTO getStudentByStudentId(Long studentId) {
        MemberEntity student = memberReposirotyUsingId.findById(studentId)
                .orElseThrow(() -> new NullPointerException("Student with ID " + studentId + " not found."));
        // 개수 -> level 로 수정.
        List<FeedbackEntity> feedbackEntityListForChart = feedbackRepository.findAllByStudent(student);
        Map<String, Integer> templateChart = getTemplateChart(feedbackEntityListForChart);
        List<FeedbackResponseDTO.GetFeedbackDTO> latestTwoFeedbackDTOs = feedbackRepository.findAllByStudent(student).stream()
                .sorted((f1, f2) -> f2.getCreatedAt().compareTo(f1.getCreatedAt())) // Sort by date in descending order
                .limit(2) // Limit to the latest 2 entries
                .map(feedbackEntity -> FeedbackResponseDTO.GetFeedbackDTO.getFeedback(feedbackEntity, feedbackEntity.getTitle())) // Convert to DTO
                .collect(Collectors.toList());


        return StudentResponseDTO.GetStudentDTO.convertToMatchedStudent(student, latestTwoFeedbackDTOs, templateChart);

    }


    private Map<String, Integer> getTemplateChart(List<FeedbackEntity> feedbackEntityList) {
        Map<TemplateType, String> templateMappings = Map.of(
                TemplateType.TEMPLATE1, "a",
                TemplateType.TEMPLATE2, "b",
                TemplateType.TEMPLATE3, "c",
                TemplateType.TEMPLATE4, "d",
                TemplateType.TEMPLATE5, "e"
        );

        // Initialize the result map with all keys (a, b, c, d, e) and values set to 0
        Map<String, Integer> templateChart = new HashMap<>();
        templateMappings.values().forEach(key -> templateChart.put(key, 0));

        // Update the map based on the actual feedbackEntityList
        Map<String, Integer> counts = feedbackEntityList.stream()
                .collect(Collectors.groupingBy(
                        feedback -> templateMappings.getOrDefault(feedback.getTemplateType(), "unknown"),
                        Collectors.summingInt(e -> 1)
                ));

        // Merge counts into the templateChart, adding the counts to the initialized values
        counts.forEach((key, count) -> {
            if (count >= 10) {
                templateChart.put(key, -1);
            } else {
                templateChart.put(key, count);
            }
        });

        return templateChart;


//        // Update the map based on the actual feedbackEntityList
//        Map<String, Integer> counts = feedbackEntityList.stream()
//                .collect(Collectors.groupingBy(
//                        feedback -> templateMappings.getOrDefault(feedback.getTemplateType(), "unknown"),  // Map TEMPLATE1 -> a, etc.
//                        Collectors.summingInt(e -> 1)  // Count occurrences
//                ));
//
//        // Merge counts into the templateChart, adding the counts to the initialized values
//        counts.forEach(templateChart::put);
//
//        return templateChart;

}


    private StudentDTO convertToStudentDTO(MemberEntity studentEntity) {
        return StudentDTO.builder()
                .id(studentEntity.getMemberId())
                .email(studentEntity.getEmail())
                .password(studentEntity.getPassword())
                .name(studentEntity.getName())
                .phoneNumber(studentEntity.getPhoneNumber())
                .image(studentEntity.getImage())
                .birthDay(studentEntity.getBirthDay())
                .gender(studentEntity.getGender())
                .role(studentEntity.getRole())
//                .token(studentEntity.getToken())
                .score(studentEntity.getScore())
                .pinCode(studentEntity.getPinCode())
                .iq(studentEntity.getIq())
                .teacherId(studentEntity.getTeacher().getMemberId())
                .feedbackEntityListForStudent(studentEntity.getFeedbackEntityListForStudent())
                .build();
    }
}
