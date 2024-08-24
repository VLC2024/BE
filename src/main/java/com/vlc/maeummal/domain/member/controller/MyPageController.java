package com.vlc.maeummal.domain.member.controller;

import com.vlc.maeummal.domain.member.dto.StudentDTO;
import com.vlc.maeummal.domain.member.dto.TeacherDTO;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.repository.MemberReposirotyUsingId;
import com.vlc.maeummal.domain.member.service.MemberService;
import com.vlc.maeummal.domain.member.service.MyPageService;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import com.vlc.maeummal.global.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.tree.expression.SqmToDuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MyPageController {
    private final UserAuthorizationConverter userAuthorizationConverter;
    private final MemberReposirotyUsingId memberReposirotyUsingId;
    private final MyPageService myPageService;
    @GetMapping
    public ResponseEntity<?> getMemberInfo(){
        Long memberId = userAuthorizationConverter.getCurrentUserId();
        MemberEntity member = memberReposirotyUsingId.findById(memberId).orElseThrow(() -> new RuntimeException(""));
        if (member.getRole().equals(Role.STUDENT)){
            StudentDTO.GetStudentInfo result =  myPageService.getStudentInfo(member);
            return ResponseEntity.ok(ApiResponse.onSuccess(result));
        } else if(member.getRole().equals(Role.TEACHER)){
            TeacherDTO.GetTeacherInfo result = myPageService.getTeacherInfo(member);
            return ResponseEntity.ok(ApiResponse.onSuccess(result));
        } else {
            return ResponseEntity.badRequest().body("잘못된 접근입니다. 회원가입과 로그인을 진행해주세요");
        }
    }

    @PatchMapping("/student")
    public ResponseEntity<?> updateMemberInfo(@RequestBody StudentDTO.GetStudentInfo studentInfo){
        Long memberId = userAuthorizationConverter.getCurrentUserId();
        MemberEntity member = memberReposirotyUsingId.findById(memberId).orElseThrow(() -> new RuntimeException(""));
        StudentDTO.GetStudentInfo result = myPageService.updateStudentInfo(member, studentInfo);
        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

    @PatchMapping("/teacher")
    public ResponseEntity<?> updateMemberInfo(@RequestBody TeacherDTO.GetTeacherInfo teacherInfo){
        Long memberId = userAuthorizationConverter.getCurrentUserId();
        MemberEntity member = memberReposirotyUsingId.findById(memberId).orElseThrow(() -> new RuntimeException(""));
        TeacherDTO.GetTeacherInfo result = myPageService.updateTeacherInfo(member, teacherInfo);
        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }

}
