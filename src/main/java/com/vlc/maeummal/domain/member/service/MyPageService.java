package com.vlc.maeummal.domain.member.service;

import com.vlc.maeummal.domain.member.dto.StudentDTO;
import com.vlc.maeummal.domain.member.dto.TeacherDTO;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.repository.MemberReposirotyUsingId;
import com.vlc.maeummal.domain.member.repository.MemberRepository;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final MemberReposirotyUsingId memberReposirotyUsingId;
    private final UserAuthorizationConverter userAuthorizationConverter;

    public StudentDTO.GetStudentInfo getStudentInfo(MemberEntity member){
        return StudentDTO.GetStudentInfo.getStudentInfo(member);
    }

    public TeacherDTO.GetTeacherInfo getTeacherInfo(MemberEntity member){
        return TeacherDTO.GetTeacherInfo.getTeacherInfo(member);
    }

    @Transactional
    public StudentDTO.GetStudentInfo updateStudentInfo(MemberEntity member, StudentDTO.GetStudentInfo studentInfo){
        // 학생 정보 업데이트
        member.setImage(studentInfo.getProfileImage());
        member.setName(studentInfo.getName());
        member.setEmail(studentInfo.getEmail());
        member.setPhoneNumber(studentInfo.getPhoneNum());
        member.setImage(studentInfo.getProfileImage());
        member.setIq(studentInfo.getIq());

        memberRepository.save(member);

        return StudentDTO.GetStudentInfo.getStudentInfo(member);
    }
    @Transactional
    public TeacherDTO.GetTeacherInfo updateTeacherInfo(MemberEntity member, TeacherDTO.GetTeacherInfo teacherInfo){

        member.setName(teacherInfo.getName());
        member.setEmail(teacherInfo.getEmail());
        member.setPhoneNumber(teacherInfo.getPhoneNum());
        member.setImage(teacherInfo.getProfileImage());

        memberRepository.save(member);

        return TeacherDTO.GetTeacherInfo.getTeacherInfo(member);
    }

    // Todo : 이미지 업로드
    // Todo : 비밀번호 변경
}
