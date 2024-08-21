package com.vlc.maeummal.domain.member.controller;

import com.vlc.maeummal.domain.member.dto.StudentDTO;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.service.MatchService;
import com.vlc.maeummal.domain.member.service.MemberService;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/match")
public class MatchController {
    private final MatchService matchService;
    private final UserAuthorizationConverter userAuthorizationConverter;

    public MatchController(MatchService matchService, UserAuthorizationConverter userAuthorizationConverter) {
        this.matchService = matchService;
        this.userAuthorizationConverter = userAuthorizationConverter;
    }

    @PostMapping("/match-student")
    public ResponseEntity<StudentDTO> matchStudentWithTeacher(@RequestParam String pinCode) {
        // 현재 로그인된 선생님의 ID를 사용
        Long teacherId = userAuthorizationConverter.getCurrentUserId();

        StudentDTO matchedStudent = matchService.matchStudentWithTeacher(pinCode, teacherId);
        return ResponseEntity.ok(matchedStudent);
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getStudentsByTeacherId() {
        // 현재 로그인된 선생님의 ID를 사용
        Long teacherId = userAuthorizationConverter.getCurrentUserId();

        List<StudentDTO> students = matchService.getStudentsByTeacherId(teacherId);
        return ResponseEntity.ok(students);
    }

}
