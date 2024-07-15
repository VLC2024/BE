package com.vlc.maeummal.domain.member.controller;

import com.vlc.maeummal.domain.member.dto.MemberDTO;
import com.vlc.maeummal.domain.member.service.MemberService;
import com.vlc.maeummal.global.apiPayload.code.ErrorReasonDTO;
import com.vlc.maeummal.global.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> registerMember(@RequestBody MemberDTO memberDTO){
        try{
            if(memberDTO == null || memberDTO.getPassword() == null){
                throw  new RuntimeException("Invalid Password value.");
            }

            //member 생성
            MemberEntity member = MemberEntity.builder()
                    .email(memberDTO.getEmail())
                    .password(passwordEncoder.encode(memberDTO.getPassword()))
                    .build();

            MemberEntity registeredMember = memberService.create(member);
            MemberDTO responseMemberDTO = memberDTO.builder()
                    .email(registeredMember.getEmail())
                    .password(registeredMember.getPassword())
                    .build();

            return ResponseEntity.ok().body(responseMemberDTO);
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
            final String token = tokenProvider.createToken(member);
            final MemberDTO resposeMemberDTO = MemberDTO.builder()
                    .name(member.getName())
                    .email(member.getEmail())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(resposeMemberDTO);
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