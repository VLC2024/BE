package com.vlc.maeummal.domain.member.service;

import com.vlc.maeummal.domain.member.dto.StudentDTO;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MemberRepository memberRepository;

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
    public List<StudentDTO> getStudentsByTeacherId(Long teacherId) {
        // 주어진 teacherId와 매칭된 학생들을 조회
        List<MemberEntity> students = memberRepository.findByTeacher_MemberId(teacherId);

        // 조회된 학생 엔티티들을 DTO로 변환하여 리스트로 반환
        return students.stream()
                .map(this::convertToStudentDTO)
                .collect(Collectors.toList());
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
