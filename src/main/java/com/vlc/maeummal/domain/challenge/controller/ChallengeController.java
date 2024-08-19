package com.vlc.maeummal.domain.challenge.controller;

import com.vlc.maeummal.domain.challenge.dto.ChallengeResponseDTO;
import com.vlc.maeummal.domain.challenge.service.ChallengeService;
import com.vlc.maeummal.domain.feedback.dto.FeedbackResponseDTO;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import com.vlc.maeummal.global.apiPayload.code.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Controller
@Slf4j
@RestController
@RequestMapping("/challenge")
@RequiredArgsConstructor
public class ChallengeController {
    final ChallengeService challengeService;

    @GetMapping("/get")
    public ApiResponse<ChallengeResponseDTO.GetChallengeDTO> isCompleteChallenge(@RequestParam(value = "studentId") Long studentId) {
        return ApiResponse.onSuccess(challengeService.getChallnege(studentId));

    }
}
