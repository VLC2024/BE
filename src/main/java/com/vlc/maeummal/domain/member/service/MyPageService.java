package com.vlc.maeummal.domain.member.service;

import com.vlc.maeummal.domain.member.dto.StudentDTO;
import com.vlc.maeummal.domain.member.dto.TeacherDTO;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.repository.MemberReposirotyUsingId;
import com.vlc.maeummal.domain.member.repository.MemberRepository;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
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
}
