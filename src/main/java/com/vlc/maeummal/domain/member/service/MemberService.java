package com.vlc.maeummal.domain.member.service;

import com.vlc.maeummal.domain.challenge.entity.ChallengeEntity;
import com.vlc.maeummal.domain.challenge.repository.ChallengeRepository;
import com.vlc.maeummal.domain.member.dto.StudentDTO;
import com.vlc.maeummal.domain.member.dto.TeacherDTO;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.repository.MemberReposirotyUsingId;
import com.vlc.maeummal.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 회원가입시, 교사 정보 저장
    public MemberEntity createTeacher(TeacherDTO teacherDTO){
        if (teacherDTO == null || teacherDTO.getPassword() == null) {
            throw new RuntimeException("Invalid Password value.");
        }
        MemberEntity member = MemberEntity.builder()
                .email(teacherDTO.getEmail())
                .password(passwordEncoder.encode(teacherDTO.getPassword()))
                .name(teacherDTO.getName())
                .phoneNumber(teacherDTO.getPhoneNumber())
                .birthDay(teacherDTO.getBirthDay())
                .gender(teacherDTO.getGender())
                .role(teacherDTO.getRole())
                .organization(teacherDTO.getOrganization())
                .build();

        if (member == null) {
            throw new RuntimeException("Invalid arguments");
        }

        final String email = member.getEmail();

        // 이미 존재하는 계정일 경우
        if (memberRepository.existsByEmail(email)) {
            log.warn("ID already exists {}", email);
            throw new RuntimeException("이미 존재하는 계정입니다.");
        } else {
            log.info("회원가입 성공 : " + member.getEmail());
            return memberRepository.save(member);
        }
    }

    // 회원가입 시, 학생 정보 저장 및 챌린지 생성
    @Transactional
    public MemberEntity createStudent(StudentDTO studentDTO) {
        if (studentDTO == null || studentDTO.getPassword() == null) {
            throw new RuntimeException("Invalid Password value.");
        }

        MemberEntity member = MemberEntity.builder()
                .email(studentDTO.getEmail())
                .password(passwordEncoder.encode(studentDTO.getPassword()))
                .name(studentDTO.getName())
                .phoneNumber(studentDTO.getPhoneNumber())
                .birthDay(studentDTO.getBirthDay())
                .gender(studentDTO.getGender())
                .role(studentDTO.getRole())
                .pinCode(studentDTO.getPinCode())
                .iq(studentDTO.getIq())
                .build();

        if (member == null) {
            throw new RuntimeException("Invalid arguments");
        }

        final String email = member.getEmail();

        // 이미 존재하는 계정일 경우
        if (memberRepository.existsByEmail(email)) {
            log.warn("ID already exists {}", email);
            return null; // 한번더 체크 필요
        } else {
            log.info("회원가입 성공 : " + member.getEmail());

            // 회원 저장
            MemberEntity savedMember = memberRepository.save(member);

            // 챌린지 생성 및 저장
            ChallengeEntity challenge = ChallengeEntity.builder()
                    .member(savedMember)
                    .completed(false)
                    .date(LocalDateTime.now())
                    .title("오늘의 챌린지")
                    .wordMission(false)
                    .templateMission(false)
                    .prepMission(false)
                    .build();
//            challenge.setMember(savedMember);

            challengeRepository.save(challenge);

            // 회원과 챌린지를 연결
//            savedMember.setChallenge(challenge);


            return savedMember;
        }
    }

    public MemberEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
        final MemberEntity originalMember = memberRepository.findByEmail(email);

        if (originalMember != null && encoder.matches(password, originalMember.getPassword())) {
            return originalMember;
        }
        return null;
    }
}