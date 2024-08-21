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


}
