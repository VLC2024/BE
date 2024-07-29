package com.vlc.maeummal.domain.feedback.controller;

import com.vlc.maeummal.domain.feedback.dto.FeedbackRequestDTO;
import com.vlc.maeummal.domain.feedback.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    /**
     * 피드백을 생성합니다.
     *
     * @param feedbackRequestDTO 피드백 요청 데이터
     * @return ResponseEntity 피드백 생성 결과
     */
    @PostMapping("/create")
    public ResponseEntity<?> createFeedback(
            @RequestBody  FeedbackRequestDTO.GetAnswer feedbackRequestDTO) {

        try {
            log.info(" in try FeedbackController: " + feedbackRequestDTO);
            // 피드백을 설정합니다.
            feedbackService.setFeedbackFromAnswer(feedbackRequestDTO);
            log.info("error in FeedbackController: " + feedbackRequestDTO);
            // 성공 응답 반환 (여기서 실제 응답 데이터는 FeedbackResponseDTO로 설정할 수 있습니다)
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            // 예외 처리 및 에러 응답 반환
            log.info("error in FeedbackController this is not valid: " + feedbackRequestDTO);
            log.error("Exception occurred while creating feedback: ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
