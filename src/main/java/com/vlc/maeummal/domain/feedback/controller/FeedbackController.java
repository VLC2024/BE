package com.vlc.maeummal.domain.feedback.controller;

import com.vlc.maeummal.domain.challenge.service.ChallengeService;
import com.vlc.maeummal.domain.feedback.dto.FeedbackRequestDTO;
import com.vlc.maeummal.domain.feedback.dto.FeedbackResponseDTO;
import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import com.vlc.maeummal.domain.feedback.service.FeedbackService;
import com.vlc.maeummal.global.apiPayload.ApiErrResponse;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import com.vlc.maeummal.global.apiPayload.code.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.vlc.maeummal.global.apiPayload.code.status.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
//    private final UserAuthorizationConverter userAuthorizationConverter;
    private final ChallengeService challengeService;
    /**
     * 피드백을 생성합니다.
     *
     * @param feedbackRequestDTO 피드백 요청 데이터
     * @return ResponseEntity 피드백 생성 결과
     */
    // 1차 피드백을 만든다 -> 다 맞으면 최종 피드백 반환, 틀리면 1차 피드백이 반환
    @PostMapping("/create")
    public ResponseEntity<?> createFeedback(@RequestBody  FeedbackRequestDTO.GetAnswer feedbackRequestDTO) {
//        Long memberId = userAuthorizationConverter.getCurrentUserId();
//        challengeService.completeMission(memberId, MissionType.TEMP);

        try {
            // Call the service method to process the feedback and get the saved Feedback ID
            FeedbackResponseDTO.GetFeedbackDetailDTO savedFeedback =
                    feedbackService.createFirstFeedBack(feedbackRequestDTO);

            if(savedFeedback.getAnswerNum().equals(savedFeedback.getImageNum())){
                FeedbackResponseDTO.GetFeedbackDetailDTO savedFeedback1 =
                        feedbackService.setFeedbackFromAnswer(feedbackRequestDTO);
                log.info("Successfully processed feedback with ID: " + savedFeedback1);
                return ResponseEntity.ok(savedFeedback1);
            }

            // Log the successful operation
            log.info("Successfully processed feedback with ID: " + savedFeedback);

            // Return the ID in the response
            return ResponseEntity.ok(savedFeedback);
        } catch (UsernameNotFoundException e) {
            // Log specific exception details
            log.error("User not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (IllegalArgumentException e) {
            // Log specific exception details
            log.error("Invalid argument: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Log the generic exception details
            log.error("An unexpected error occurred: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }

    }
    // 모든 피드백 리스트 반환

//    @GetMapping("/all/")
//    public ApiResponse<List<FeedbackResponseDTO.GetFeedbackDTO>> getAllFeedback() {
//        List<FeedbackResponseDTO.GetFeedbackDTO> feedbackEntityList = feedbackService.getAllFeedback();
//        return ApiResponse.of(SuccessStatus._OK,  feedbackEntityList);
//
//    }
    @GetMapping("/all")
    public ApiResponse<List<FeedbackResponseDTO.GetFeedbackDTO>> getAllFeedbackFromStudentId(@RequestParam(value = "id") Long studentId) {
        List<FeedbackResponseDTO.GetFeedbackDTO> feedbackEntityList = feedbackService.getAllFeedback();
        return ApiResponse.of(SuccessStatus._OK,  feedbackEntityList);

    }
    @GetMapping("/detail")
    public ApiResponse<FeedbackResponseDTO.GetFeedbackDetailDTO> getFeedbackDetailFromId(@RequestParam(value = "id") Long id) {
      FeedbackResponseDTO.GetFeedbackDetailDTO feedback = feedbackService.getFeedbackDetail(id);
        return ApiResponse.of(SuccessStatus._OK,  feedback);

    }

// 사용 x
    @GetMapping("/not/use")
    public ResponseEntity<?> getFeedbackFromId(@RequestParam(value = "id") Long studentId) {
        List<FeedbackResponseDTO.GetFeedbackDetailDTO> feedbackList = feedbackService.getAllFeedbackFromStudent(studentId);
        if (feedbackList == null) {
            ApiErrResponse<FeedbackEntity> errorResponse = ApiErrResponse.onFailureWithCode(
                    ErrorStatus._NOT_FOUND_FEEDBACK,
                    ErrorStatus._NOT_FOUND_FEEDBACK.getMessage(),
                    null
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } else {
            ApiResponse< List<FeedbackResponseDTO.GetFeedbackDetailDTO>> successResponse = ApiResponse.of(SuccessStatus._OK, feedbackList);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
    }

}
