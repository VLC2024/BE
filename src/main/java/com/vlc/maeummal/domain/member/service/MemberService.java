package com.vlc.maeummal.domain.member.service;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public MemberEntity create(final MemberEntity memberEntity){
        // memberEntity가 null이면 사용자가 필요한 정보를 입력하지 않음
        if(memberEntity == null){
            throw new RuntimeException("Invalid arguments");
        }

        final String email = memberEntity.getEmail();

        // 이미 존재하는 계정일 경우
        if(memberRepository.existsByEmail(email)){
            log.warn("ID already exists {}", email);
            return null; // 한번더 체크 필요
        }
        else {
            log.info("create member Entity {}",email);
            return memberRepository.save(memberEntity);
        }

    }

    public MemberEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder){
        final MemberEntity originalMember = memberRepository.findByEmail(email);

        if(originalMember != null && encoder.matches(password, originalMember.getPassword())){
            return originalMember;
        }
        return null;
    }

    @Transactional
    public MemberEntity addStudentToTeacher(Long teacherId, Long studentPinCode) {
        MemberEntity teacher = memberRepository.findById(String.valueOf(teacherId))
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        MemberEntity student = memberRepository.findByPinCode(studentPinCode)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        // 학생의 teacherId 필드를 매칭된 선생님의 memberId로 설정
        student.setTeacherId(teacher.getMemberId());
        memberRepository.save(student);

        return teacher;
    }

    @Transactional
    public MemberEntity removeStudentFromTeacher(Long teacherId, Long studentPinCode) {
        MemberEntity teacher = memberRepository.findById(String.valueOf(teacherId))
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        MemberEntity student = memberRepository.findByPinCode(studentPinCode)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        // 학생의 teacherId 필드를 null로 설정하여 매칭 해제
        if (student.getTeacherId().equals(teacher.getMemberId())) {
            student.setTeacherId(null);
            memberRepository.save(student);
        }

        return teacher;
    }

    public List<MemberEntity> getStudentsOfTeacher(Long teacherId) {
        return memberRepository.findAll().stream()
                .filter(student -> teacherId.equals(student.getTeacherId()))
                .toList();
    }
}