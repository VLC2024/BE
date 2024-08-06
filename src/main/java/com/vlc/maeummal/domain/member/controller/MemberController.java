package com.vlc.maeummal.domain.member.controller;

import com.vlc.maeummal.domain.member.dto.MemberDTO;
import com.vlc.maeummal.domain.member.dto.StudentDTO;
import com.vlc.maeummal.domain.member.dto.TeacherDTO;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.service.MemberService;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import com.vlc.maeummal.global.apiPayload.code.ErrorReasonDTO;
import com.vlc.maeummal.global.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private String  token = "";

//    @RequestMapping("/signup")
    @PostMapping("/signup/student")
    public ResponseEntity<?> registerStudent(@RequestBody StudentDTO student){
        try{
            MemberEntity registeredMember = memberService.createStudent(student);
            StudentDTO responseMemberDTO = StudentDTO.builder()
                    .email(registeredMember.getEmail())
                    .password(registeredMember.getPassword())
                    .build();
            log.info("회원가입 성공 : " + student.getEmail());
            return ResponseEntity.ok(ApiResponse.onSuccess(responseMemberDTO));
        } catch (Exception e){
            ErrorReasonDTO errorReasonDTO = ErrorReasonDTO.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .isSuccess(false)
                    .code("INTERNAL_SERVER_ERROR")
                    .message("An internal server error occurred.")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorReasonDTO);
        }
    }
    @PostMapping("/signup/teacher")
    public ResponseEntity<?> registerTeacher(@RequestBody TeacherDTO teacher){
        try{
            MemberEntity registeredMember = memberService.createTeacher(teacher);
            TeacherDTO responseMemberDTO = TeacherDTO.builder()
                    .email(registeredMember.getEmail())
                    .password(registeredMember.getPassword())
                    .build();
            log.info("회원가입 성공 : " + teacher.getEmail());
            return ResponseEntity.ok(ApiResponse.onSuccess(responseMemberDTO));
        } catch (Exception e){
            ErrorReasonDTO errorReasonDTO = ErrorReasonDTO.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .isSuccess(false)
                    .code("INTERNAL_SERVER_ERROR")
                    .message("An internal server error occurred.")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorReasonDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody MemberDTO memberDTO){
        MemberEntity member = memberService.getByCredentials(
                memberDTO.getEmail(),
                memberDTO.getPassword(),
                passwordEncoder);

        if(member != null){
            token = tokenProvider.createToken(member);

            final MemberDTO responseMemberDTO = MemberDTO.builder()

                    .email(member.getEmail())
                    .token(token)
                    .build();
            log.info("로그인 성공 : " + member.getEmail());
            return ResponseEntity.ok(ApiResponse.onSuccess(responseMemberDTO));
        } else {
            ErrorReasonDTO errorReasonDTO = ErrorReasonDTO.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .isSuccess(false)
                    .code("INTERNAL_SERVER_ERROR")
                    .message("An internal server error occurred.")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorReasonDTO);
        }
    }
    @GetMapping("/token")
    public ResponseEntity<?> getToken(@RequestBody MemberDTO memberDTO){
        MemberEntity member = memberService.getByCredentials(
                memberDTO.getEmail(),
                memberDTO.getPassword(),
                passwordEncoder);

        if(member != null){

            MemberDTO.GetTokenDTO tokenDTO = MemberDTO.GetTokenDTO.getTokenDTO(token);
            return ResponseEntity.ok(ApiResponse.onSuccess(tokenDTO));
        } else {
            ErrorReasonDTO errorReasonDTO = ErrorReasonDTO.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .isSuccess(false)
                    .code("INTERNAL_SERVER_ERROR")
                    .message("An internal server error occurred.")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorReasonDTO);
        }
    }
}