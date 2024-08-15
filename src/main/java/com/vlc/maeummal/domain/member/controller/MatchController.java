package com.vlc.maeummal.domain.member.controller;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class MatchController {
    private final MemberService memberService;

    public MatchController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/{teacherId}/students/{studentPinCode}")
    public ResponseEntity<MemberEntity> addStudentToTeacher(
            @PathVariable Long teacherId,
            @PathVariable Long studentPinCode) {
        MemberEntity updatedTeacher = memberService.addStudentToTeacher(teacherId, studentPinCode);
        return ResponseEntity.ok(updatedTeacher);
    }

    @DeleteMapping("/{teacherId}/students/{studentPinCode}")
    public ResponseEntity<MemberEntity> removeStudentFromTeacher(
            @PathVariable Long teacherId,
            @PathVariable Long studentPinCode) {
        MemberEntity updatedTeacher = memberService.removeStudentFromTeacher(teacherId, studentPinCode);
        return ResponseEntity.ok(updatedTeacher);
    }

    @GetMapping("/{teacherId}/students")
    public ResponseEntity<List<MemberEntity>> getStudentsOfTeacher(@PathVariable Long teacherId) {
        List<MemberEntity> students = memberService.getStudentsOfTeacher(teacherId);
        return ResponseEntity.ok(students);
    }
}
