package com.vlc.maeummal.domain.member.controller;

import com.vlc.maeummal.domain.member.dto.StudentDTO;
import com.vlc.maeummal.domain.member.dto.TeacherDTO;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.repository.MemberReposirotyUsingId;
import com.vlc.maeummal.domain.member.service.MyPageService;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import com.vlc.maeummal.global.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PatchMapping("/updateProfileImage")
    public ResponseEntity<?> updateImage(@RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("업로드할 이미지가 없습니다. 이미지 업로드에 실패했습니다.");
        }

        myPageService.uploadProfileImage(file);
        return ResponseEntity.ok(ApiResponse.successWithoutResult());
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestParam("현재 비밀번호") String currentPassword,
                                            @RequestParam("새 비밀번호") String newPassword) {
        myPageService.changePassword(currentPassword, newPassword);
        return ResponseEntity.ok(ApiResponse.onSuccess("비밀번호가 성공적으로 변경되었습니다."));
    }
}
